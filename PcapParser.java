import java.util.List;
import java.util.ArrayList;

import java.io.*;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PcapParser {

    private ByteBuffer bytes;

    private static final int globalHeaderLength = 24; // bytes
    private static final int recordHeaderLength = 16; // bytes

    private int dataLinkType;

    public PcapParser (byte[] bytes) {
        this.bytes = ByteBuffer.wrap(bytes);
    }
    
    public String toString () {
        return BinaryUtils.toHexString(bytes.array(), true);
    }
    
    public boolean isPcap () {
        int magicNumber = BinaryUtils.extractIntByte(bytes, 0, 4);
        return magicNumber == 0xa1b2c3d4 || magicNumber == 0xd4c3b2a1;
    }

    public int dataLinkType () {
        return dataLinkType;
    }

    public int inclLen (int recordOffset) {
        return BinaryUtils.extractIntByte(bytes, recordOffset + 8, 4);
    }

    public int origLen (int recordOffset) {
        return BinaryUtils.extractIntByte(bytes, recordOffset + 12, 4);
    }

    /* Parses the beginning of the file and returns whether this file is parsable by this parser */
    public boolean parseGlobalHeader () {
        this.dataLinkType = BinaryUtils.extractIntByte(bytes, 20, 4);
        return this.bytes.limit() > 40 && this.isPcap() && this.dataLinkType == 0;
    }

    /* Divide data in data frames and returns a list of them */
    public List<DataFrame> extractFrames () throws PcapParserException {
        // global header: 24 bytes
        // packet header: 16 bytes
        List<DataFrame> frames = new ArrayList<DataFrame>();
        int recordOffset = globalHeaderLength, currentFrameLen = 0, inclLen, origLen;
        ByteBuffer frameBytes = null;
        do {
            /* Parse records header */
            inclLen = inclLen(recordOffset);
            origLen = origLen(recordOffset);
            recordOffset += recordHeaderLength;

            /* Parse record data (splitted frames) */
            // New frame
            if (currentFrameLen == 0) {
                frameBytes = ByteBuffer.allocateDirect(origLen);
            }

            // Extract record to frame
            frameBytes.put(bytes.array(), recordOffset, inclLen);
            currentFrameLen += inclLen;

            // End frame
            if (currentFrameLen == origLen) {
                currentFrameLen = 0; 
                frames.add(new DataFrame(frameBytes));
            } else if (currentFrameLen > origLen) throw new PcapParserException();

            recordOffset += recordHeaderLength + inclLen;
            
        } while (recordOffset < this.bytes.limit()); // TODO: break somewhere!

        return frames;
    }
   

    /* Divide frames in protocol data units and return a list of them */ 
    public List<ProtocolSpec> extractLayers (DataFrame frame) {
        List<ProtocolSpec> layers = new ArrayList<ProtocolSpec>();
        int frameOffset = 0, currentLayerLen = 0, i = 0;
        ProtocolSpec lastLayer = null;
        String protoName;

        do {
            protoName = this.detectLayer(frame, frameOffset, lastLayer);

            switch (protoName) {
                case "EthernetII": lastLayer = new EthernetII(frame, frameOffset); break;
                case "ARP": lastLayer = new ARP(frame, frameOffset); break;
                case "IPv4": lastLayer = new IPv4(frame, frameOffset); break;
                case "ICMP": lastLayer = new ICMP(frame, frameOffset); break;
                case "TCP": lastLayer = new TCP(frame, frameOffset); break;
                case "UDP": lastLayer = new UDP(frame, frameOffset); break;
                case "HTTP": lastLayer = new HTTP(frame, frameOffset); break;
                case "DNS": lastLayer = new DNS(frame, frameOffset); break;
                case "DHCP": lastLayer = new DHCP(frame, frameOffset); break;
                default: lastLayer = new UnknownProtocol(frame, frameOffset);
            }

            layers.add(lastLayer);
            frameOffset += layers.get(i).headerSize(frame, frameOffset);
            i++;

        } while (frameOffset < frame.length() && lastLayer.name != "UnknownProtocol" && lastLayer.OSILayer != 7);

        for (ProtocolSpec proto : layers)
            if (proto.name.equals("EthernetII")) {
                ((EthernetII)proto).setFooterOrigin(frameOffset);
                break;
            }

        return layers;
    }
        
    public String detectLayer (DataFrame frame, int offset, ProtocolSpec lastLayer) {
        int layerOffset;
        if (lastLayer == null) {
            if (this.dataLinkType == 0) 
                return "EthernetII";
            else return "UnknownProtocol";
        }
        
        switch (lastLayer.name) {
            case "EthernetII":
                EthernetII ethernet = (EthernetII) lastLayer;
                switch (ethernet.etherType()) {
                    case 0x0806: return "ARP";
                    case 0x0800: return "IPv4";
                    case 0x86DD: return "IPv6";
                    default: return "UnknownProtocol";
                }
            case "IPv4":
                IPv4 ip = (IPv4) lastLayer;
                switch (ip.proto()) {
                    case 1: return "ICMP";
                    case 6: return "TCP";
                    case 17: return "UDP";
                    default: return "UnknownProtocol";
                }
            case "TCP":
                lastLayer = (TCP) lastLayer;
                layerOffset = offset + lastLayer.headerSize(frame, offset);
                if (HTTP.test(frame, layerOffset))
                    return "HTTP";
                if (DNS.test(frame, layerOffset))
                    return "DNS";
                return "UnknownProtocol";
            case "UDP":
                lastLayer = (UDP) lastLayer;
                layerOffset = offset + lastLayer.headerSize(frame, offset);
                if (DHCP.test(frame, layerOffset))
                    return "DHCP";
                if (DNS.test(frame, layerOffset))
                    return "DNS";
                return "UnknownProtocol";
            default:
                return "UnknownProtocol"; 
        }
    }

    public List<DataFrame> filterProtocol (List<DataFrame> frames, String filterProtoName) {
        List<DataFrame> filtered = new ArrayList<DataFrame>();
        for (DataFrame frame : frames)
            for (ProtocolSpec proto : frame.layers())
                if (proto.name.equals(filterProtoName)) {
                    filtered.add(frame);
                    break;
                }
        return filtered;
    }

    public static void main (String[] args) {
        String filename = (args.length > 0 ? args[0] : "icmping.pcap");
        String filterProtoName = (args.length > 1 ? args[1] : "EthernetII");
        try {
            byte[] data = Files.readAllBytes(Paths.get(filename));
            PcapParser parser = new PcapParser(data);
            //System.out.println(BinaryUtils.toHexString(data, true, 40, 138)); // First ICMP packet
            System.out.println(BinaryUtils.toHexString(data, true, 0, data.length)); // First ICMP packet
            
            List<DataFrame> frames = parser.extractFrames();
            for (DataFrame frame : frames) {
                frame.setLayers(parser.extractLayers(frame));
                System.out.println(frame);
            }
            System.out.println('\n');
            for (DataFrame frame : parser.filterProtocol(frames, filterProtoName)) {
                System.out.println(frame);
            }

        } catch (IOException|PcapParserException e) {
            e.printStackTrace();
        }
    }
}

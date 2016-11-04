import java.util.List;
import java.util.ArrayList;

import java.io.*;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PcapParser {

    private byte[] bytes; // TODO: make a ByteBuffer

    private static final int globalHeaderLength = 24; // bytes
    private static final int recordHeaderLength = 16; // bytes

    private int dataLinkType;

    public PcapParser (byte[] bytes) {
        this.bytes = bytes;
    }
    
    public String toHexString (boolean withAscii) {
        return toHexString(withAscii, 0, this.bytes.length);
    }

    public String toHexString (boolean withAscii, int offsetMin, int offsetMax) {
        StringBuilder hexStr = new StringBuilder();
        StringBuilder asciiStr = new StringBuilder();
        
        int i, bytesCount = offsetMax - offsetMin, rowWidth = 16;
        for (i = 0; i < bytesCount; i++) {
            
            hexStr.append(String.format("%02x ", bytes[offsetMin + i]));
            if (withAscii)
                asciiStr.append(String.format("%c", this.toPrintable(bytes[offsetMin + i])));
            
            if (i % rowWidth == rowWidth/2-1) hexStr.append(' ');
            if (i % rowWidth == rowWidth-1) 
                if (!withAscii) hexStr.append('\n');
                else {
                    hexStr.append('|').append(asciiStr).append("|\n");
                    asciiStr.delete(0, asciiStr.length());
                }
        }
        hexStr.append(String.format("%" + ((rowWidth - i % rowWidth) * 3 + (i % rowWidth <= rowWidth/2 ? 1 : 0)) + "s", " "));
        if (withAscii) hexStr.append('|').append(asciiStr).append("|\n");

        return hexStr.toString();
    }

    private byte toPrintable(byte b) {
        if      (b < 0x20) return (byte)0x2E;
        else if (b > 0x7E) return (byte)0x2E;
        return b;
    }

    public String toString () {
        return this.toHexString(true);
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
        return this.bytes.length > 40 && this.isPcap() && this.dataLinkType == 0;
    }

    /* Divide data in data frames and returns a list of them */
    public List<DataFrame> extractFrames () {
        // global header: 24 bytes
        // packet header: 16 bytes
        List<DataFrame> frames = new ArrayList<DataFrame>();
        int recordOffset = globalHeaderLength, currentFrameLen = 0, inclLen, origLen;
        ByteBuffer frameBytes;
        do {
            /* Parse records header */
            inclLen = inclLen(recordOffset);
            origLen = origLen(recordOffset);

            /* Parse record data (splitted frames) */
            // New frame
            if (currentFrameLen == 0) {
                frameBytes = new ByteBuffer(origLen);
            }

            // Extract record to frame
            frameBytes.put(bytes, recordOffset + recordHeaderLength, inclLen);
            currentFrameLen += inclLen;

            // End frame
            if (currentFrameLen == origLen) {
                currentFrameLen = 0; 
                frames.add(frameBytes);
            } else if (currentFrameLen > origLen) throw new PcapParserException();

            recordOffset += inclLen;
            
        } while (true); // TODO: break somewhere!

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
                default: "UnknownProtocol": lastLayer = new UnknownProtocol(frame, frameOffset);
            }

            layers.add(ethernet);
            frameOffset += layers.get(i).headerLength();
            i++;

        } while (frameOffset < frame.length() && lastLayer.name != "UnknownProtocol" && layerLayer.OSILayer != 7);

        return layers;
    }
        
    public String detectLayer (DataFrame frame, int offset, ProtocolSpec lastLayer) {
        // TODO
        if (lastLayer == null) {
            if (this.dataLinkType == 0) 
                return "EthernetII"
            else return "UnknownProtocol"
        }
        switch (lastLayer.name) {
            case "EthernetII":
                lastLayer = (EthernetII) lastLayer;
                switch (lastLayer.etherType()) {
                    case 0x0806: return "ARP";
                    case 0x0800: return "IPv4";
                    case 0x86DD: return "IPv6";
                    default: return "UnknownProtocol";
                }
            break;
            case "IPv4":
                lastLayer = (IPv4) lastLayer;
                switch (lastLayer.protocol()) {
                    case 1: return "ICMP";
                    case 6: return "TCP";
                    case 17: return "UDP";
                    default: return "UnknownProtocol";
                }
            break;
            case "TCP":
                lastLayer = (TCP) lastLayer;
                int layerOffset = offset + lastLayer.headerLength();
                if (HTTP.test(frame, layerOffset))
                    return "HTTP";
                if (DNS.test(frame, layerOffset))
                    return "DNS";
                return "UnknownProtocol";
            break;
            case "UDP":
                lastLayer = (UDP) lastLayer;
                int layerOffset = offset + lastLayer.headerLength();
                if (DHCP.test(frame, layerOffset))
                    return "DHCP";
                if (DNS.test(frame, layerOffset))
                    return "DNS";
                return "UnknownProtocol";
            break;
            default:
                return "UnknownProtocol"; 
        }
    }

    public static void main (String[] args) {
        try {
            byte[] data = Files.readAllBytes(Paths.get("icmping.pcap"));
            PcapParser parser = new PcapParser(data);
            System.out.println(parser.toHexString(true, 40, 138)); // First ICMP packet
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

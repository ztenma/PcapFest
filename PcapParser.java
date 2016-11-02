import java.io.*;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PcapParser {

    private byte[] bytes; // TODO: make a ByteBuffer

    private static final int globalHeaderLength = 24; // bytes
    private static final int packetHeaderLength = 16; // bytes

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
    
    public boolean isPcap() {
        int magicNumber = BinaryUtils.extractInt(bytes, 0, 4);
        return magicNumber == 0xa1b2c3d4 || magicNumber == 0xd4c3b2a1;
    }

    public List<DataFrame> extractFrames () {
        // global header: 24 bytes
        // packet header: 16 bytes
        List<DataFrame> frames = new ArrayList<DataFrame>();
        int dataBlockOffset = globalHeaderLength, currentFrameLen = 0, inclLen, origLen;
        ByteBuffer frameBytes;
        do {
            inclLen = BinaryUtils.extractIntByte(frame, dataBlockOffset + 8, 4);
            origLen = BinaryUtils.extractIntByte(frame, dataBlockOffset + 12, 4);
            dataBlockOffset += packetHeaderLength;

            if (currentFrameLen == 0) {
                frameBytes = new ByteBuffer(origLen);
            }

            frameBytes.put(bytes, dataBlockOffset, inclLen);
            currentFrameLen += inclLen;

            if (currentFrameLen == origLen) {
                currentFrameLen = 0; 
            }

            dataBlockOffset += inclLen;
            
        } while ();

        return frames;
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

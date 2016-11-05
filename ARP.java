import java.nio.ByteBuffer;

public class ARP implements ProtocolSpec {

    public static final String name = "ARP";
    public static final byte OSILayer = 2;

    private DataFrame frame;
    private ByteBuffer frameBytes;

    private int headerOrigin;

    public ARP (DataFrame frame, int headerOrigin) {
        this.frame = frame;
        this.frameBytes = frame.bytes();
        this.headerOrigin = headerOrigin;
    }

    public static boolean test (DataFrame frame, int offset) {
        ARP arp = new ARP(frame, offset);
        return true;
    }

    public String name () { return name; }

    public int OSILayer () { return OSILayer; }

    public int headerSize (DataFrame frame, int offset) {
        return 28;
    }

    // 16 bits
    public int hwType () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin, 0, 16);
    }

    // 16 bits
    public int protoType () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+2, 0, 16);
    }	

    // 8 bits
    public int hwAddrLength () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+4, 0, 8);
    }

    // 8 bits
    public int protoAddrLength () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+5, 0, 8);
    }

    // 16 bits
    public int operation () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+6, 0, 16);
    }

    // 48 bits = 6 octets
    public String srcMAC () {
        return BinaryUtils.extractMACAddress(frameBytes, headerOrigin+8, 6);
    }

    // 32 bits
    public String srcIP () {
        return BinaryUtils.extractIPv4Address(frameBytes, headerOrigin+14, 4);
    }

    // 48 bits
    public String dstMAC () {
        return BinaryUtils.extractMACAddress(frameBytes, headerOrigin+18, 6);
    }

    // 32 bits
    public String dstIP () {
        return BinaryUtils.extractIPv4Address(frameBytes, headerOrigin+24, 4);
    }

    @Override
    public String toString() {
        return "ARP{" +
                "srcMAC=" + srcMAC() + "," +
                "srcIP=" + srcIP() + "," +
                "dstMAC=" + dstMAC() + "," +
                "dstIP=" + dstIP() +
                '}';
    }
}

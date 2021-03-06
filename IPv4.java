import java.nio.ByteBuffer;

public class IPv4 implements ProtocolSpec {

    public static final String name = "IPv4";
    public static final byte OSILayer = 2;

    private DataFrame frame;
    private ByteBuffer frameBytes;

    private int headerOrigin;

    public IPv4 (DataFrame frame, int headerOrigin) {
        this.frame = frame;
        this.frameBytes = frame.bytes();
        this.headerOrigin = headerOrigin;
    }

    public static boolean test (DataFrame frame, int offset) {
        return true; //TODO
    }

    public String name () { return name; }

    public int OSILayer () { return OSILayer; }

    public int headerSize (DataFrame frame, int offset) {
        return this.ihl()* 4;  // headerSize
    }

    public int version () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin, 0, 4);
    }

    public int ihl () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin, 4, 4);
    }

    public int typeOfService () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+1, 0, 8);
    }

    public int totalLength () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+2, 0, 16);
    }

    public int identification () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+4, 0, 16);
    }

    public int flags () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+6, 0, 3);
    }

    public int fragmentOffset () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+6, 3, 13);
    }

    // 8 bits
    public int ttl () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+8, 0, 8);
    }

    // 8 bits
    public int proto () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+9, 0, 8);
    }

    // 16 bits
    public int checksum () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+10, 0, 16);
    }

    // 32 bits
    public String srcIP () {
        return BinaryUtils.extractIPv4Address(frameBytes, headerOrigin+12, 4);
    }

    // 32 bits
    public String dstIP () {
        return BinaryUtils.extractIPv4Address(frameBytes, headerOrigin+16, 4);
    }

    @Override
    public String toString() {
        return "IPv4{" +
                "srcIP=" + srcIP() + "," +
                "dstIP=" + dstIP() + "," +
                "flags=" + flags() + "," +
                "ttl=" + ttl() +
                '}';
    }
    
    public String toPrettyString() {
        return "[IPv4]\n" +
                "srcIP = " + srcIP() + "\n" +
                "dstIP = " + dstIP() + "\n" +
                "flags = " + flags() + "\n" +
                "ttl = " + ttl() + "\n";
    }
}


import java.nio.ByteBuffer;

public class TCP implements ProtocolSpec {

    public static final String name = "TCP";
    public static final byte OSILayer = 4;

    private DataFrame frame;
    private ByteBuffer frameBytes;

    private int headerOrigin;

    public TCP (DataFrame frame, int headerOrigin) {
        this.frame = frame;
        this.frameBytes = frame.bytes();
        this.headerOrigin = headerOrigin;
    }

    public static boolean test (DataFrame frame, int offset) {
        return true;
    }

    public String name () { return name; }

    public int OSILayer () { return OSILayer; }

    public int headerSize (DataFrame frame, int offset) {
        return this.headerLength() * 4;
    }

    // 16 bits
    public int srcPort () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin, 0, 16);
    }

    // 16 bits
    public int dstPort () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+2, 0, 16);
    }

    // 32 bits
    public int seqNumber () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+4, 0, 32);
    }

    // 32 bits 
    public int acknowldgmtNumber () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+8, 0, 32);
    }

    // 4 bits
    public int headerLength() {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+12, 0, 4);
    }

    // 3 bits
    public int reserved () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+12, 4, 3);
    }

    // 9 bit
    public int flags () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+12, 7, 9);
    }

    // 16 bits
    public int windowSize () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+14, 0, 16);
    }

    // 16 bits
    public int checksum () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+16, 0, 16);
    }

    // 16 bits
    public int urgPointer () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+18, 0, 16);
    }

    @Override
    public String toString() {
        return "TCP{" +
                "srcPort=" + srcPort() + "," +
                "dstPort=" + dstPort() + "," +
                "flags=" + flags() + "," +
                "seqNumber=" + seqNumber() + "}";
    }

    public String toPrettyString() {
        return "[TCP]\n" +
                "srcPort = " + srcPort() + "\n" +
                "dstPort = " + dstPort() + "\n" +
                "flags = " + flags() + "\n" +
                "seqNumber = " + seqNumber() + "\n";
    }
}

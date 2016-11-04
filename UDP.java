
public class UDP extends ProtocolSpec {

    public static final String name = "UDP";
    public static final byte OSILayer = 4;

    private DataFrame frame;
    private ByteBuffer frameBytes;

    private int headerOrigin;

    public UDP (DataFrame frame, int headerOrigin) {
        this.frame = frame;
        this.frameBytes = frame.getBytes();
        this.headerOrigin = headerOrigin;
    }

    // 16 bits
    public int srcPort () {
        return BinaryUtils.extractInt(frame, headerOrigin, 0, 16);
    }

    // 16 bits
    public int dstPort () {
        return BinaryUtils.extractInt(frame, headerOrigin+2, 0, 16);
    }

    // 16 bits
    public int length () {
        return BinaryUtils.extractInt(frame, headerOrigin+4, 0, 16);
    }

    // 16 bits
    public int checksum () {
        return BinaryUtils.extractInt(frame, headerOrigin+6, 0, 16);
    }

}

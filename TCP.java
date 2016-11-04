import java.nio.ByteBuffer;

public class TCP extends ProtocolSpec {

    public static final String name = "TCP";
    public static final byte OSILayer = 4;

    private DataFrame frame;
    private ByteBuffer frameBytes;

    private int headerOrigin;

    public TCP (DataFrame frame, int headerOrigin) {
        this.frame = frame;
        this.frameBytes = frame.getBytes();
        this.headerOrigin = headerOrigin;
    }

    public static boolean test (ByteBuffer bytes, int offset) {
        return false;
    }

    public static int headerSize () {
        return this.dataOffset * 4;
    }

    // 16 bits
    public int srcPort () {
        return BinaryUtils.extractInt(frame, headerOrigin, 0, 16);
    }

    // 16 bits
    public int dstPort () {
        return BinaryUtils.extractInt(frame, headerOrigin+2, 0, 16);
    }

    // 32 bits
    public int seqNumber () {
        return BinaryUtils.extractInt(frame, headerOrigin+4, 0, 32);
    }

    // 32 bits 
    public int acknowldgmtNumber () {
        return BinaryUtils.extractInt(frame, headerOrigin+8, 0, 32);
    }

    // 4 bits
    public int dataOffset () {
        return BinaryUtils.extractInt(frame, headerOrigin+12, 0, 4);
    }

    // 3 bits
    public int reserved () {
        return BinaryUtils.extractInt(frame, headerOrigin+12, 4, 3);
    }

    // 9 bit
    public int flags () {
        return BinaryUtils.extractInt(frame, headerOrigin+12, 7, 9);
    }

    // 16 bits
    public int windowSize () {
        return BinaryUtils.extractInt(frame, headerOrigin+14, 0, 16);
    }

    // 16 bits
    public int checksum () {
        return BinaryUtils.extractInt(frame, headerOrigin+16, 0, 16);
    }

    // 16 bits
    public int urgPointer () {
        return BinaryUtils.extractInt(frame, headerOrigin+18, 0, 16);
    }

}


public class TCP extends ProtocolSpec {

    public static final String name = "TCP";
    public static final byte OSILayer = 4;

    private DataFrame frame;
    private ByteBuffer frameBytes;

    private int pduOrigin;
    private int pduLength;

    public TCP (DataFrame frame, int pduOrigin, int pduLength) {
        this.frame = frame;
        this.frameBytes = frame.getBytes();
        this.pduOrigin = pduOrigin;
        this.pduLength = pduLength;
    }

    // 16 bits
    public int srcPort () {
        return BinaryUtils.extractInt(frame, pduOrigin, 0, 16);
    }

    // 16 bits
    public int dstPort () {
        return BinaryUtils.extractInt(frame, pduOrigin+2, 0, 16);
    }

    // 32 bits
    public int seqNumber () {
        return BinaryUtils.extractInt(frame, pduOrigin+4, 0, 32);
    }

    // 32 bits 
    public int acknowldgmtNumber () {
        return BinaryUtils.extractInt(frame, pduOrigin+8, 0, 32);
    }

    // 4 bits
    public int dataOffset () {
        return BinaryUtils.extractInt(frame, pduOrigin+12, 0, 4);
    }

    // 3 bits
    public int reserved () {
        return BinaryUtils.extractInt(frame, pduOrigin+12, 4, 3);
    }

    // 9 bit
    public int flags () {
        return BinaryUtils.extractInt(frame, pduOrigin+12, 7, 9);
    }

    // 16 bits
    public int windowSize () {
        return BinaryUtils.extractInt(frame, pduOrigin+14, 0, 16);
    }

    // 16 bits
    public int checksum () {
        return BinaryUtils.extractInt(frame, pduOrigin+16, 0, 16);
    }

    // 16 bits
    public int urgPointer () {
        return BinaryUtils.extractInt(frame, pduOrigin+18, 0, 16);
    }

}

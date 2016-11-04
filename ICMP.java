
public class ICMP extends ProtocolSpec {

    public static final String name = "ICMP";
    public static final byte OSILayer = 4;

    private DataFrame frame;
    private ByteBuffer frameBytes;

    private int headerOrigin;

    public ICMP (DataFrame frame, int headerOrigin) {
        this.frame = frame;
        this.frameBytes = frame.getBytes();
        this.headerOrigin = headerOrigin;
    }	
    // 8 bits
    public int type () {
        return BinaryUtils.extractInt(frame, headerOrigin, 0, 8);
    }

    // 8 bits
    public int code () {
        return BinaryUtils.extractInt(frame, headerOrigin+1, 0, 8);
    }

    // 16 bits
    public int checksum () {
        return BinaryUtils.extractInt(frame, headerOrigin+2, 0, 16);
    }

    // 32 bits
    public int restOfHeader () {
        return BinaryUtils.extractInt(frame, headerOrigin+4, 0, 32);
    }

}

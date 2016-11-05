import java.nio.ByteBuffer;

public class ICMP implements ProtocolSpec {

    public static final String name = "ICMP";
    public static final byte OSILayer = 4;

    private DataFrame frame;
    private ByteBuffer frameBytes;

    private int headerOrigin;

    public ICMP (DataFrame frame, int headerOrigin) {
        this.frame = frame;
        this.frameBytes = frame.bytes();
        this.headerOrigin = headerOrigin;
    }	

    public static boolean test (DataFrame frame, int offset) {
        return true;
    }
   
    public static int headerSize () {
        return 64;
    }

    // 8 bits
    public int type () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin, 0, 8);
    }

    // 8 bits
    public int code () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+1, 0, 8);
    }

    // 16 bits
    public int checksum () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+2, 0, 16);
    }

    // 32 bits
    public int restOfHeader () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+4, 0, 32);
    }

    @Override
    public String toString() {
        return "ICMP{" +
                "type=" + type() + "," +
                "code=" + code() +
                '}';
    }

}

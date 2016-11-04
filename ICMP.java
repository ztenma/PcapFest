import java.nio.ByteBuffer;

public class ICMP implements ProtocolSpec {

    public static final String name = "ICMP";
    public static final byte OSILayer = 4;

    private DataFrame frame;
    private ByteBuffer frameBytes;

    private int headerOrigin;

    public ICMP (DataFrame frame, int headerOrigin) {
        this.frame = frame;
        this.headerOrigin = headerOrigin;
    }	

    public static boolean test (ByteBuffer bytes, int offset) {
        return false;
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

}

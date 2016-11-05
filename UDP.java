import java.nio.ByteBuffer;

public class UDP implements ProtocolSpec {

    public static final String name = "UDP";
    public static final byte OSILayer = 4;

    private DataFrame frame;
    private ByteBuffer frameBytes;

    private int headerOrigin;

    public UDP (DataFrame frame, int headerOrigin) {
        this.frame = frame;
        this.frameBytes = frame.bytes();
        this.headerOrigin = headerOrigin;
    }

    public static boolean test (DataFrame frame, int offset) {
        return true;
    }

    public static int headerSize (DataFrame frame, int offset) {
        UDP udp = new UDP(frame, offset);
        return udp.length();
    }

    // 16 bits
    public int srcPort () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin, 0, 16);
    }

    // 16 bits
    public int dstPort () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+2, 0, 16);
    }

    // 16 bits
    public int length () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+4, 0, 16);
    }

    // 16 bits
    public int checksum () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+6, 0, 16);
    }

}

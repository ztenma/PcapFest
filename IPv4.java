import java.nio.ByteBuffer;

public class IPv4 implements ProtocolSpec {

    public static final String name = "IPv4";
    public static final byte OSILayer = 2;

    private DataFrame frame;
    private ByteBuffer frameBytes;

    private int headerOrigin;

    public IPv4 (DataFrame frame, int headerOrigin) {
        this.frame = frame;
        this.frameBytes = frame.getBytes();
        this.headerOrigin = headerOrigin;
    }

    public static boolean test (ByteBuffer bytes, int offset) {
        return false; //TODO 
    }

    public int version () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin, 0, 4);
    }

    public String ihl () {
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

    // TODO : convertir IP en notation pointe
    // 32 bits
    public int srcIP () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+12, 0, 32);
    }

    // 32 bits
    public int dstIP () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+16, 0, 32);
    }
}


import java.nio.ByteBuffer;

public class UnknownProtocol implements ProtocolSpec {

    public static final String name = "Unknown protocol";
    public static final int OSILayer = 0;

    public UnknownProtocol (DataFrame frame, int headerOffset) {
    }

    public static boolean test (ByteBuffer bytes, int offset) {
        return true;
    }

    public static int headerSize (ByteBuffer bytes, int offset) {
        return -1; 
    }

    public static int footerSize (ByteBuffer bytes, int offset) {
        return -1;
    }
}

public class UnknownProtocol extends ProtocolSpec {

    public static final String name = "Unknown protocol";
    public static final int OSILayer = 0;

    public boolean isConform (ByteBuffer bytes, int offset) {
        return true;
    }

    public int headerSize (ByteBuffer bytes, int offset) {
        return -1; 
    }

    public int footerSize (ByteBuffer bytes, int offset) {
        return -1; 
    }
}

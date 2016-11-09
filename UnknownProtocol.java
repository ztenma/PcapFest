import java.nio.ByteBuffer;

public class UnknownProtocol implements ProtocolSpec {

    public static final String name = "UnknownProtocol";
    public static final int OSILayer = 0;

    public UnknownProtocol (DataFrame frame, int headerOffset) {
    }

    public static boolean test (DataFrame frame, int offset) {
        return true;
    }

    public String name () { return name; }

    public int OSILayer () { return OSILayer; }

    public int headerSize (DataFrame frame, int offset) {
        return -1; 
    }

    public int footerSize (DataFrame frame, int offset) {
        return -1;
    }

    public String toString () {
        return name;
    }
    
    public String toPrettyString () {
        return "[" + name + "]\n";
    }
}

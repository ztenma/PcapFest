import java.nio.ByteBuffer;

public interface ProtocolSpec {

    String name ();
    int OSILayer ();
    //boolean test (DataFrame frame, int offset);
    int headerSize (DataFrame frame, int offset);
    //int footerSize (DataFrame frame, int offset);
    public String toPrettyString();
}

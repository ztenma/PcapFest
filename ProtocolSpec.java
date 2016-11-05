import java.nio.ByteBuffer;

public interface ProtocolSpec {
   
    public String name = "UnknownProtocol";
    public int OSILayer = 0;

    //public abstract boolean test (DataFrame frame, int offset);
    public abstract int headerSize (DataFrame frame, int offset);
    //public abstract int footerSize (DataFrame frame, int offset);
}

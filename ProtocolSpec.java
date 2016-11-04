import java.nio.ByteBuffer;

public interface ProtocolSpec {
   
    public String name = "UnknownProtocol";
    public int OSILayer = 0;

    public abstract boolean test (ByteBuffer bytes, int offset);
    public abstract int headerSize (ByteBuffer bytes, int offset);
    //public abstract int footerSize (ByteBuffer bytes, int offset);
}

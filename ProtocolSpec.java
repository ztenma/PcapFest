
public abstract class ProtocolSpec {
   
    public abstract static String name;
    public abstract static int OSILayer;

    public abstract static boolean test (ByteBuffer bytes, int offset);
    public abstract static int headerSize (ByteBuffer bytes, int offset);
    public abstract static int footerSize (ByteBuffer bytes, int offset);
    //public abstract ProtocolSpec extract (byte[] bytes);
}

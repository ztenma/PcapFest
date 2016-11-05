import java.nio.ByteBuffer;

public class DNSRRs implements ProtocolSpec {

    public static final String name = "DNSRRs";
    public static final byte OSILayer = 7;

    private DataFrame frame;
    private ByteBuffer frameBytes;

    private int headerOrigin;

    public DNSRRs (DataFrame frame, int headerOrigin) {
        this.frame = frame;
        this.frameBytes = frame.bytes();
        this.headerOrigin = headerOrigin;
    }

    // ? bits
    /**
    public int name () {
    }
    **/
    
    // 8 bits
    public int type () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin, 0, 8);
    }
    
    // 8 bits
    public int classe () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+1, 0, 8);
    }
    
    // 16 bits
    public int ttl () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+2, 0, 16);
    }
    
    // 8 bits
    public int length () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+4, 0, 8);
    }
    
    // ? bits
    /**
    public int data () {
    }
    **/

    
}


public class DNSRRs extends ProtocolSpec {

    public static final String name = "DNSRRs";
    public static final byte OSILayer = 7;

    private DataFrame frame;
    private ByteBuffer frameBytes;

    private int pduOrigin;
    private int pduLength;
    private int pduTailOrigin;

    public DNSRRs (DataFrame frame, int pduOrigin, int pduLength) {
        this.frame = frame;
        this.frameBytes = frame.getBytes();
        this.pduOrigin = pduOrigin;
        this.pduLength = pduLength;
        this.pduTailOrigin = pduTailOrigin;
    }

    // ? bits
    /**
    public int name () {
    }
    **/
    
    // 8 bits
    public int type () {
        return BinaryUtils.extractInt(frame, pduTailOrigin, 0, 8);
    }
    
    // 8 bits
    public int classe () {
        return BinaryUtils.extractInt(frame, pduTailOrigin+1, 0, 8);
    }
    
    // 16 bits
    public int ttl () {
        return BinaryUtils.extractInt(frame, pduTailOrigin+2, 0, 16);
    }
    
    // 8 bits
    public int length () {
        return BinaryUtils.extractInt(frame, pduTailOrigin+4, 0, 8);
    }
    
    // ? bits
    /**
    public int data () {
    }
    **/

    
}

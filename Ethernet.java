
public class EthernetII extends ProtocolSpec {

    public static final String name = "EthernetII";
    public static final byte OSILayer = 2;

    private DataFrame frame;
    private ByteBuffer frameBytes;

    // For MAC Header
    private int pduOrigin;
    private int pduLength;

    // For Checksum
    private int pduTailOrigin;
    private int pduTailLength;

    public EthernetII (DataFrame frame, int pduOrigin, int pduLength, int pduTailOrigin, int pduTailLength) {
        this.frame = frame;
        this.frameBytes = frame.getBytes();
        this.pduOrigin = pduOrigin;
        this.pduLength = pduLength;
        this.pduTailOrigin = pduTailOrigin;
        this.pduTailLength = pduTailLength;
    }

    public String dstMAC() {
        return BinaryUtils.extractMACAddress(frame, pduOrigin, 6);
    }
    
    public String srcMAC() {
        return BinaryUtils.extractMACAddress(frame, pduOrigin + 6, 6);
    }

    public int etherType () {
        return BinaryUtils.extractIntByte(frame, pduOrigin + 12, 2);
    }

    public int checksum () {
        return BinaryUtils.extractIntByte(frame, pduTailOrigin, 4);
    }
}

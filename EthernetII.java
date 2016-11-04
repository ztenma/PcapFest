
public class EthernetII extends ProtocolSpec {

    public static final String name = "EthernetII";
    public static final byte OSILayer = 2;

    private DataFrame frame;
    private ByteBuffer frameBytes;

    // For MAC Header
    private int headerOrigin;

    // For Checksum
    private int footerOrigin;

    public static int headerSize (DataFrame frame, int offset) {
        return 14;
    }

    public static int footerSize (DataFrame frame, int offset) {
        return 4; // TODO: padding?
    }

    public EthernetII (DataFrame frame, int headerOrigin, int footerOrigin) {
        this.frame = frame;
        this.frameBytes = frame.getBytes();
        this.headerOrigin = headerOrigin;
        this.footerOrigin = footerOrigin;
    }
    
    public void setFooterOrigin (int offset) {
        this.footerOrigin = offset;
    }

    public String dstMAC () {
        return BinaryUtils.extractMACAddress(frame, headerOrigin, 6);
    }
    
    public String srcMAC () {
        return BinaryUtils.extractMACAddress(frame, headerOrigin + 6, 6);
    }

    public int etherType () {
        return BinaryUtils.extractIntByte(frame, headerOrigin + 12, 2);
    }

    public int checksum () {
        return BinaryUtils.extractIntByte(frame, footerOrigin, 4);
    }
}

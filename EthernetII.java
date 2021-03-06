import java.nio.ByteBuffer;

public class EthernetII implements ProtocolSpec {

    public static final String name = "EthernetII";
    public static final byte OSILayer = 2;

    private ByteBuffer frameBytes;

    // For MAC Header
    private int headerOrigin;

    // For Checksum
    private int footerOrigin;

    public EthernetII (DataFrame frame, int headerOrigin) {
        this.frameBytes = frame.bytes();
        this.headerOrigin = headerOrigin;
        this.footerOrigin = -1;
    }

    public static boolean test (DataFrame frame, int offset) {
        return true;
    }

    public String name () { return name; }

    public int OSILayer () { return OSILayer; }

    public int headerSize (DataFrame frame, int offset) { return 14; }

    public int footerSize (DataFrame frame, int offset) {
        return 4; // TODO: padding?
    }

    public void setFooterOrigin (int offset) {
        this.footerOrigin = offset;
    }

    public String dstMAC () {
        return BinaryUtils.extractMACAddress(frameBytes, headerOrigin, 6);
    }
    
    public String srcMAC () {
        return BinaryUtils.extractMACAddress(frameBytes, headerOrigin + 6, 6);
    }

    public int etherType () {
        return BinaryUtils.extractIntByte(frameBytes, headerOrigin + 12, 2);
    }

    public int checksum () {
        return BinaryUtils.extractIntByte(frameBytes, footerOrigin, 4);
    }

    @Override
    public String toString() {
        return "EthernetII{" +
                "srcMAC=" + srcMAC() +
                ",dstMAC=" + dstMAC() +
                '}';
    }
    
    public String toPrettyString() {
        return "[EthernetII]\n" +
                "srcMAC = " + srcMAC() + "\n" +
                "dstMAC = " + dstMAC() + "\n" +
                "etherType = " + etherType() + "\n";
    }
}

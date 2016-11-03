
public class ARP extends ProtocolSpec {

    public static final String name = "ARP";
    public static final byte OSILayer = 2;

    private DataFrame frame;
    private ByteBuffer frameBytes;

    private int headerOrigin;

    public ARP (DataFrame frame, int headerOrigin) {
        this.frame = frame;
        this.frameBytes = frame.getBytes();
        this.headerOrigin = headerOrigin
    }

    // 16 bits
    public int hwType () {
        return BinaryUtils.extractInt(frame, headerOrigin, 0, 16);
    }

    // 16 bits
    public int protoType () {
        return BinaryUtils.extractInt(frame, headerOrigin+2, 0, 16);
    }	

    // 8 bits
    public int hwAddrLength () {
        return BinaryUtils.extractInt(frame, headerOrigin+4, 0, 8);
    }

    // 8 bits
    public int protoAddrLength () {
        return BinaryUtils.extractInt(frame, headerOrigin+5, 0, 8);
    }

    // 16 bits
    public int operation () {
        return BinaryUtils.extractInt(frame, headerOrigin+6, 0, 16);
    }

    // TODO : convertir IP
    // 48 bits = 6 octets
    public String srcMAC () {
        return extractMACAddress(frame, headerOrigin+8, 6);
    }

    // 32 bits
    public int srcIP () {
        return BinaryUtils.extractInt(frame, headerOrigin+14, 0, 32);
    }

    // 48 bits
    public String dstMAC () {
        return extractMACAddress(frame, headerOrigin+18, 6);
    }

    // 32 bits
    public int dstIP () {
        return BinaryUtils.extractInt(frame, headerOrigin+24, 0, 32);
    }
}

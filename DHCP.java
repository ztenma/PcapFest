import java.nio.ByteBuffer;

public class DHCP implements ProtocolSpec {

    public static final String name = "DHCP";
    public static final byte OSILayer = 7;

    private DataFrame frame;
    private ByteBuffer frameBytes;

    private int headerOrigin;

    public DHCP (DataFrame frame, int headerOrigin) {
        this.frame = frame;
        this.frameBytes = frame.bytes();
        this.headerOrigin = headerOrigin;
    }

    // http://repository.root-me.org/R%C3%A9seau/FR%20-%20Les%20r%C3%A9seaux%20Ethernet%20-%20le%20format%20des%20trames.pdf

    public static boolean test (DataFrame frame, int offset) {
        return true;
    }
    public String name () { return name; }

    public int OSILayer () { return OSILayer; }

    public int headerSize (DataFrame frame, int offset) {
        return frame.length() - offset;
    }

    // 8 bits
    public int op () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin, 0, 8);
    }

    // 8 bits
    public int htype () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+1, 0, 8);
    }

    // 8 bits
    public int hlen () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+2, 0, 8);
    }

    // 8 bits
    public int hops () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+3, 0, 8);
    }

    // 32 bits
    public int xid () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+4, 0, 32);
    }

    // 16 bits
    public int secs () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+8, 0, 16);
    }

    // 16 bits
    public int flags () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+10, 0, 16);
    }

    // 32 bits
    public String clientIP () {
        return BinaryUtils.extractIPv4Address(frameBytes, headerOrigin+12, 4);
    }

    // 32 bits
    public String yourIP () {
        return BinaryUtils.extractIPv4Address(frameBytes, headerOrigin+16, 4);
    }

    // 32 bits
    public String serverIP () {
        return BinaryUtils.extractIPv4Address(frameBytes, headerOrigin+20, 4);
    }

    // 32 bits
    public String gatewayIP () {
        return BinaryUtils.extractIPv4Address(frameBytes, headerOrigin+24, 4);
    }

    // 128 bits
    public String clientMAC () {
        return BinaryUtils.extractMACAddress(frameBytes, headerOrigin+28, 6);
    }

    // OPTIONEL
    /*
    // 512 bits
    public int serverName () {
    return BinaryUtils.extractInt(frameBytes, headerOrigin+44, 0, 512);
    }

    // 1024 bits
    public int fileName () {
    return BinaryUtils.extractInt(frameBytes, headerOrigin+108, 0, 1024);
    }
     */
    
    @Override
    public String toString() {
        return "DHCP{" +
                "operation=" + op() + "," +
                "flags=" + flags() + "," +
                "yourIP=" + yourIP() + "," +
                "serverIP=" + serverIP() + "," +
                "gatewayIP=" + gatewayIP() + "," +
                "clientMAC=" + clientMAC() +
                '}';
    }
    
    public String toPrettyString() {
        return "[DHCP]\n" +
                "operation = " + op() + "\n" +
                "flags = " + flags() + "\n" +
                "yourIP = " + yourIP() + "\n" +
                "serverIP = " + serverIP() + "\n" +
                "gatewayIP = " + gatewayIP() + "\n" +
                "clientMAC = " + clientMAC() + "\n";
    }
}

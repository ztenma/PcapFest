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

    public int headerSize (DataFrame frame, int offset) {

        return -1; // TODO
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
    public int clientIP () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+12, 0, 32);
    }

    // 32 bits
    public int yourIP () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+16, 0, 32);
    }

    // 32 bits
    public int serverIP () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+20, 0, 32);
    }

    // 32 bits
    public int gatewayIP () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+24, 0, 32);
    }

    // 128 bits
    public int clientMAC () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin+28, 0, 128);
    }

    // OPTIONEL
    /**
    // 512 bits
    public int serverName () {
    return BinaryUtils.extractInt(frameBytes, headerOrigin+44, 0, 512);
    }

    // 1024 bits
    public int fileName () {
    return BinaryUtils.extractInt(frameBytes, headerOrigin+108, 0, 1024);
    }
     **/
    @Override
    public String toString() {
        return "DHCP{" +
                "yourIP=" + yourIP() + "," +
                "serverIP=" + serverIP() + "," +
                "gatewayIP=" + gatewayIP() + "," +
                "clientMAC=" + clientMAC() +
                '}';
    }
}

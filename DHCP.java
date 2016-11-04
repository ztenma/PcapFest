
public class DHCP extends ProtocolSpec {

    public static final String name = "DHCP";
    public static final byte OSILayer = 7;

    private DataFrame frame;
    private ByteBuffer frameBytes;

    private int headerOrigin;

    public DHCP (DataFrame frame, int headerOrigin) {
        this.frame = frame;
        this.frameBytes = frame.getBytes();
        this.headerOrigin = headerOrigin;
    }

    // http://repository.root-me.org/R%C3%A9seau/FR%20-%20Les%20r%C3%A9seaux%20Ethernet%20-%20le%20format%20des%20trames.pdf

    // 8 bits
    public int op () {
        return BinaryUtils.extractInt(frame, headerOrigin, 0, 8);
    }

    // 8 bits
    public int htype () {
        return BinaryUtils.extractInt(frame, headerOrigin+1, 0, 8);
    }

    // 8 bits
    public int hlen () {
        return BinaryUtils.extractInt(frame, headerOrigin+2, 0, 8);
    }

    // 8 bits
    public int hops () {
        return BinaryUtils.extractInt(frame, headerOrigin+3, 0, 8);
    }

    // 32 bits
    public int xid () {
        return BinaryUtils.extractInt(frame, headerOrigin+8, 0, 32);
    }

    // 16 bits
    public int secs () {
        return BinaryUtils.extractInt(frame, headerOrigin+12, 0, 16);
    }

    // 16 bits
    public int flags () {
        return BinaryUtils.extractInt(frame, headerOrigin+14, 0, 16);
    }

    // 32 bits
    public int clientIP () {
        return BinaryUtils.extractInt(frame, headerOrigin+16, 0, 32);
    }

    // 32 bits
    public int yourIP () {
        return BinaryUtils.extractInt(frame, headerOrigin+20, 0, 32);
    }

    // 32 bits
    public int serverIP () {
        return BinaryUtils.extractInt(frame, headerOrigin+24, 0, 32);
    }

    // 32 bits
    public int gatewayIP () {
        return BinaryUtils.extractInt(frame, headerOrigin+28, 0, 32);
    }

    // 128 bits
    public int clientMAC () {
        return BinaryUtils.extractInt(frame, headerOrigin+32, 0, 128);
    }

    // OPTIONEL
    /**
    // 512 bits
    public int serverName () {
    return BinaryUtils.extractInt(frame, headerOrigin+48, 0, 512);
    }

    // 1024 bits
    public int fileName () {
    return BinaryUtils.extractInt(frame, headerOrigin+112, 0, 1024);
    }
     **/

}

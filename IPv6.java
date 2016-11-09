import java.nio.ByteBuffer;

public class IPv6 implements ProtocolSpec {

    public static final String name = "IPv6";
    public static final byte OSILayer = 2;

    private DataFrame frame;
    private ByteBuffer frameBytes;


    private int headerOrigin;


    public IPv6 (DataFrame frame, int headerOrigin) {
        this.frame = frame;
        this.frameBytes = frame.bytes();
        this.headerOrigin = headerOrigin;
    }

    public static boolean test (DataFrame frame, int offset) {
        return true;
    }

    public String name () { return name; }

    public int OSILayer () { return OSILayer; }

    public int headerSize (DataFrame frame, int offset) {
        return 40;
    }

	// 4 bits
	public int version () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin, 0, 4);
    }

	// 8 bits
    public int trafficClass () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin, 4, 8);
    }
	
	// 20 bits
    public int flowLabel () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin +1, 4, 20);
    }

	// 16 bits
    public int payloadLenght () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin +4, 0, 16);
    }
	
	// 8 bits
    public int nextHeader () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin +6, 0, 8);
    }

	// 8 bits
    public int hopLimit () {
        return BinaryUtils.extractInt(frameBytes, headerOrigin +7, 0, 8);
    }

	
	// 128 bits
	public String srcIP () {
		return BinaryUtils.extractIPv6Address(frameBytes, headerOrigin +8, 16);
	}
	
	// 128 bits
	public String dstIP () {
		return BinaryUtils.extractIPv6Address(frameBytes, headerOrigin +24, 16);
	}

    public String toString () {
        return "IPv6{" + 
            "srcIP=" + srcIP() + "," +
            "dstIP=" + dstIP() + "," +
            "ttl=" + hopLimit() + "}";
    }
    
    public String toPrettyString () {
        return "[IPv6]\n" + 
            "srcIP = " + srcIP() + "\n" +
            "dstIP = " + dstIP() + "\n" +
            "ttl = " + hopLimit() + "\n";
    }
}

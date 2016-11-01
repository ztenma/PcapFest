
public class IPv6 extends ProtocolSpec {

    public static final String name = "IPv6";
    public static final byte OSILayer = 2;

    private DataFrame frame;
    private ByteBuffer frameBytes;


    private int pduOrigin;
    private int pduLength;


    public IPv6 (DataFrame frame, int pduOrigin, int pduLength) {
        this.frame = frame;
        this.frameBytes = frame.getBytes();
        this.pduOrigin = pduOrigin;
        this.pduLength = pduLength;
    }
 
	// 4 bits
	public int version () {
        return BinaryUtils.extractInt(frame, pduOrigin, 0, 4);
    }

	// 8 bits
    public int trafficClass () {
        return BinaryUtils.extractInt(frame, pduOrigin, 4, 8);
    }
	
	// 20 bits
    public int flowLabel () {
        return BinaryUtils.extractInt(frame, pduOrigin+1, 4, 20);
    }

	// 16 bits
    public int payloadLenght () {
        return BinaryUtils.extractInt(frame, pduOrigin+4, 0, 16);
    }
	
	// 8 bits
    public int nextHeader () {
        return BinaryUtils.extractInt(frame, pduOrigin+6, 0, 8);
    }

	// 8 bits
    public int hopLimit () {
        return BinaryUtils.extractInt(frame, pduOrigin+7, 0, 8);
    }

	
	// 128 bits
	public int srcIPv6 () {
		return BinaryUtils.extractInt(frame, pduOrigin+8, 0, 128);
	}
	
	// 128 bits
	public int dstIPv6 () {
		return BinaryUtils.extractInt(frame, pduOrigin+24, 0, 128);
	}
	
}

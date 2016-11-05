import java.nio.ByteBuffer;

public class IPv6 implements ProtocolSpec {

    public static final String name = "IPv6";
    public static final byte OSILayer = 2;

    private DataFrame frame;
    private ByteBuffer frameBytes;


    private int headerOrigin;


    public IPv6 (DataFrame frame, int pduOrigin, int pduLength) {
        this.frame = frame;
        this.frameBytes = frame.bytes();
        this.headerOrigin = pduOrigin;
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
	public int srcIPv6 () {
		return BinaryUtils.extractInt(frameBytes, headerOrigin +8, 0, 128);
	}
	
	// 128 bits
	public int dstIPv6 () {
		return BinaryUtils.extractInt(frameBytes, headerOrigin +24, 0, 128);
	}
	
}

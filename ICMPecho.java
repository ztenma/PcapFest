
public class ICMP implements ProtocolSpec {

    public static final String name = "ICMP";
    public static final byte OSILayer = 4;

    private DataFrame frame;
    private ByteBuffer frameBytes;

    private int pduOrigin;
    private int pduLength;

    public ICMP (DataFrame frame, int pduOrigin, int pduLength) {
        this.frame = frame;
        this.frameBytes = frame.getBytes();
        this.pduOrigin = pduOrigin;
        this.pduLength = pduLength;
    }
	
	// 8 bits
	public int type () {
		return BinaryUtils.extractInt(frameBytes, pduOrigin, 0, 8);
	}
	
	// 8 bits
	public int code () {
		return BinaryUtils.extractInt(frameBytes, pduOrigin+1, 0, 8);
	}
	
	// 16 bits
	public int checksum () {
		return BinaryUtils.extractInt(frameBytes, pduOrigin+2, 0, 16);
	}
	
	// 16 bits
	public int identifier () {
		return BinaryUtils.extractInt(frameBytes, pduOrigin+4, 0, 16);
	}
	
	// 16 bits
	public int seqNumber () {
		return BinaryUtils.extractInt(frameBytes, pduOrigin+8, 0, 16);
	}
	
	// 32 bits
	public int payload () {
		return BinaryUtils.extractInt(frameBytes, pduOrigin+10, 0, 32);
	}
	
}

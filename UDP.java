
public class UDP extends ProtocolSpec {

    public static final String name = "UDP";
    public static final byte OSILayer = 4;

    private DataFrame frame;
    private ByteBuffer frameBytes;


    private int pduOrigin;
    private int pduLength;


    public UDP (DataFrame frame, int pduOrigin, int pduLength) {
        this.frame = frame;
        this.frameBytes = frame.getBytes();
        this.pduOrigin = pduOrigin;
        this.pduLength = pduLength;
    }
	
	// 16 bits
	public int srcPort () {
		return BinaryUtils.extractInt(frame, pduOrigin, 0, 16);
	}
	
	// 16 bits
	public int dstPort () {
		return BinaryUtils.extractInt(frame, pduOrigin+2, 0, 16);
	}
	
	// 16 bits
	public int length () {
		return BinaryUtils.extractInt(frame, pduOrigin+4, 0, 16);
	}
	
	// 16 bits
	public int checksum () {
		return BinaryUtils.extractInt(frame, pduOrigin+6, 0, 16);
	}
	
}

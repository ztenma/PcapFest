
public class ARP extends ProtocolSpec {

    public static final String name = "ARP";
    public static final byte OSILayer = 3;

    private DataFrame frame;
    private ByteBuffer frameBytes;

    private int pduOrigin;
    private int pduLength;

    public ARP (DataFrame frame, int pduOrigin, int pduLength) {
        this.frame = frame;
        this.frameBytes = frame.getBytes();
        this.pduOrigin = pduOrigin;
        this.pduLength = pduLength;
    }
	
	// 16 bits
	public int hdwareType () {
        return BinaryUtils.extractInt(frame, pduOrigin, 0, 16);
    }

	// 16 bits
	public int protoType () {
        return BinaryUtils.extractInt(frame, pduOrigin+2, 0, 16);
    }	
	
	// 8 bits
	public int hdwareAddrLenght () {
		return BinaryUtils.extractInt(frame, pduOrigin+4, 0, 8);
	}
	
	// 8 bits
	public int protoAddrLength () {
		return BinaryUtils.extractInt(frame, pduOrigin+5, 0, 8);
	}
	
	// 16 bits
	public int operation () {
		return BinaryUtils.extractInt(frame, pduOrigin+6, 0, 16);
	}
	
	// TODO : convertir IP
	// 48 bits = 6 octets
	public String srcMAC () {
		return extractMACAddress(frame, pduOrigin+8, 6);
	}
	
	// 32 bits
	public int srcIP () {
		return BinaryUtils.extractInt(frame, pduOrigin+14, 0, 32);
	}
	
	// 48 bits
	public String dstMAC () {
		return extractMACAddress(frame, pduOrigin+18, 6);
	}
	
	// 32 bits
	public int dstIP () {
		return BinaryUtils.extractInt(frame, pduOrigin+24, 0, 32);
	}
}

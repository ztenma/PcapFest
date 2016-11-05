import java.nio.ByteBuffer;

public class DNS implements ProtocolSpec {

    public static final String name = "DNS";
    public static final byte OSILayer = 7;

    private DataFrame frame;
    private ByteBuffer frameBytes;

    private int pduOrigin;

    public DNS (DataFrame frame, int pduOrigin) {
        this.frame = frame;
        this.frameBytes = frame.bytes();
        this.pduOrigin = pduOrigin;
    }


    public static boolean test (DataFrame frame, int offset) {
        //http://stackoverflow.com/questions/7565300/identifying-dns-packets
        DNS dns = new DNS(frame, offset);
        int flags = dns.flags();
        if (dns.questionsNumber() == 1) {
            if ((flags & 0x8000) == 0 // query
                && (flags & 0x000F) == 0 // RCODE == 0
                && dns.answersRRNumber() == 0
                && dns.autorityRRNumber() == 0)
                    return true;
            if ((flags & 0x8000) == 0x8000 // response
                && dns.answersRRNumber() == 1)
                    return true;
        }
        return true;
    }
    // http://repository.root-me.org/R%C3%A9seau/FR%20-%20Les%20r%C3%A9seaux%20Ethernet%20-%20le%20format%20des%20trames.pdf

    public int headerSize (DataFrame frame, int offset) {
        return 12;
    }

    // 16 bits
    public int transactionID () {
        return BinaryUtils.extractInt(frameBytes, pduOrigin, 0, 16);
    }
    
    // 16 bits
    public int flags () {
        return BinaryUtils.extractInt(frameBytes, pduOrigin+2, 0, 16);
    }
    
    // 16 bits
    public int questionsNumber () {
        return BinaryUtils.extractInt(frameBytes, pduOrigin+4, 0, 16);
    }
    
    // 16 bits
    public int answersRRNumber () {
        return BinaryUtils.extractInt(frameBytes, pduOrigin+6, 0, 16);
    }
    
    // 16 bits
    public int autorityRRNumber () {
        return BinaryUtils.extractInt(frameBytes, pduOrigin+8, 0, 16);
    }
    
    // 16 bits
    public int additionalRRNumber () {
        return BinaryUtils.extractInt(frameBytes, pduOrigin+10, 0, 16);
    }

    @Override
    public String toString() {
        return "DNS{" +
                "QUERY code=" + (flags() & 0x8000) + "," +
                "RESPOND code=" + (flags() & 0x8000) +
                '}';
    }
}

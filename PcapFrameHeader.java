
public class PcapFrameHeader {

    public int timestampSeconds;
    public int timestampMicroseconds;
    public int includedLength;
    public int originalLength; // included != original -> frame greater than snaplen

    /* Parse libpcap global header */
    public PcapFrameHeader (byte[] bytes) {
       timestampSeconds =      BinaryUtils.extractInt(bytes, 0,  4); 
    }
/*
    public void extract (byte[] bytes) {
       timestampSeconds =      BinaryUtils.extractInt(bytes, 0,  4); 
       timestampMicroseconds = BinaryUtils.extractInt(bytes, 4,  4); 
       includedLength =        BinaryUtils.extractInt(bytes, 8,  4); 
       originalLength =        BinaryUtils.extractInt(bytes, 12, 4); 
    }*/
}


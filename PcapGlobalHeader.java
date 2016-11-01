
public class PcapGlobalHeader {

    public int magicNumber;
    public short versionMajor;
    public short versionMinor;
    public int timeZone;
    public int timestampAccuracy;
    public int snapshotLength;
    public int linkLayerType; // 1: Ethernet -> http://www.tcpdump.org/linktypes.html

    /* Parse libpcap global header */
    public PcapGlobalHeader (byte[] bytes) {
    
    }
/*
    public void extract (byte[] bytes) {
       magicNumber =       extractInt(bytes, 0,  4); 
       versionMajor =      extractInt(bytes, 4,  2); 
       versionMinor =      extractInt(bytes, 6,  2); 
       timeZone =          extractInt(bytes, 8,  4); 
       timestampAccuracy = extractInt(bytes, 12, 4); 
       snapshotLength =    extractInt(bytes, 16, 4); 
       linkLayerType =     extractInt(bytes, 20, 4);
    }*/
}


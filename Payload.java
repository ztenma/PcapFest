import javax.xml.bind.DatatypeConverter;

public class Payload implements ProtocolSpec {

    private byte[] data;
    private int length;

    public Payload (DataFrame frame, int offset) {
        this.length = frame.bytes().position(offset).remaining();//frame.length() - offset;
        this.data = new byte[length];
        System.out.println("Offset: " + offset + ", Length: " + length);
        frame.bytes().get(this.data, offset, frame.bytes().position(offset).remaining());
    }

    public String name () {
        return "Payload";
    }

    public int OSILayer () {
        return 0;
    }

    public byte[] getData() {
        return this.data;
    }

    public String hexData () {
        return DatatypeConverter.printHexBinary(this.data);
    }

    public int headerSize (DataFrame frame, int offset) {
        return length;
    }

    public String toString () {
        return "Payload{}";
    }

    public String toPrettyString () {
        return "[Payload]\n" + hexData();
    }
}

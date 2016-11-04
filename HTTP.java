import java.nio.ByteBuffer;
import java.util.List;
import java.util.ArrayList;

public class HTTP implements ProtocolSpec {

    public static final String name = "HTTP";
    public static final byte OSILayer = 7;

    private DataFrame frame;
    private ByteBuffer frameBytes;

    private int headerOrigin;

    public HTTP (DataFrame frame, int headerOrigin) {
        this.frame = frame;
        this.frameBytes = frame.getBytes();
        this.headerOrigin = headerOrigin;
    }

    public static int headerSize () {
        return this.bytes.limit() - this.headerOffset;
    }

    public static boolean test (ByteBuffer bytes, int offset) {
        return new HTTP(bytes, offset).initialLine().indexOf("HTTP/") != -1;
    }

    // 16 bits
    public String initialLine () {
        int i;
        for (i = 0; i < this.headerSize() && bytes.get(i) != '\r'; i++);
        return bytes.subSequence(0, i).toString();
    }

    public boolean isRequest () {
        return false;
    }
    
    public boolean isResponse () {
        return false;
    }

    public List<String> headers () {
        return null;
    }

    public String body () {
        return null;
    }
}

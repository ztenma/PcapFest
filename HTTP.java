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
        this.frameBytes = frame.bytes();
        this.headerOrigin = headerOrigin;
    }

    public static int headerSize (DataFrame frame, int offset) {
        HTTP http = new HTTP(frame, offset);
        return frame.bytes().limit() - offset;
    }

    public static boolean test (DataFrame frame, int offset) {
        return new HTTP(frame, offset).initialLine().contains("HTTP/");
    }

    // 16 bits
    public String initialLine () {
        int i;
        for (i = 0; i < headerSize(frame, headerOrigin) && frameBytes.get(i) != '\r'; i++);
        return frameBytes.asCharBuffer().subSequence(0, i).toString();
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

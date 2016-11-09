import java.nio.ByteBuffer;
import java.nio.charset.Charset;
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

    public static boolean test (DataFrame frame, int offset) {
        String text = convertBytesToString(frame.bytes(), offset);
        int initialLineEnd = text.indexOf("\r\n");
        if (initialLineEnd != -1)
            return new HTTP(frame, offset).initialLine().matches(".*HTTP/[0-9]\\.[0-9].*");
        return false;
    }

    public String name () { return name; }

    public int OSILayer () { return OSILayer; }

    public int headerSize (DataFrame frame, int offset) {
        String text = convertBytesToString(this.frameBytes, this.headerOrigin);
        return text.indexOf("\r\n\r\n") + 4;
    }

    public static String convertBytesToString (ByteBuffer bytes, int offset) {
        return new String(bytes.array(), offset, bytes.limit() - offset, Charset.forName("UTF-8"));
    }

    public String initialLine () {
        String text = convertBytesToString(this.frameBytes, this.headerOrigin);
        int initialLineEnd = text.indexOf("\r\n");
        return text.substring(0, initialLineEnd);
    }

    public boolean isRequest () {
        return this.initialLine().matches("[a-zA-Z]{3,8} +[^ ]+ +HTTP/[0-9]\\.[0-9]");
    }
    
    public boolean isResponse () {
        return initialLine().matches("HTTP/[0-9]\\.[0-9] +");
    }

    public String[] headers () {
        String text = convertBytesToString(this.frameBytes, this.headerOrigin);
        return text.substring(0, this.headerSize(null, 0)).split("\r\n");
    }

    public String body () {
        return null;
    }

    @Override
    public String toString() {
        return "HTTP{" +
                "InitialLine=" + initialLine() +
                '}';
    }
}

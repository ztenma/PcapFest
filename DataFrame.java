import java.nio.ByteBuffer;
import java.util.ArrayList;

public class DataFrame {
    
    private static currentId = 0;
    
    private int id;
    private int length;
    private String timestamp;

    private ByteBuffer bytes;

    private ArrayList<ProtocolSpec> layers;

    public DataFrame (ByteBuffer frameBytes, int id, int timestamp) {
        this.id = currentId++;
        this.length = frameBytes.limit();
        this.timestamp = timestamp;
        this.bytes = frameBytes;//ByteBuffer.wrap(frameBytes).asReadOnlyBuffer();
        this.layers = new ArrayList<ProtocolSpec>();
    }

    public ProtocolSpec layer (String layerName) {
        for (ProtocolSpec proto : this.layers) {
            if (proto.name.equals(layerName))
                return proto;
        }
    }

    public ByteBuffer bytes () {
        return this.bytes;
    }

    public int id () {
        return this.id;
    }

    public int length () {
        return this.length;
    }

    public int timestamp () {
        return this.timestamp;
    }
}

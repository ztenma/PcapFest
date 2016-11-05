import java.nio.ByteBuffer;
import java.util.List;
import java.util.ArrayList;

public class DataFrame {
    
    private static int currentId = 0;
    
    private int id;
    private int length;
    private int timestamp;

    private ByteBuffer bytes;

    private ArrayList<ProtocolSpec> layers;

    public DataFrame (ByteBuffer frameBytes) {
        this.id = currentId++;
        this.length = frameBytes.limit();
        this.timestamp = 0;
        this.bytes = frameBytes;//ByteBuffer.wrap(frameBytesBytes).asReadOnlyBuffer();
        this.layers = new ArrayList<ProtocolSpec>();
    }

    public void setLayers (List<ProtocolSpec> layers) {
        this.layers = layers;
    }

    public List<ProtocolSpec> layers () {
        return this.layers;
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

    public String toString() {

    }
}

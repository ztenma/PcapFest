import java.nio.ByteBuffer;
import java.util.List;
import java.util.ArrayList;

public class DataFrame {
    
    private static int currentId = 0;
    
    private int id;
    private int length;
    private int timestamp;

    private ByteBuffer bytes;

    private List<ProtocolSpec> layers;

    public DataFrame (ByteBuffer frameBytes) {
        this.id = currentId++;
        this.length = frameBytes.limit();
        this.timestamp = 0;
        this.bytes = frameBytes;
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
        return null;
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
        StringBuilder s = new StringBuilder();
        s.append("Frame{id=" + id + ",length=" + length + ",layers={");
        for (ProtocolSpec proto : this.layers)
            s.append(proto.toString()).append(",");
        s.append("}}");
        return s.toString();
    }
}

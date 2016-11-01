import java.nio.ByteBuffer;
import java.util.ArrayList;

public class DataFrame {
    
    private int id;
    private int length;
    private String timestamp;

    private ByteBuffer bytes;

    private ArrayList<ProtocolSpec> layers;

    public DataFrame (byte[] frameBytes) {
        this.bytes = ByteBuffer.wrap(frameBytes).asReadOnlyBuffer();
        this.layers = new ArrayList<ProtocolSpec>();
    }

    public ProtocolSpec getLayer (String layerName) {
        for (ProtocolSpec proto : this.layers) {
            if (proto.name.equals(layerName))
                return proto;
        }
    }

    public ByteBuffer getBytes () {
        return this.bytes;
    }

}

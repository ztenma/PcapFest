

public class BinaryUtils {
    

    /* Extract 'len' bytes from 'bytes' starting at 'offset', decoding it as an integer 
     * of size equal to 'len'.
     * It decodes data as big endian. All frame data in pcap format are big endian. */
    /*public static int extractInt(byte[] bytes, int offset, int len) {
        int val = 0;
        for (int i = 0; i < len; i++) {
            val |= (bytes[offset + len - 1 - i] & 0xff) << i * 8;
        }
        return val;
    }*/

    public static int extractIntByte(ByteBuffer bytes, int byteOffset, int byteLen) {
        return extractInt(bytes, byteOffset, 0, byteLen*8);
    }

    public static int extractInt(ByteBuffer bytes, int byteOffset, int bitOffset, int bitLen) {
        if (bitOffset < 0 || bitOffset >= 32 || bitLen < 0 || bitLen >= 32)
            throw new BinaryArithmeticException();
        int val = bytes.position(byteOffset).getInt();
        if (bitOffset != 0 && bitLen != 0) {
            int mask = (0xFFFFFFFF << 8 - bitLen) >>> bitOffset;
            val = val & mask;
        }
        return val;
    }

    public static String extractHexString(byte[] bytes, int offset, int len) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < len; i++) {
            s.append(String.format("%02X", bytes[offset + i]));
        }
        return s.toString();
    }

    public static String extractMACAddress(byte[] bytes, int offset, int len) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < len; i++) {
            s.append(String.format("%02X:", bytes[offset + i]));
        }
        return s.deleteCharAt(len-1).toString();
    }

    public static void main (String[] args) {
        byte b = 5;
        byte[] bb = {0, 1, 0, 4};
        System.out.println(Integer.class.isInstance(b) + " " + Integer.class.isInstance(b & 0xff));
        System.out.println(extractInt(bb, 0, 4));
    }
}

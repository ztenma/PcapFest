import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.xml.bind.DatatypeConverter;

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

    public static int extractIntByte (ByteBuffer bytes, int byteOffset, int byteLen) {
        return extractInt(bytes, byteOffset, 0, byteLen*8, false);
    }

    public static int extractIntByte (ByteBuffer bytes, int byteOffset, int byteLen, boolean littleEndian) {
        return extractInt(bytes, byteOffset, 0, byteLen*8, littleEndian);
    }

    public static int extractInt (ByteBuffer bytes, int byteOffset, int bitOffset, int bitLen) {
        return extractInt(bytes,byteOffset, bitOffset, bitLen, false);
    }

    public static int extractInt (ByteBuffer bytes, int byteOffset, int bitOffset, int bitLen, boolean littleEndian) {
        if (bitOffset < 0 || bitOffset >= 32 || bitLen < 0 || bitLen > 32)
            return 0;
        ByteBuffer buf = (ByteBuffer)bytes.position(byteOffset);
        ByteOrder endianness = (littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);
        int val = buf.order(endianness).getInt();
        if (!(bitOffset == 0 && bitLen == 32)) {
            int mask = (0xFFFFFFFF << 32 - bitLen) >>> bitOffset;
            int finalOffset = (32 - bitOffset - bitLen);
            val = (val & mask) >>> finalOffset;
        }
        return val;
    }

    /*public static String extractHexString (byte[] bytes, int offset, int len) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < len; i++) {
            s.append(String.format("%02X", bytes[offset + i]));
        }
        return s.toString();
    }*/
    
    public static String extractHexString (ByteBuffer bytes, int offset, int len) {
        return DatatypeConverter.printHexBinary(bytes.array());
    }

    public static String extractMACAddress (ByteBuffer bytes, int offset, int len) {
        byte[] bytearray = bytes.array();
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < len; i++) {
            s.append(String.format("%02X:", bytearray[offset + i]));
        }
        return s.deleteCharAt(s.length()-1).toString();
    }

    public static String extractIPv4Address (ByteBuffer bytes, int offset, int len) {
        byte[] bytearray = bytes.array();
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < len; i++) {
            s.append(String.format("%d.", bytearray[offset + i] & 0xFF));
        }
        return s.deleteCharAt(s.length()-1).toString();
    }

    public static String extractIPv6Address (ByteBuffer bytes, int offset, int len) {
        byte[] bytearray = bytes.array();
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < len/2; i++) {
            s.append(String.format("%02X%02X:", bytearray[offset + 2*i], bytearray[offset + 2*i + 1]));
        }
        return s.deleteCharAt(s.length()-1).toString();
    }
    
    public static String toHexString (byte[] bytes, boolean withAscii) {
        return toHexString(bytes, withAscii, 0, bytes.length);
    }

    public static String toHexString (byte[] bytes, boolean withAscii, int offsetMin, int offsetMax) {
        StringBuilder hexStr = new StringBuilder();
        StringBuilder asciiStr = new StringBuilder();
        
        int i, bytesCount = offsetMax - offsetMin, rowWidth = 16;
        for (i = 0; i < bytesCount; i++) {
            
            hexStr.append(String.format("%02x ", bytes[offsetMin + i]));
            if (withAscii)
                asciiStr.append(String.format("%c", toPrintable(bytes[offsetMin + i])));
            
            if (i % rowWidth == rowWidth/2-1) hexStr.append(' ');
            if (i % rowWidth == rowWidth-1) 
                if (!withAscii) hexStr.append('\n');
                else {
                    hexStr.append('|').append(asciiStr).append("|\n");
                    asciiStr.delete(0, asciiStr.length());
                }
        }
        hexStr.append(String.format("%" + ((rowWidth - i % rowWidth) * 3 + (i % rowWidth <= rowWidth/2 ? 1 : 0)) + "s", " "));
        if (withAscii) hexStr.append('|').append(asciiStr).append("|\n");

        return hexStr.toString();
    }

    private static byte toPrintable(byte b) {
        if      (b < 0x20) return (byte)0x2E;
        else if (b > 0x7E) return (byte)0x2E;
        return b;
    }

    public static void main (String[] args) {
        /*byte b = 5;
        byte[] bb = {0, 1, 0, 4};
        System.out.println(Integer.class.isInstance(b) + " " + Integer.class.isInstance(b & 0xff));
        System.out.println(extractInt(bb, 0, 4));
        */
    }
}

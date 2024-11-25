import java.math.BigInteger;

public class Utils {

    public static final int RADIX = 16;

    public static BigInteger converterParaBigInteger(String numeroEmHex) {
        return new BigInteger(numeroEmHex, RADIX);
    }

    public static BigInteger converterParaBigInteger(byte[] numeroEmHex) {
        return new BigInteger(numeroEmHex);
    }

    public static String converterParaString(BigInteger numero) {
        return numero.toString(RADIX);
    }

    public static String converterParaString(byte[] numero) {
        StringBuilder bts = new StringBuilder();
        
        for (byte b: numero) {
            bts.append(String.format("%x", b));
        }
        
        return bts.toString();
    }
    

    public static byte[] converterParaBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), RADIX) << 4)
                    + Character.digit(hex.charAt(i + 1), RADIX));
        }
        
        return data;
    }

    public static String converterParaStringHex(byte[] bytes) {
        StringBuilder bts = new StringBuilder();

        for (byte b: bytes) {
            bts.append(String.format("%02x", b));
        }

        return bts.toString();
    }
    
}

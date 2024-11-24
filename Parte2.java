import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Parte2 {

    //  Autores: Adriana Serpa, Marcos Sanhudo

    /*  DEFINIÇÕES
     *  - Mensagem: o conjunto recebido de caracteres, incluindo o cabeçalho com o vetor de incialização;
     *  - Corpo da mensagem: o texto que se quer comunicar.
     */

    public static final int TAMANHO_DO_VI = 128;

    public static String mensagem = "";
    public static String corpoDaMensagem = "";

    public static void main(String[] args) {
        ;
    }

    public String isolarCorpoDaMensagem(String mensagem) {
        String corpoDaMensagem = "";

        corpoDaMensagem = mensagem.substring(TAMANHO_DO_VI - 1);

        return corpoDaMensagem;
    }

    public String reverterString(String string) {
        String stringRevertida = "";
        char[] stringEmChars = string.toCharArray();

        for (int indice = stringEmChars.length - 1; indice >= 0; indice--) {
            stringRevertida += stringEmChars[indice];
        }

        return stringRevertida;
    }

    public String decifraMensagem (String mensagem, byte[] chaveGerada){
        byte[] byteMsg = transformaEmBytes(mensagem);
        byte[] iv = Arrays.copyOfRange(byteMsg, 0, 16);
        byte[] mensagemCifrada = Arrays.copyOfRange(byteMsg, 16, byteMsg.length);

        Cipher cifra = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec chavePrivada = new SecretKeySpec(chaveGerada, "AES");
        cifra.init(Cipher.DECRYPT_MODE, chavePrivada, new IvParameterSpec(iv)); 

        byte[] mensagemDecifrada = cifra.doFinal(mensagemCifrada);
        return new String(mensagemDecifrada);
    }

    public String cifraMensagem(String mensagem, byte[] chaveGerada){
        byte[] randomIv = new byte[16];
        criarChave.getInstance("AES").generateKey().getEncoded();
        IvParameterSpec ivParam = IvParameterSpec(randomIv);

        Cipher cifra = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec chavePrivada = new SecretKeySpec(chaveGerada, "AES");
        cifra.init(Cipher.ENCRYPT_MODE, chavePrivada); 

        byte[] mensagemCifrada = cifra.doFinal(mensagem.getBytes());
        
        // Combinar IV e mensagem cifrada
        byte[] mensagemFinal = new byte[randomIv.length + mensagemCifrada.length];
        System.arraycopy(randomIv, 0, mensagemFinal, 0, randomIv.length);
        System.arraycopy(mensagemCifrada, 0, mensagemFinal, randomIv.length, mensagemCifrada.length);

        return bytesToHex(mensagemFinal);

    }

}
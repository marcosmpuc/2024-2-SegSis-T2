import java.math.BigInteger;
import java.security.SecureRandom;
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

    // Mensagem recebida do professor, e V
    public static final String MENSAGEM_RECEBIDA = "b4ade50c0b4d5495f54c5dea25e8a2240c142c12d841517f60ea5541c0477c2fc147993d391a745929cea1cb539c25da";
    public static final BigInteger V = Utils.converterParaBigInteger("4538e817432122d461d761d277300c8124f6e1eade43f95327dd10297b167b2e3fcf6de78161386e85501906b6cc7abf4b6482387ce2c74d8200b545c2a3167b9ea337d6fa3db67adfb12a26843a9537ff15e0a280160157f452d5d16893be5c0df8c667f4198bb75beb3f4fe190bf9a5e66f34744a7fe9a0f452503f2b770c9");

    public static void main(String[] args) {
        try {
            // Gera a senha (S)
            byte[] senha = Parte1.calcularSenha(V);
            System.out.println("A chave gerada foi: " + Utils.converterParaStringHex(senha));
            System.out.println(" ");   

            // Decifra a mensagem recebida do professor
            String corpoDecifrado = decifrarMensagem(senha, MENSAGEM_RECEBIDA);
            System.out.println("Corpo decifrado: " + corpoDecifrado);
            System.out.println();

            // Inverte os caracteres da mensagem que foi decifrada
            String corpoInvertido = inverterString(corpoDecifrado);
            System.out.println("Corpo invertido: " + corpoInvertido);
            System.out.println();

            // Cifra a mensagem invertida
            String mensagemCifrada = cifrarMensagem(senha, corpoInvertido);
            System.out.println("Mensagem a ser enviada: " + mensagemCifrada);
            System.out.println();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static String decifrarMensagem (byte[] senha, String mensagem) throws Exception {
        byte[] bytesDaMensagem = Utils.converterParaBytes(mensagem);
        byte[] vetorDeInicializacao = isolarVetorDeInicializacao(bytesDaMensagem);
        byte[] mensagemCifrada = isolarCorpoDaMensagem(bytesDaMensagem);

        Cipher cifra = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec chavePrivada = new SecretKeySpec(senha, "AES");
        cifra.init(Cipher.DECRYPT_MODE, chavePrivada, new IvParameterSpec(vetorDeInicializacao)); 
        byte[] mensagemDecifrada = cifra.doFinal(mensagemCifrada);

        return new String(mensagemDecifrada);
    }

    public static String cifrarMensagem (byte[] senha, String mensagem) throws Exception {
        byte[] vetorDeInicializacao = gerarVetorDeInicializacao();
        IvParameterSpec parametrosVi = new IvParameterSpec(vetorDeInicializacao);

        Cipher cifra = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec chavePrivada = new SecretKeySpec(senha, "AES");
        cifra.init(Cipher.ENCRYPT_MODE, chavePrivada, parametrosVi); 
        byte[] mensagemCifrada = cifra.doFinal(mensagem.getBytes());
        
        byte[] mensagemFinal = combinarViECorpoDaMensagem(vetorDeInicializacao, mensagemCifrada);

        return Utils.converterParaStringHex(mensagemFinal);
    }

    public static byte[] isolarCorpoDaMensagem(byte[] bytesDaMensagem) {
        byte[] mensagemCifrada = Arrays.copyOfRange(bytesDaMensagem, 16, bytesDaMensagem.length);
        return mensagemCifrada;
    }

    public static byte[] isolarVetorDeInicializacao(byte[] bytesDaMensagem) {
        byte[] vetorDeInicializacao = Arrays.copyOfRange(bytesDaMensagem, 0, 16);
        return vetorDeInicializacao;
    }

    public static byte[] gerarVetorDeInicializacao() {
        byte[] vetorDeInicializacao = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(vetorDeInicializacao);

        return vetorDeInicializacao;
    }

    public static byte[] combinarViECorpoDaMensagem(byte[] vetorDeInicializacao, byte[] mensagemCifrada) {
        byte[] mensagemFinal = new byte[vetorDeInicializacao.length + mensagemCifrada.length];

        System.arraycopy(vetorDeInicializacao, 0, mensagemFinal, 0, vetorDeInicializacao.length);
        System.arraycopy(mensagemCifrada, 0, mensagemFinal, vetorDeInicializacao.length, mensagemCifrada.length);

        return mensagemFinal;
    }
    
    public static String inverterString(String string) {
        return new StringBuilder(string).reverse().toString();
    }

}
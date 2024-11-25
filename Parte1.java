import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Parte1 {

    // Autores: Adriana Serpa, Marcos Sanhudo
    
    // Valores fornecidos no enunciado
    static final String G_HEX = "A4D1CBD5C3FD34126765A442EFB99905F8104DD258AC507FD6406CFF14266D31266FEA1E5C41564B777E690F5504F213160217B4B01B886A5E91547F9E2749F4D7FBD7D3B9A92EE1909D0D2263F80A76A6A24C087A091F531DBF0A0169B6A28AD662A4D18E73AFA32D779D5918D08BC8858F4DCEF97C2A24855E6EEB22B3B2E5";
    static final String P_HEX = "B10B8F96A080E01DDE92DE5EAE5D54EC52C99FBCFB06A3C69A6A9DCA52D23B616073E28675A23D189838EF1E2EE652C013ECB4AEA906112324975C3CD49B83BFACCBDD7D90C4BD7098488E9C219A73724EFFD6FAE5644738FAA31A4FF55BCCC0A151AF5F0DC8B4BD45BF37DF365C1A65E68CFDA76D4DA708DF1FB2BC2E4A4371";
    
    // Valores dos alunos (A) e do professor (B)
    static final String A_HEX = "c59e1f148e3cd53f740283b7dc9ae3";
    static final String B_HEX = "00A1B2595D943A88BF37E60F1BC2623EDF1E3C1A821977F84F1FF6960D3481BFB17749527879A31FD05758270135CD985900B073127E9792F2D52BE7DA183E6765D810B47BF5D4E52FF88E744AD0530294C52B406040ED73430496F70C49E2E634984F5430CF8CC4734B8A624895A88E8605212A95C93C4EE49E8A8087DABDACF2";

    // Variáveis de controle
    static final String NOME_DO_ALGORITMO = "SHA-256";
    static final int TAMANHO_DA_SENHA = 16;
    
    public static void main(String[] args) {
        // Convertendo os valores hexadecimais para BigInteger
        BigInteger g = Utils.converterParaBigInteger(G_HEX);
        BigInteger p = Utils.converterParaBigInteger(P_HEX);
        BigInteger a = Utils.converterParaBigInteger(A_HEX);
        BigInteger B = Utils.converterParaBigInteger(B_HEX);

        // Calculando A = g^a mod p
        BigInteger A = calcularChave(g, a, p);

        // Calculando V = B^a mod p
        BigInteger V = calcularChave(B, a, p);

        // Calculando S = SHA256(V)
        byte[] S = calcularSenha(V);

        // Resultado em hexadecimal
        String AHexResultado = Utils.converterParaString(A);
        String VHexResultado = Utils.converterParaString(V);
        String SHexResultado = Utils.converterParaString(S);
        System.out.println("O valor de A em hexadecimal é: " + AHexResultado);
        System.out.println("O valor de V em hexadecimal é: " + VHexResultado);
        System.out.println("O valor de S em hexadecimal é: " + SHexResultado);
    }

    public static BigInteger calcularChave(BigInteger base, BigInteger a, BigInteger p) {
        return base.modPow(a, p);
    }

    public static byte[] calcularSenha(BigInteger V) {
        try {
            MessageDigest md256 = MessageDigest.getInstance(NOME_DO_ALGORITMO);
            byte[] senha = new byte[TAMANHO_DA_SENHA];

            md256.update(V.toByteArray());
            byte[] senhaSemPadding = md256.digest();

            System.arraycopy(senhaSemPadding, senhaSemPadding.length - TAMANHO_DA_SENHA, senha, 0 , TAMANHO_DA_SENHA);

            return senha;        
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}

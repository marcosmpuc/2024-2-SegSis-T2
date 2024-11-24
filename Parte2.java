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

}
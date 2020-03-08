import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("D:\\An III\\Tema1TC\\test.out");
        }
        catch (Exception e) {
            System.out.println("Eroare la deschiderea fisierului de iesire!\n" + e);
            return ;
        }

        LexicalAnalyzer lexicalAnalyzer;
        try {
            lexicalAnalyzer = new LexicalAnalyzer("D:\\An III\\Tema1TC\\test.in");
        }
        catch (IOException e) {
            System.out.println("Eroare la deschiderea fisierului de intrare!\n" + e);
            return ;
        }

        Token token = lexicalAnalyzer.getToken();
        while (token.getTypeIndex() != 1) {
            fileWriter.write(lexicalAnalyzer.tokenToString(token));
            if (token.getTypeIndex() == 0) {
                break;
            }
            token = lexicalAnalyzer.getToken();
        }
        fileWriter.close();
    }
}

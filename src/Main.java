import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        List<String> tokenTypes = new ArrayList<>(Arrays.asList("Error", "EndOfFile", "Keyword", "Operator", "String", "Identifier"));

        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("D:\\An III\\Tema1TC\\test.out");
        }
        catch (Exception e) {
            System.out.println("Eroare la deschiderea fisierului de iesire!\n" + e);
        }

        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer("D:\\An III\\Tema1TC\\test.in");
    }
}

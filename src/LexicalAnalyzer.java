import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LexicalAnalyzer {
    private State currentState;
    private int currentByte;
    private FileReader fileReader;
    private List<String> keywords;
    private List<String> tokenValues;

    public LexicalAnalyzer(String fileName) {
        currentState = States.Initial;
        currentByte = 0;
        keywords = new ArrayList<>(Arrays.asList("False", "None", "True", "and", "as", "assert", "break", "class", "continue", "def",
                                                "del", "elif", "else", "except", "finally", "for", "from", "global", "if", "import",
                                                "in", "is", "lambda", "nonlocal", "not", "or", "pass", "raise", "return", "try",
                                                "while", "with", "yield"));
        tokenValues = new ArrayList<>();
        try {
            this.fileReader = new FileReader(fileName);
        }
        catch (Exception e) {
            System.out.println("Eroare la deschiderea fisierului de intrare!\n" + e);
        }
    }

    public Token getToken() throws IOException {
        int i, charactersRead = 0;
        String input = "";
        State previousState = currentState;
        while((i=fileReader.read())!=-1 && currentState != FinalStates.End && currentState != FinalStates.Fail) {
            previousState = currentState;
            currentState = currentState.next((char)i);
            input += (char)i;
            charactersRead++;
        }

        if (currentState == FinalStates.Fail)
            return new Token(0, currentByte);

        currentByte += charactersRead;

        if (currentState == FinalStates.End) {
            if (previousState == States.Identifier) {
                if (keywords.contains(input)) {
                    return new Token(2, keywords.indexOf(input));
                }
            }
        }

        return new Token(1, 0);
    }
}

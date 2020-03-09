import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class LexicalAnalyzer {
    private State currentState;
    private int currentByte;
    private String content;
    private List<String> keywords;
    private List<String> tokenValues;
    private List<String> tokenTypes;
    private Map<State, Integer> finalStateToTokenType;

    public LexicalAnalyzer(String fileName) throws IOException{
        currentState = States.Initial;
        currentByte = 0;
        keywords = new ArrayList<>(Arrays.asList("False", "None", "True", "and", "as", "assert", "break", "class", "continue", "def",
                                                "del", "elif", "else", "except", "finally", "for", "from", "global", "if", "import",
                                                "in", "is", "lambda", "nonlocal", "not", "or", "pass", "raise", "return", "try",
                                                "while", "with", "yield"));
        tokenValues = new ArrayList<>();
        tokenTypes = new ArrayList<>(Arrays.asList("Error", "EndOfFile", "Keyword", "Operator", "String", "Identifier",
                                                    "Ignored", "IntegerConstant", "FloatingConstant", "ExponentConstant", "HexaConstant"));
        finalStateToTokenType = Map.ofEntries(entry(States.Space, 6), entry(States.Newline, 6), entry(States.AnyCharacter, 3),
                                                entry(States.Number, 7), entry(States.Float, 8), entry(States.Comment, 6),
                                                entry(States.StringSimpleQuotesEnd, 4), entry(States.StringDoubleQuotesEnd, 4),
                                                entry(States.CharacterCanBeFollowedByEqual, 3), entry(States.Exponent, 9),
                                                entry(States.GroupCharacter, 3), entry(States.Zero, 7), entry(States.Hexa, 10),
                                                entry(States.Identifier, 5));

        content = Files.readString(Paths.get(fileName));
        content = content.replaceAll("\\r", "");
    }

    public Token getToken() throws IOException {
        int charactersRead = 0;
        State previousState = currentState;
        String input = "";
        while(currentByte + charactersRead < content.length() && currentState != FinalStates.End && currentState != FinalStates.Fail) {
            previousState = currentState;
            currentState = currentState.next(content.charAt(currentByte + charactersRead));
            input += content.charAt(currentByte + charactersRead);
            charactersRead++;
        }
        input = input.substring(0, input.length() - 1);

        if (currentState == FinalStates.Fail) {
            return new Token(0, currentByte);
        }

        if (currentState == FinalStates.End) {
            reset(charactersRead);
            if (previousState == States.Identifier) {
                if (keywords.contains(input)) {
                    return new Token(2, keywords.indexOf(input));
                }
            }
            if (!tokenValues.contains(input)) {
                tokenValues.add(input);
            }
            return new Token(finalStateToTokenType.get(previousState), tokenValues.indexOf(input));
        }

        return new Token(1, 0);
    }

    public String tokenToString(Token token) {
        if (token.getTypeIndex() == 6) {
            return "";
        }
        if (token.getTypeIndex() == 0) {
            return "(" + 0 + "," + token.getValueIndex() + ")\n";
        }
        if (token.getTypeIndex() == 2) {
            return "(" +
                    tokenTypes.get(token.getTypeIndex()) + "," +
                    keywords.get(token.getValueIndex()) +
                    ")\n";
        }
        return "(" +
                tokenTypes.get(token.getTypeIndex()) + "," +
                tokenValues.get(token.getValueIndex()) +
                ")\n";
    }

    private void reset(int charactersRead) {
        currentState = States.Initial;
        if (charactersRead > 1) {
            currentByte += charactersRead - 1;
        }
        else {
            currentByte++;
        }
    }
}

import java.util.HashMap;

public class DFAState {
    private boolean isFinal;
    private HashMap<Character, DFAState> transitions;

    public DFAState(boolean isFinal, HashMap<Character, DFAState> transitions) {
        this.isFinal = isFinal;
        this.transitions = transitions;
    }

    public DFAState transitionWith(char transitionSymbol) throws Exception{
        if (this.transitions.containsKey(transitionSymbol)) {
            return this.transitions.get(transitionSymbol);
        }
        throw new Exception("Undefined transition");
    }

    public boolean isFinal() {
        return isFinal;
    }
}

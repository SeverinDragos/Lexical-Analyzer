public class DFA {
    private DFAState initialState;
    private DFAState currentState;
    private boolean encounteredError;

    public DFA(DFAState startState) {
        this.initialState = startState;
        this.currentState = this.initialState;
        this.encounteredError = false;
    }

    public void nextState(char transitionSymbol) {
        if (!encounteredError) {
            try {
                this.currentState = this.currentState.transitionWith(transitionSymbol);
            } catch (Exception e) {
                this.encounteredError = true;
                /**Need to do some more shit here*/
            }
        }
    }

    public boolean isAccepted() {
        if (this.encounteredError) {
            return false;
        }
        return this.currentState.isFinal();
    }
}

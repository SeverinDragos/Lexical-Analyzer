public enum FinalStates implements FinalState {
    Fail {
        @Override
        public State next(char c) {
            return Fail;
        }
    },

    End {
        @Override
        public State next(char c) {
            return End;
        }
    },
}

import java.util.TreeSet;

public enum States implements State {
    Initial {
        @Override
        public State next(char c) {
            /**?*/
            if (c == '0')   return Zero;
            if (c == '_' || Character.isLetter(c))  return Identifier;
            if (c == ' ')   return Space;
            if (c == '\n')  return Newline;
            if (Character.isDigit(c))   return Number;
            TreeSet<Character> operators = new TreeSet<>() {
                {
                    add('+');add('*');add('/');add('<');add('>');add('%');add('-');
                }
            };
            if (operators.contains(c))  return CharacterCanBeFollowedByEqual;
            TreeSet<Character> characters = new TreeSet<>() {
                {
                    add('+');add('*');add('-');add(':');add('=');add('%');add(';');add('<');
                    add('>');add('(');add(')');add('[');add(']');add('{');add('}');add('.');
                    add(',');add('!');
                }
            };
            if (characters.contains(c)) return AnyCharacter;
            if (c == '#')   return Comment;
            if (c == '\'')  return StringSimpleQuotes;
            if (c == '\"')  return StringDoubleQuotes;
            return FinalStates.Fail;
        }
    },

    Identifier {
        @Override
        public State next(char c) {
            if (c == '_' || Character.isLetterOrDigit(c))   return Identifier;
            return FinalStates.End;
        }
    },

    Space {
        @Override
        public State next(char c) {
            if (c == ' ')   return Space;
            return FinalStates.End;
        }
    },

    Newline {
        @Override
        public State next(char c) {
            switch (c) {
                case '\t': return Newline;
                default: return FinalStates.End;
            }
        }
    },

    AnyCharacter {
        @Override
        public State next(char c) {
            return FinalStates.End;
        }
    },

    Number {
        @Override
        public State next(char c) {
            if (Character.isDigit(c))   return Number;
            if (c == '.')   return Float;
            if (c == 'e')   return Exponent;
            return FinalStates.End;
        }
    },

    Float {
        @Override
        public State next(char c) {
            if (Character.isDigit(c))   return Number;
            return FinalStates.End;
        }
    },

    Comment {
        @Override
        public State next(char c) {
            if (c == '\n')  return FinalStates.End;
            return Comment;
        }
    },

    StringSimpleQuotes {
        @Override
        public State next(char c) {
            switch (c) {
                case '\\': return EscapedCharacterSimpleQuotes;
                case '\'': return StringSimpleQuotesEnd;
                case '\n': return FinalStates.Fail;
                default: return StringSimpleQuotes;
            }
        }
    },

    StringSimpleQuotesEnd {
        @Override
        public State next(char c) {
            return FinalStates.End;
        }
    },

    StringDoubleQuotes {
        @Override
        public State next(char c) {
            switch (c) {
                case '\\': return EscapedCharacterDoubleQuotes;
                case '\"': return StringDoubleQuotesEnd;
                case '\n': return FinalStates.Fail;
                default: return StringDoubleQuotes;
            }
        }
    },

    StringDoubleQuotesEnd {
        @Override
        public State next(char c) {
            return FinalStates.End;
        }
    },

    EscapedCharacterSimpleQuotes {
        @Override
        public State next(char c) {
            return StringSimpleQuotes;
        }
    },

    EscapedCharacterDoubleQuotes {
        @Override
        public State next(char c) {
            return StringDoubleQuotes;
        }
    },

    CharacterCanBeFollowedByEqual {
        @Override
        public State next(char c) {
            if (c == '=') {
                return GroupCharacter;
            }
            return FinalStates.End;
        }
    },

    GroupCharacter {
        @Override
        public State next(char c) {
            return FinalStates.End;
        }
    },

    Exponent {
        @Override
        public State next(char c) {
            if (Character.isDigit(c))   return Number;
            return FinalStates.End;
        }
    },

    Zero {
        @Override
        public State next(char c) {
            if (c == 'x')   return Hexa;
            if (Character.isDigit(c))   return Number;
            return FinalStates.End;
        }
    },

    Hexa {
        @Override
        public State next(char c) {
            if (Character.isDigit(c) || ('a' <= c && c <= 'f') || ('A' <= c && c <= 'F')) return Hexa;
            return FinalStates.End;
        }
    },
}

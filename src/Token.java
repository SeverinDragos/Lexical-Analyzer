public class Token {
    private int typeIndex;
    private int valueIndex;

    public Token(int type, int valueIndex) {
        this.typeIndex = type;
        this.valueIndex = valueIndex;
    }

    public int getTypeIndex() {
        return typeIndex;
    }

    public int getValueIndex() {
        return valueIndex;
    }

    @Override
    public String toString() {
        return "(" +
                "type=" + typeIndex +
                ", value=" + valueIndex +
                ")\n";
    }
}

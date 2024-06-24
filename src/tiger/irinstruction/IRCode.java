package tiger.irinstruction;

public class IRCode extends IRInstruction{
    private final StringBuilder sb;

    public IRCode(String string) {
        sb = new StringBuilder();
        sb.append(string);
        sb.append(System.lineSeparator());

    }

    @Override
    public String toString(){
        return sb.toString();
    }
}

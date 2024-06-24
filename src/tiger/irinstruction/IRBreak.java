package tiger.irinstruction;

public class IRBreak extends IRInstruction {
    private String endLabel;

    public IRBreak(String label) {
        this.endLabel = label;
    }

    @Override
    public String toString() {
        return "\t\tgoto, " + endLabel+", ,\n";
    }
}

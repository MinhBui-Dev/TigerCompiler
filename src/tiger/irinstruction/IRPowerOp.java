package tiger.irinstruction;

public class IRPowerOp extends IRInstruction {
    private final String base;
    private final String exponent;
    private final String result;
    private final String startLabel;
    private final String endLabel;

    public IRPowerOp(String base, String exponent, String result, String startLabel, String endLabel) {
        this.base = base;
        this.exponent = exponent;
        this.result = result;
        this.startLabel = startLabel;
        this.endLabel = endLabel;
    }

    @Override
    public String toString() {
        return "\t\tassign, " + result + ", 1" +
                System.lineSeparator() +
                "\t\tbreq, " + exponent + ", 0, " + endLabel +
                System.lineSeparator() +
                "\t" + startLabel + ":" +
                System.lineSeparator() +
                "\t\tmult, " + result + ", " + base + ", " + result +
                System.lineSeparator() +
                "\t\tsub, " + exponent + ", 1, " + exponent +
                System.lineSeparator() +
                "\t\tbreq, " + exponent + ", 0, " + endLabel +
                System.lineSeparator() +
                "\t\tbrneq, " + exponent + ", 0, " + startLabel +
                System.lineSeparator() +
                "\t" + endLabel + ":" +
                System.lineSeparator();
    }
}

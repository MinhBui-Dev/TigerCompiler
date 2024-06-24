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
        StringBuilder sb = new StringBuilder();
        sb.append("\t\tassign, "+result+", 1");
        sb.append(System.lineSeparator());
        sb.append("\t\tbreq, "+exponent+", 0, "+endLabel);
        sb.append(System.lineSeparator());
        sb.append("\t"+startLabel+":");
        sb.append(System.lineSeparator());
        sb.append("\t\tmult, " + result + ", " + base + ", " + result);
        sb.append(System.lineSeparator());
        sb.append("\t\tsub, "+ exponent + ", 1, " + exponent);
        sb.append(System.lineSeparator());
        sb.append("\t\tbreq, "+exponent+", 0, "+endLabel);
        sb.append(System.lineSeparator());
        sb.append("\t\tbrneq, "+exponent+", 0, "+startLabel);
        sb.append(System.lineSeparator());
        sb.append("\t"+endLabel+":");
        sb.append(System.lineSeparator());
        return sb.toString();
    }
}

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
        sb.append("\t\tassign, ").append(result).append(", 1");
        sb.append(System.lineSeparator());
        sb.append("\t\tbreq, ").append(exponent).append(", 0, ").append(endLabel);
        sb.append(System.lineSeparator());
        sb.append("\t").append(startLabel).append(":");
        sb.append(System.lineSeparator());
        sb.append("\t\tmult, ").append(result).append(", ").append(base).append(", ").append(result);
        sb.append(System.lineSeparator());
        sb.append("\t\tsub, ").append(exponent).append(", 1, ").append(exponent);
        sb.append(System.lineSeparator());
        sb.append("\t\tbreq, ").append(exponent).append(", 0, ").append(endLabel);
        sb.append(System.lineSeparator());
        sb.append("\t\tbrneq, ").append(exponent).append(", 0, ").append(startLabel);
        sb.append(System.lineSeparator());
        sb.append("\t").append(endLabel).append(":");
        sb.append(System.lineSeparator());
        return sb.toString();
    }
}

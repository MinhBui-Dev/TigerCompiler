package tiger.irinstruction;

public class IRComparisonBranch extends IRInstruction {
    private final String leftOperand;
    private final String rightOperand;
    private final String op;
    private final String label;
    private final String result;

    public IRComparisonBranch(String leftOperand, String rightOperand, String result, String op, String label) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.op = op;
        this.label = label;
        this.result = result;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t\tassign, "+result+", 1,");
        sb.append(System.lineSeparator());
        switch (op) {
            case "==":
                sb.append("\t\tbreq, " + leftOperand + ", " + rightOperand + ", " + label);
                sb.append(System.lineSeparator());
                break;
            case "!=":
                sb.append("\t\tbrneq, " + leftOperand + ", " + rightOperand + ", " + label);
                sb.append(System.lineSeparator());
                break;
            case "<":
                sb.append("\t\tbrlt, " + leftOperand + ", " + rightOperand + ", " + label);
                sb.append(System.lineSeparator());
                break;
            case ">":
                sb.append("\t\tbrgt, " + leftOperand + ", " + rightOperand + ", " + label);
                sb.append(System.lineSeparator());
                break;
            case "<=":
                sb.append("\t\tbrleq, " + leftOperand + ", " + rightOperand + ", " + label);
                sb.append(System.lineSeparator());
                break;
            case ">=":
                sb.append("\t\tbrgeq, " + leftOperand + ", " + rightOperand + ", " + label);
                sb.append(System.lineSeparator());
                break;
        }
        sb.append("\t\tassign, "+result+", 0,");
        sb.append(System.lineSeparator());
        sb.append("\t"+label+":");
        sb.append(System.lineSeparator());
        return sb.toString();
    }
}

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
        sb.append("\t\tassign, ").append(result).append(", 1,");
        sb.append(System.lineSeparator());
        switch (op) {
            case "==":
                sb.append("\t\tbreq, ").append(leftOperand).append(", ").append(rightOperand).append(", ").append(label);
                sb.append(System.lineSeparator());
                break;
            case "!=":
                sb.append("\t\tbrneq, ").append(leftOperand).append(", ").append(rightOperand).append(", ").append(label);
                sb.append(System.lineSeparator());
                break;
            case "<":
                sb.append("\t\tbrlt, ").append(leftOperand).append(", ").append(rightOperand).append(", ").append(label);
                sb.append(System.lineSeparator());
                break;
            case ">":
                sb.append("\t\tbrgt, ").append(leftOperand).append(", ").append(rightOperand).append(", ").append(label);
                sb.append(System.lineSeparator());
                break;
            case "<=":
                sb.append("\t\tbrleq, ").append(leftOperand).append(", ").append(rightOperand).append(", ").append(label);
                sb.append(System.lineSeparator());
                break;
            case ">=":
                sb.append("\t\tbrgeq, ").append(leftOperand).append(", ").append(rightOperand).append(", ").append(label);
                sb.append(System.lineSeparator());
                break;
        }
        sb.append("\t\tassign, ").append(result).append(", 0,");
        sb.append(System.lineSeparator());
        sb.append("\t").append(label).append(":");
        sb.append(System.lineSeparator());
        return sb.toString();
    }
}

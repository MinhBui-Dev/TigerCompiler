package tiger.irinstruction;

public class IRComparisonBranch extends IRInstruction {
    private final String leftOperand;
    private final String rightOperand;
    private final String op;
    private final String label;

    public IRComparisonBranch(String op, String leftOperand, String rightOperand,  String label) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.op = op;
        this.label = label;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        switch (op) {
            case "==":
                sb.append("\t\tbreq, ").append(leftOperand).append(", ").append(rightOperand).append(", ").append(label);
                break;
            case "!=":
                sb.append("\t\tbrneq, ").append(leftOperand).append(", ").append(rightOperand).append(", ").append(label);
                break;
            case "<":
                sb.append("\t\tbrlt, ").append(leftOperand).append(", ").append(rightOperand).append(", ").append(label);
                break;
            case ">":
                sb.append("\t\tbrgt, ").append(leftOperand).append(", ").append(rightOperand).append(", ").append(label);
                break;
            case "<=":
                sb.append("\t\tbrleq, ").append(leftOperand).append(", ").append(rightOperand).append(", ").append(label);
                break;
            case ">=":
                sb.append("\t\tbrgeq, ").append(leftOperand).append(", ").append(rightOperand).append(", ").append(label);
                break;
        }
        sb.append(System.lineSeparator());
        return sb.toString();
    }
}

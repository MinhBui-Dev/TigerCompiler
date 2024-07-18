package tiger.irinstruction;

public class IRBinaryOp extends IRInstruction {
    private final String op;
    private final String left;
    private final String right;
    private final String result;

    public IRBinaryOp(String op, String left, String right, String result) {
        this.op = op;
        this.left = left;
        this.right = right;
        this.result = result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        switch (op) {
            case "+":
                sb.append("\t\tadd, ").append(left).append(", ").append(right).append(", ").append(result);
                break;
            case "-":
                sb.append("\t\tsub, ").append(left).append(", ").append(right).append(", ").append(result);
                break;
            case "*":
                sb.append("\t\tmult, ").append(left).append(", ").append(right).append(", ").append(result);
                break;
            case "/":
                sb.append("\t\tdiv, ").append(left).append(", ").append(right).append(", ").append(result);
                break;
            case "&":
                sb.append("\t\tand, ").append(left).append(", ").append(right).append(", ").append(result);
                break;
            case "|":
                sb.append("\t\tor, ").append(left).append(", ").append(right).append(", ").append(result);
                break;
        }

        sb.append(System.lineSeparator());
        return sb.toString();
    }
}

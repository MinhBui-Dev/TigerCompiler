package tiger.irinstruction;

public class IRBinaryOp extends IRInstruction {
    private String op;
    private String left;
    private String right;
    private String result;

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
                sb.append("\t\tadd, " + left + ", " + right + ", " + result);
                sb.append(System.lineSeparator());
                break;
            case "-":
                sb.append("\t\tsub, " + left + ", " + right + ", " + result);
                sb.append(System.lineSeparator());
                break;
            case "*":
                sb.append("\t\tmult, " + left + ", " + right + ", " + result);
                sb.append(System.lineSeparator());
                break;
            case "/":
                sb.append("\t\tdiv, " + left + ", " + right + ", " + result);
                sb.append(System.lineSeparator());
                break;
            case "&":
                sb.append("\t\tand, " + left + ", " + right + ", " + result);
                sb.append(System.lineSeparator());
                break;
            case "|":
                sb.append("\t\tor, " + left + ", " + right + ", " + result);
                sb.append(System.lineSeparator());
                break;
        }
        return sb.toString();
    }
}

package tiger.irinstruction;

import java.util.HashSet;
import java.util.Set;

public class IRBinaryOp extends IRInstruction{
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

    public String getOp() {
        return op;
    }

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }

    public String getResult() {
        return result;
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

    @Override
    public <T> T accept(IRVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public Set<String> getUse() {
        HashSet<String> use = new HashSet<>();
        if (!left.matches("^\\d+$")) {
            use.add(left);
        }
        if (!right.matches("^\\d+$")) {
            use.add(right);
        }
        return use;
    }

    @Override
    public Set<String> getDef() {
        HashSet<String> def = new HashSet<>();
        if (!result.matches("^\\d+$")) {
            def.add(result);
        }
        return def;
    }
}

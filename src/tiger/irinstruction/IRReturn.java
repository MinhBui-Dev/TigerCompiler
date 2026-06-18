package tiger.irinstruction;

import java.util.HashSet;
import java.util.Set;

public class IRReturn extends IRInstruction{
    private final String value;
    public IRReturn(String value) {
        this.value = value;
    }
    public IRReturn(){
        this.value = "";
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t\treturn, ").append(value).append(", ,");
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
        if (!value.matches("^\\d+$")) {
            use.add(value);
        }
        return use;
    }

    @Override
    public Set<String> getDef() {
        return new HashSet<>();
    }
}

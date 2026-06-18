package tiger.irinstruction;

import java.util.HashSet;
import java.util.Set;

public class IRAssignArray extends IRInstruction{
    private final String target;
    private final String size;
    private final String value;

    public IRAssignArray(String target, String size, String value){
        this.target = target;
        this.size = size;
        this.value = value;
    }

    public String getTarget() {
        return target;
    }

    public String getSize() {
        return size;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t\tassign, ").append(target).append(", ").append(size).append(", ").append(value);
        sb.append(System.lineSeparator());
        return sb.toString();
    }

    @Override
    public <T> T accept(IRVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public Set<String> getUse() {
        return new HashSet<>();
    }

    @Override
    public Set<String> getDef() {
        return new HashSet<>();
    }


}

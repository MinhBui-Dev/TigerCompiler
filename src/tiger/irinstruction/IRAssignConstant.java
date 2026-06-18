package tiger.irinstruction;

import java.util.HashSet;
import java.util.Set;

public class IRAssignConstant extends IRInstruction{
    private final String target;
    private final String constant;

    public IRAssignConstant(String target, String source){
        this.target = target;
        this.constant = source;
    }

    public String getTarget() {
        return target;
    }

    public String getConstant() {
        return constant;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t\tassign, ").append(target).append(", ").append(constant).append(",");
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
        HashSet<String> def = new HashSet<>();
        def.add(target);
        return def;
    }


}

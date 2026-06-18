package tiger.irinstruction;

import java.util.HashSet;
import java.util.Set;

public class IRAssign extends IRInstruction{
    private final String target;
    private final String source;

    public IRAssign(String target, String source){
        this.target = target;
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public String getSource() {
        return source;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t\tassign, ").append(target).append(", ").append(source).append(",");
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
        if (!source.matches("^\\d+$")) {
            use.add(source);
        }
        return use;
    }

    @Override
    public Set<String> getDef() {
        HashSet<String> def = new HashSet<>();
        def.add(target);
        return def;
    }
}

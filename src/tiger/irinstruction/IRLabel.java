package tiger.irinstruction;

import java.util.HashSet;
import java.util.Set;

public class IRLabel extends IRInstruction{
    private final String label;

    public IRLabel(String label){
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public String toString(){
        return "\t" + label + ":" + System.lineSeparator();
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

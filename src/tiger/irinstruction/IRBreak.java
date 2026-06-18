package tiger.irinstruction;

import java.util.HashSet;
import java.util.Set;

public class IRBreak extends IRInstruction{
    private final String label;

    public IRBreak(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "\t\tgoto, " + label+", ," + System.lineSeparator();
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

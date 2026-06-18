package tiger.irinstruction;

import java.util.HashSet;
import java.util.Set;

public class IRArrayStore extends IRInstruction{
    private final String source;
    private final String array;
    private final String index;

    public IRArrayStore(String source, String array, String index) {
        this.source = source;
        this.array = array;
        this.index = index;
    }

    public String getSource() {
        return source;
    }

    public String getArray() {
        return array;
    }

    public String getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "\t\tarray_store, " + array + ", " + index + ", " + source +
                System.lineSeparator();
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
        if (!index.matches("^\\d+$")) {
            use.add(index);
        }
        return use;
    }

    @Override
    public Set<String> getDef() {
        return new HashSet<>();
    }
}

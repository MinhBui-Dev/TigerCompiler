package tiger.irinstruction;

import java.util.HashSet;
import java.util.Set;

public class IRArrayLoad extends IRInstruction{
    private final String result;
    private final String array;
    private final String index;

    public IRArrayLoad(String result, String array, String index) {
        this.result = result;
        this.array = array;
        this.index = index;
    }

    public String getResult() {
        return result;
    }

    public String getArray() {
        return array;
    }

    public String getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "\t\tarray_load, " + result + ", " + array + ", " + index +
                System.lineSeparator();
    }

    @Override
    public <T> T accept(IRVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public Set<String> getUse() {
        HashSet<String> use = new HashSet<>();
        if (!index.matches("^\\d+$")) {
            use.add(index);
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

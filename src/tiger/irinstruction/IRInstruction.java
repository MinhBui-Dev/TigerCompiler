package tiger.irinstruction;

import java.util.*;

public abstract class IRInstruction {
    public abstract String toString();

    public abstract <T> T accept(IRVisitor<T> visitor);

    public abstract Set<String> getUse();

    public abstract Set<String> getDef();

    public Set<String> getAllVar(){
        HashSet<String> set = new HashSet<String>(getUse());
        set.addAll(getDef());
        return set;
    }
}

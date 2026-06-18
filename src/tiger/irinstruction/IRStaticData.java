package tiger.irinstruction;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class IRStaticData extends IRInstruction{
    private final HashMap<String, String> intVars;
    private final HashMap<String, String> floatVars;

    public IRStaticData(){
        this.intVars = new HashMap<>();
        this.floatVars = new HashMap<>();
    }


    public void addIntVar(String var, String value){
        this.intVars.put(var, value);
    }

    public void addFloatVar(String var, String value){
        this.floatVars.put(var, value);
    }

    public HashMap<String, String> getIntVars() {
        return intVars;
    }

    public HashMap<String, String> getFloatVars() {
        return floatVars;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("\tstatic-int-list: ");
        Set<String> intVars = this.intVars.keySet();
        for (String var : intVars){
            sb.append(var).append(", ");
        }
        sb.append(System.lineSeparator());

        Set<String> floatVars = this.floatVars.keySet();
        sb.append("\tstatic-float-list: ");
        for (String var: floatVars){
            sb.append(var).append(", ");
        }
        sb.append(System.lineSeparator());

        return  sb.toString();
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

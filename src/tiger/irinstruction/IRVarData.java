package tiger.irinstruction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IRVarData extends IRInstruction{
    private Set<String> intVars;
    private Set<String> floatVars;
    private int tempCount;

    public IRVarData(){
        this.intVars = new HashSet<>();
        this.floatVars = new HashSet<>();
        this.tempCount = 0;
    }

    public void addIntVar(String var){
        this.intVars.add(var);
    }

    public Set<String> getIntVars() {
        return intVars;
    }

    public void addFloatVar(String var){
        this.floatVars.add(var);
    }

    public void incTempCount(){
        this.tempCount++;
    }

    public int getTempCount(){
        return this.tempCount;
    }

    public int getVarsCount(){
        return this.intVars.size();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("\tint-list: ");
        for (String var : intVars){
            sb.append(var).append(", ");
        }
        sb.append(System.lineSeparator());

        sb.append("\tfloat-list: ");
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

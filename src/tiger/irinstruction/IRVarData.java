package tiger.irinstruction;

import java.util.ArrayList;
import java.util.List;

public class IRVarData extends IRInstruction{
    private List<String> intVars;
    private List<String> floatVars;
    private int tempCount;

    public IRVarData(){
        this.intVars = new ArrayList<>();
        this.floatVars = new ArrayList<>();
        this.tempCount = 0;
    }

    public void addIntVar(String var){
        this.intVars.add(var);
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
}

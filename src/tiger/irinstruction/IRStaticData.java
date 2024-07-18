package tiger.irinstruction;

import java.util.ArrayList;
import java.util.List;

public class IRStaticData extends IRInstruction{
    private List<String> intVars;
    private List<String> floatVars;

    public IRStaticData(){
        this.intVars = new ArrayList<>();
        this.floatVars = new ArrayList<>();
    }

    public void addIntVar(String var){
        this.intVars.add(var);
    }

    public void addFloatVar(String var){
        this.floatVars.add(var);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("\tstatic-int-list: ");
        for (String var : intVars){
            sb.append(var).append(", ");
        }
        sb.append(System.lineSeparator());

        sb.append("\tstatic-float-list: ");
        for (String var: floatVars){
            sb.append(var).append(", ");
        }
        sb.append(System.lineSeparator());

        return  sb.toString();
    }

}

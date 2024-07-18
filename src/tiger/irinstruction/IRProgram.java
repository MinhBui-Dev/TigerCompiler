package tiger.irinstruction;

import java.util.ArrayList;
import java.util.List;

public class IRProgram extends IRInstruction{
    private IRStaticData data;
    private List<IRFunction> functions;
    private String name;

    public IRProgram (String name){
        this.name = name;
        this.functions = new ArrayList<>();
    }

    public List<IRFunction> getFunctions() {
        return functions;
    }

    //possible usage for parallel execution case
    public void setFunctions(List<IRFunction> functions) {
        this.functions = functions;
    }

    public void addFunction(IRFunction function){
        this.functions.add(function);
    }

    public IRStaticData getData() {
        return data;
    }

    public void setData(IRStaticData data) {
        this.data = data;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("start_program ").append(name).append(System.lineSeparator());
        sb.append(data.toString()).append(System.lineSeparator());
        for (IRFunction function : functions){
            sb.append(function.toString()).append(System.lineSeparator());
        }
        sb.append("end_program ").append(name);
        return sb.toString();
    }
}

package tiger.irinstruction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class IRProgram extends IRInstruction{
    private IRStaticData data;
    private List<IRFunction> functions;
    private final String name;
    private IRFunction mainFunction;

    public IRProgram (String name){
        this.name = name;
        this.functions = new ArrayList<>();
    }

    public List<IRFunction> getFunctions() {
        return functions;
    }

    public IRFunction getMainFunction(){
        return mainFunction;
    }

    public List<IRFunction> getFunctionsExceptMain() {
        return functions.stream()
                .filter(function -> !"main".equals(function.getFunctionName()))
                .collect(Collectors.toList());
    }

    //possible usage for parallel execution case
    public void setFunctions(List<IRFunction> functions) {
        this.functions = functions;
        for (IRFunction function: functions){
            if ("main".equals(function.getFunctionName())){
                this.mainFunction = function;
            }
        }
    }

    public void addFunction(IRFunction function){
        this.functions.add(function);
        if ("main".equals(function.getFunctionName())){
            this.mainFunction = function;
        }
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

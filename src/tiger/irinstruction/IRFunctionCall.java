package tiger.irinstruction;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IRFunctionCall extends IRInstruction{
    private final String functionName;
    private final List<String> args;
    private final String returnVar;


    public IRFunctionCall(String functionName, List<String> args, String returnVar){
        this.functionName = functionName;
        this.args = args;
        this.returnVar = returnVar;
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<String> getArgs() {
        return args;
    }

    public String getReturnVar() {
        return returnVar;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t\tcallr, ").append(returnVar).append(", ").append(functionName);
        if(!args.isEmpty()){
            sb.append(", ").append(String.join(", ", args));
        } else {
            sb.append(", ");
        }
        sb.append(System.lineSeparator());
        return sb.toString();
    }

    @Override
    public <T> T accept(IRVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public Set<String> getUse() {
        HashSet<String> use = new HashSet<>();
        for(String arg : args){
            if (!arg.matches("^\\d+$")) {
                use.add(arg);
            }
        }
        return use;
    }

    @Override
    public Set<String> getDef() {
        HashSet<String> def = new HashSet<>();
        if (!returnVar.matches("^\\d+$")) {
            def.add(returnVar);
        }
        return def;
    }

}

package tiger.irinstruction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IRProcedureCall extends IRInstruction{
    private final String functionName;
    private final ArrayList<String> args;

    public IRProcedureCall(String functionName, ArrayList<String> args) {
        this.functionName = functionName;
        this.args = args;
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<String> getArgs() {
        return args;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t\tcall, ").append(functionName);
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
        return new HashSet<>();
    }
}

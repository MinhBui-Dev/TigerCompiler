package tiger.irinstruction;

import java.util.*;

public class IRFunction extends IRInstruction{
    private List<IRInstruction> staticInitInstruction;
    private List<IRInstruction> instructions;
    private String functionName;
    private IRVarData data;
    private String returnType;
    private LinkedHashMap<String, String> functionArgs;

    public IRFunction(String name){
        this.functionName = name;
        this.returnType = null;
        this.staticInitInstruction = null;
        this.instructions = null;
        this.functionArgs = new LinkedHashMap<>();
    }

    public IRVarData getData() {
        return data;
    }

    public void setData(IRVarData data) {
        this.data = data;
    }

    public LinkedHashMap<String, String> getFunctionArgs() {
        return functionArgs;
    }

    public void setFunctionArgs(LinkedHashMap<String, String> functionArgs) {
        this.functionArgs = functionArgs;
    }

    public List<IRInstruction> getStaticInitInstruction() {
        return staticInitInstruction;
    }

    public void setStaticInitInstruction(List<IRInstruction> staticInitInstruction) {
        this.staticInitInstruction = staticInitInstruction;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public List<IRInstruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<IRInstruction> instructions) {
        this.instructions = instructions;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("start_function ").append(functionName).append(System.lineSeparator());
        if (returnType != null){
            sb.append("\t").append(returnType).append(" ").append(functionName).append("(");
        } else {
            sb.append("\tvoid ").append(functionName).append("(");
        }

        if (!functionArgs.isEmpty()){
            Set<String> key = functionArgs.keySet();
            for (String id: key){
                sb.append(functionArgs.get(id)).append(" ").append(id).append(", ");
            }
            sb.setLength(sb.length() -2);
        }
        sb.append(")").append(System.lineSeparator());
        sb.append(data.toString());

        sb.append("\t").append(functionName).append(":").append(System.lineSeparator());

        if (staticInitInstruction != null){
            for (IRInstruction instruction: staticInitInstruction){
                sb.append(instruction.toString());
            }
        }

        if(instructions != null){
            for (IRInstruction instruction: instructions){
                sb.append(instruction.toString());
            }
        }

        sb.append("end_function ").append(functionName).append(System.lineSeparator());
        return sb.toString();
    }
}

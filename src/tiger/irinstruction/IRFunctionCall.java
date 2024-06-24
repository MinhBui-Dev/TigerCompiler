package tiger.irinstruction;

public class IRFunctionCall extends IRInstruction{
    private final String functionName;
    private final String args;
    private final boolean hasReturn;
    private String returnVar;

    public IRFunctionCall(String functionName, String args){
        this.functionName = functionName;
        this.args = args;
        this.hasReturn = false;
    }

    public IRFunctionCall(String functionName, String args, String returnVar){
        this.functionName = functionName;
        this.args = args;
        this.hasReturn = true;
        this.returnVar = returnVar;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(hasReturn){
            sb.append("\t\tcallr, "+returnVar+", "+functionName+args);
        } else {
            sb.append("\t\tcall, "+functionName+args);
        }
        sb.append(System.lineSeparator());
        return sb.toString();
    }
}

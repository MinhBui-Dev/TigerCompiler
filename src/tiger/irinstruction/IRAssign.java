package tiger.irinstruction;

public class IRAssign extends IRInstruction {
    private final String target;
    private final String source;
    private String valueArray;
    private final boolean arrayAssign;

    public IRAssign(String target, String source){
        this.target = target;
        this.source = source;
        this.arrayAssign = false;
    }

    public IRAssign(String target, String source, String valueArray){
        this.target = target;
        this.source = source;
        this.valueArray = valueArray;
        this.arrayAssign = true;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(arrayAssign){
            sb.append("\t\tassign, "+target+", "+source+", "+valueArray);

        } else {
            sb.append("\t\tassign, "+target+", "+source+",");
        }
        sb.append(System.lineSeparator());
        return sb.toString();
    }
}

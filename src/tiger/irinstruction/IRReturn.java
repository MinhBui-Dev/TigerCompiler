package tiger.irinstruction;

public class IRReturn extends IRInstruction {
    private String value;
    private final boolean hasReturnValue;
    public IRReturn(String value) {
        this.value = value;
        this.hasReturnValue = true;
    }
    public IRReturn(){
        this.hasReturnValue = false;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (hasReturnValue) {
            sb.append("\t\treturn, "+value+", ,");
        } else {
            sb.append("\t\treturn, , ,");
        }
        sb.append(System.lineSeparator());
        return sb.toString();
    }
}

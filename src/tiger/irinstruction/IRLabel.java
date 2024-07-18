package tiger.irinstruction;

public class IRLabel extends IRInstruction{
    private final String label;

    public IRLabel(String label){
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public String toString(){
        return "\t" + label + ":" + System.lineSeparator();
    }
}

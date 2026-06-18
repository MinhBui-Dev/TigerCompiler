package tiger.mips32;

public class Label extends Instruction{
    private final String label;

    public Label(String label){
        this.label = label;
    }

    public String toString(){
        return label + ":" + System.lineSeparator();
    }
}

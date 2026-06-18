package tiger.mips32;

public class Jump extends Instruction{
    private final String label;

    public Jump(String label){
        this.label = label;
    }

    public String toString(){
        return "\tj " + label + System.lineSeparator();
    }
}

package tiger.mips32;

public class Return extends Instruction {
    private String label;

    public Return(String label) {
        this.label = label;
    }

    public String toString(){
        return String.format("\tjr %s", label) +System.lineSeparator();
    }

}

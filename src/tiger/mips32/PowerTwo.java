package tiger.mips32;

public class PowerTwo extends Instruction{
    private final String dest;
    private final String source;
    private final String intermediate;


    public PowerTwo(final String dest, final String source, final String intermediate) {
        this.dest = dest;
        this.source = source;
        this.intermediate = intermediate;
    }

    public String toString(){
        return String.format("\tsll %s, %s, %s", dest, source, intermediate) + System.lineSeparator();
    }
}

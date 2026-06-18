package tiger.mips32;

public class AddIntermediate extends Instruction{
    private final String source;
    private final String intermediate;
    private final String dest;

    public AddIntermediate(final String dest,final String source,final String intermediate) {
        this.source = source;
        this.intermediate = intermediate;
        this.dest = dest;
    }

    public String toString(){
        return String.format("\taddi %s, %s, %s", dest, source, intermediate) + System.lineSeparator();
    }
}

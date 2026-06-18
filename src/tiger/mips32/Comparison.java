package tiger.mips32;

public class Comparison extends Instruction {
    private final String source;
    private final String target;
    private final String dest;

    public Comparison(final String dest, final String source, final String target) {
        this.source = source;
        this.target = target;
        this.dest = dest;
    }

    public String toString(){
        return String.format("\tsltu %s, %s, %s", dest, source, target) + System.lineSeparator();
    }
}

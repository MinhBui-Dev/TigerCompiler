package tiger.mips32;

public class Div extends Instruction{
    private final String dest;
    private final String source;
    private final String target;


    public Div(final String dest, final String source, final String target ) {
        this.source = source;
        this.target = target;
        this.dest = dest;
    }

    public String toString(){
        return String.format("\tdiv %s, %s, %s", dest, source, target) + System.lineSeparator();
    }
}

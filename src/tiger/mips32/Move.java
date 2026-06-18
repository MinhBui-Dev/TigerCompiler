package tiger.mips32;

public class Move extends Instruction {
    private final String dest;
    private final String src;

    public Move(final String dest, final String src) {
        this.dest = dest;
        this.src = src;
    }

    public String toString(){
        return String.format("\tmove %s, %s", dest, src) + System.lineSeparator();
    }
}

package tiger.mips32;

public class LoadWordOffset extends Instruction {
    private final String destination;
    private final String source;
    private final String offset;

    public LoadWordOffset(String destination, String source, String offset){
        this.destination = destination;
        this.source = source;
        this.offset = offset;
    }

    public String toString() {
        return String.format("\tlw %s, %s(%s)", destination, offset, source) + System.lineSeparator();
    }
}

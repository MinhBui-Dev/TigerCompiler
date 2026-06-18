package tiger.mips32;

public class StoreWordOffset extends Instruction {
    private final String destination;
    private final String source;
    private final String offset;

    public StoreWordOffset(String destination, String source, String offset){
        this.destination = destination;
        this.source = source;
        this.offset = offset;
    }

    public String toString() {
        return String.format("\tsw %s, %s(%s)", destination, offset, source) + System.lineSeparator();
    }

    //Store value at memory address (local data)
}

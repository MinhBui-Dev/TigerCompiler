package tiger.mips32;

public class StoreWord extends Instruction{
    private final String destination;
    private final String source;

    public StoreWord(String destination, String source){
        this.destination = destination;
        this.source = source;
    }

    public String toString() {
        return String.format("\tsw %s, %s", destination, source) + System.lineSeparator();
    }

    //Store value at label (Static Data)
}

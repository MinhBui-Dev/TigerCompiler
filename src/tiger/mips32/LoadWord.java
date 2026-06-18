package tiger.mips32;

public class LoadWord extends Instruction{
    private final String destination;
    private final String source;

    public LoadWord(String destination, String source){
        this.destination = destination;
        this.source = source;
    }

    public String toString() {
        return String.format("\tlw %s, %s", destination, source) + System.lineSeparator();
    }

    //Load value at label (static data)
}

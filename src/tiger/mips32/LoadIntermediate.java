package tiger.mips32;

public class LoadIntermediate extends Instruction {
    private final String destination;
    private final String intValue;

    public LoadIntermediate(String destination, String intValue) {
        this.destination = destination;
        this.intValue = intValue;
        this.comment = "";
    }

    public LoadIntermediate(String destination, String intValue, String comment) {
        this.destination = destination;
        this.intValue = intValue;
        this.comment = comment;
    }

    public String toString() {
        return String.format("\tli %s, %s \t%s", destination, intValue, comment) + System.lineSeparator();
    }

}

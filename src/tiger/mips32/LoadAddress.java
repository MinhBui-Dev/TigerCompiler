package tiger.mips32;

public class LoadAddress extends Instruction{
    private final String destination;
    private final String address;

    public LoadAddress(String destination, String address) {
        this.destination = destination;
        this.address = address;
    }

    public String toString() {
        return String.format("\tla %s, %s", destination, address) + System.lineSeparator();
    }
}

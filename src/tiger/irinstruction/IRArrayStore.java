package tiger.irinstruction;

public class IRArrayStore extends IRInstruction {
    private final String value;
    private final String array;
    private final String index;

    public IRArrayStore(String value, String array, String index) {
        this.value = value;
        this.array = array;
        this.index = index;
    }


    @Override
    public String toString() {
        String sb = "\t\tarray_store, " + array + ", " + index + ", " + value +
                System.lineSeparator();
        return sb;
    }
}

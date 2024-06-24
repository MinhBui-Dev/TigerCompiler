package tiger.irinstruction;

public class IRArrayLoad extends IRInstruction {
    private final String result;
    private final String array;
    private final String index;

    public IRArrayLoad(String result, String array, String index) {
        this.result = result;
        this.array = array;
        this.index = index;
    }


    @Override
    public String toString() {
        String sb = "\t\tarray_load, " + result + ", " + array + ", " + index +
                System.lineSeparator();
        return sb;
    }
}

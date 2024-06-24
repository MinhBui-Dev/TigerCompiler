package tiger.irinstruction;

public class IRArrayStore extends IRInstruction {
    private String value;
    private String array;
    private String index;

    public IRArrayStore(String value, String array, String index) {
        this.value = value;
        this.array = array;
        this.index = index;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t\tarray_store, "+array+", "+index+", "+value);
        sb.append(System.lineSeparator());
        return sb.toString();
    }
}

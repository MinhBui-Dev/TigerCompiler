package tiger.irinstruction;

public class IRArrayLoad extends IRInstruction {
    private String result;
    private String array;
    private String index;

    public IRArrayLoad(String result, String array, String index) {
        this.result = result;
        this.array = array;
        this.index = index;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t\tarray_load, "+ result +", "+array+", "+index);
        sb.append(System.lineSeparator());
        return sb.toString();
    }
}

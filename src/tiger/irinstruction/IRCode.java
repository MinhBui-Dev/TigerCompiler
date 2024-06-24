package tiger.irinstruction;

public class IRCode extends IRInstruction{
    private StringBuilder sb;

    public IRCode(String string) {
        sb = new StringBuilder();
        sb.append(string);
        sb.append(System.lineSeparator());

    }

    public void addCode(String code){
        sb.append(code);
    }

    @Override
    public String toString(){
        return sb.toString();
    }
}

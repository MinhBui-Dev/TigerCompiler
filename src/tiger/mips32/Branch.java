package tiger.mips32;

public class Branch extends Instruction{
    private final String op;
    private final String source;
    private final String target;
    private final String label;

    public Branch(final String op,final String source,final String target,final String label) {
        this.op = op;
        this.source = source;
        this.target = target;
        this.label = label;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        switch (op) {
            case "==":
                sb.append(String.format("\tbeq %s, %s, %s", source, target, label));
                break;
            case "!=":
                sb.append(String.format("\tbne %s, %s, %s", source, target, label));
                break;
            case "<":
                sb.append(String.format("\tblt %s, %s, %s", source, target, label));
                break;
            case ">":
                sb.append(String.format("\tbgt %s, %s, %s", source, target, label));
                break;
            case "<=":
                sb.append(String.format("\tble %s, %s, %s", source, target, label));
                break;
            case ">=":
                sb.append(String.format("\tbge %s, %s, %s", source, target, label));
                break;
        }
        sb.append(System.lineSeparator());
        return sb.toString();
    }
}

package tiger.mips32;

public class LibCall extends Instruction {
    private String arg;
    private String lib;

    public LibCall(String arg, String lib) {
        this.arg = arg;
        this.lib = lib;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        if (lib.equals("printi")){
            sb.append("\tli $v0, 1").append(System.lineSeparator());
        } else {
            sb.append("\tli $v0, 17").append(System.lineSeparator());
        }
        sb.append(String.format("\tmove $a0, %s", arg)).append(System.lineSeparator());
        sb.append(String.format("\tsyscall")).append(System.lineSeparator());
        sb.append("\tli $v0, 11").append(System.lineSeparator());
        sb.append("\tli $a0, 10").append(System.lineSeparator());
        sb.append(String.format("\tsyscall")).append(System.lineSeparator());
        return sb.toString();
    }
}

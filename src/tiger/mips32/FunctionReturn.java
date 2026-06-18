package tiger.mips32;

import java.util.ArrayList;
import java.util.List;

public class FunctionReturn extends Instruction{
    private final String returnReg;
    private ArrayList<String> args;
    private final String functionName;

    public FunctionReturn(String returnAddress, String functionName, ArrayList<String> args) {
        this.functionName = functionName;
        this.args = args;
        this.returnReg = returnAddress;

    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        int offset = 0;
        if (this.args.size() < 5) {
            offset = (args.size() * 4) - 4;
            for (int i = 0; i < args.size(); i++) {
                sb.append(String.format("\tsw %s, %d($sp)", args.get(i), offset));
                sb.append(System.lineSeparator());
                offset -= 4;
            }
        } else {
            offset = (args.size() * 4) - 4;
            for (int i = 0; i < args.size(); i++) {
                sb.append(String.format("\tsw %s, %d($sp)", args.get(i), offset));
                sb.append(System.lineSeparator());
                offset -= 4;
            }
        }

        sb.append("\tjal " +  functionName + System.lineSeparator());

        sb.append(System.lineSeparator());


        sb.append(String.format("\tmove %s, $v0", returnReg));
        sb.append(System.lineSeparator());
        return sb.toString();
    }
}

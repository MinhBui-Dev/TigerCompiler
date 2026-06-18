package tiger.mips32;

import java.util.ArrayList;
import java.util.LinkedList;

public class FunctionCall extends Instruction{
    private final String functionLabel;
    private final ArrayList<String> arguments;

    public FunctionCall(String function, ArrayList<String> arguments){
        this.functionLabel = function;
        this.arguments = arguments;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int offset = 0;
        if (this.arguments.size() < 5) {
            offset = (arguments.size() * 4) - 4;
            for (int i = 0; i < arguments.size(); i++) {
                sb.append(String.format("\tsw %s, %d($sp)", arguments.get(i), offset));
                sb.append(System.lineSeparator());
                offset -= 4;
            }
        } else {
            offset = (arguments.size() * 4) - 4;
            for (int i = 0; i < arguments.size(); i++) {
                sb.append(String.format("\tsw %s, %d($sp)", arguments.get(i), offset));
                sb.append(System.lineSeparator());
                offset -= 4;
            }
        }
        sb.append("\tjal " +  functionLabel + System.lineSeparator());

        return sb.toString();
    }
}

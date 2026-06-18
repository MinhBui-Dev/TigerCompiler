package tiger.mips32;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Data extends Instruction{

    private final HashMap<String, String> intVars;
    private final HashMap<String, String> floatVars;

    public Data(HashMap<String, String> intVars, HashMap<String, String> floatVars){
        this.intVars = intVars;
        this.floatVars = floatVars;
    }


    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(".data").append(System.lineSeparator());
        if(!intVars.isEmpty()){
            for(String var : intVars.keySet()){
                //check if var is array var
                if (var.contains("[")) {
                    // Use regular expression to find the integer between "[" and "]"
                    String[] details = extractArrayDetails(var);
                    String arrayName = details[0];
                    String size = details[1];
                    sb.append(String.format("%s: .word %s:%s",arrayName,intVars.get(var),size));
                } else {
                    sb.append(String.format("%s: .word %s",var,intVars.get(var)));
                }
                sb.append(System.lineSeparator());
            }
        }
        sb.append(System.lineSeparator());
        sb.append(".text").append(System.lineSeparator());
        sb.append(".globl main").append(System.lineSeparator());
        return sb.toString();
    }

    public static String[] extractArrayDetails(String s) {
        int openBracketIndex = s.indexOf('[');
        int closeBracketIndex = s.indexOf(']');

        if (openBracketIndex != -1 && closeBracketIndex != -1 && openBracketIndex < closeBracketIndex) {
            String arrayName = s.substring(0, openBracketIndex);
            String arraySize = s.substring(openBracketIndex + 1, closeBracketIndex);
            return new String[]{arrayName, arraySize};
        }

        return null;
    }

}

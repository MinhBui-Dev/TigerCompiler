package TigerCompiler;

import antlr.TigerLexer;
import antlr.TigerParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Tigerc {
    public static void main(String[] args) {
        // Checking if args are valid
        if (args.length < 2) {
            System.out.println("Usage: java ./Tigerc -f <filename> -s -p");
            System.exit(1);
        } else if (args.length > 4) {
            System.out.println("Usage: java ./Tigerc -f <filename> -s -p");
            System.exit(1);
        } else {
            // check for the number of arguments -f filename -s -p flag and set boolean values accordingly
            boolean printTokenArg = false;
            boolean printTreeArg = false;
            boolean fileArg = false;
            boolean invalidArg = false;
            String filename = null;
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "-f":
                        fileArg = true;
                        i++;
                        filename = args[i];
                        break;
                    case "-s":
                        printTokenArg = true;
                        break;
                    case "-p":
                        printTreeArg = true;
                        break;
                    default:
                        invalidArg = true;
                        break;
                }
            }
            if (invalidArg || !fileArg){
                //Exit with return code 1 due to invalid arguments
                System.out.println("Usage: java ./Tigerc -f <filename> -s -p");
                System.exit(1);
            }

            try {
                TigerParser parser = getParser(filename);
            } catch (IOException e) {
                // File not found
                System.exit(1);
            }
        }


    }

    private static TigerParser getParser(String filename) throws IOException {
        TigerParser parser = null;

        //Create Parser
        CharStream input = CharStreams.fromFileName(filename);
        TigerLexer lexer = new TigerLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        parser = new TigerParser(tokens);

        return parser;
    }
}

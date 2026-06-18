package tigercompiler;

import tiger.*;
import antlr.TigerParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import tiger.ast.AstNode;
import tiger.irinstruction.IRProgram;
import tiger.mips32.Instruction;
import tiger.symbol.ScopedSymbolTable;
import tigercompiler.codeanalysis.CFG;
import tigercompiler.codeanalysis.MyCodeAnalyzer;
import tigercompiler.registorallocator.BriggsRegisterAllocationStrategy;
import tigercompiler.registorallocator.NaiveRegisterAllocationStrategy;
import tigercompiler.registorallocator.RegisterAllocationStrategy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Tigerc {
    public static void main(String[] args) {
        // Checking if args are valid
        if (args.length < 2) {
            System.out.println("Usage: java -jar ./Tigerc.jar -f <filename> -s -p -t -i");
            System.exit(1);
        } else if (args.length > 14) {
            System.out.println("Usage: java -jar ./Tigerc.jar -f <filename> -s -p -t -i -c -v");
            System.exit(1);
        } else {
            // check for the number of arguments -f filename -s -p flag and set boolean values accordingly
            boolean printTokenArg = false;
            boolean printTreeArg = false;
            boolean fileArg = false;
            boolean invalidArg = false;
            String filename = null;


            //Phase 2
            boolean printTableArg = false;
            boolean printIRCodeArg = false;

            //Phase 3
            boolean printCFGArg = false;
            boolean printLivenessArg = false;
            boolean runNaive = false;
            boolean runBriggs = false;
            boolean printtInterfernceGraph = false;
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
                    case "-t":
                        printTableArg = true;
                        break;
                    case "-i":
                        printIRCodeArg = true;
                        break;
                    case "-c":
                        printCFGArg = true;
                        break;
                    case "-v":
                        printLivenessArg = true;
                        break;
                    case "-n":
                        runNaive = true;
                        break;
                    case "-g":
                        runBriggs = true;
                        break;
                    case "-r":
                        i++;
                        break;
                    case "-a":
                        printtInterfernceGraph = true;
                        break;
                    default:
                        invalidArg = true;
                        break;
                }
            }
            if (invalidArg || !fileArg){
                //Exit with return code 1 due to invalid arguments
                System.out.println("Usage: java -jar ./Tigerc.jar -f <filename> -s -p -t -i");
                System.exit(1);
            }

            try {
                TigerParser parser = getParser(filename);
                parser.removeErrorListeners(); //remove default error listener
                parser.addErrorListener(new TigerErrorListener()); //add my error listner

                ParseTree TigerParseTree = parser.tiger_program();
                MyTigerListener listener = new MyTigerListener();
                ParseTreeWalker walker = new ParseTreeWalker();

                walker.walk(listener, TigerParseTree);

                //Print Token
                if (printTokenArg) {
                    String name = filename.substring(0, filename.length() - 6) + ".tokens";
                    File file = new File(name);
                    file.createNewFile();
                    FileWriter writer = new FileWriter(file);
                    LinkedList<TokenTuple> tokenSet = listener.getTokenSet();
                    for (TokenTuple token : tokenSet) {
                        writer.write("<" + token.getKey() + ", \"" + token.getText() + "\">\n");
                    }
                    writer.close();
                }
                if (printTreeArg) {
                    String name = filename.substring(0, filename.length() - 6) + ".tree.gv";
                    File file = new File(name);
                    file.createNewFile();
                    FileWriter writer = new FileWriter(file);
                    String dot = MyTigerListener.getTree();
                    writer.write(dot);
                    writer.close();
                }

                //Phase Two
                MyTigerSemanticAnalyzer visitor = new MyTigerSemanticAnalyzer();
                AstNode program = visitor.visit(TigerParseTree);
                ScopedSymbolTable table = visitor.getSymbolTable();
                List<String> semanticErrors = visitor.getSemanticError();

                if (!semanticErrors.isEmpty()) {
                    for (String error : semanticErrors) {
                        System.err.println(error);
                    }
                    System.exit(4);
                }


                MyIRGenerator irGenerator = new MyIRGenerator(table);
                IRProgram programIR = irGenerator.generate(program);

                if (printTableArg) {
                    //print out table
                    //Walk symbol table tree and print out
                    String tableString = traverseTable(table);
                    String name = filename.substring(0, filename.length() - 6) + ".st";
                    File file = new File(name);
                    file.createNewFile();
                    FileWriter writer = new FileWriter(file);
                    writer.write(tableString);
                    writer.close();
                }

                if (printIRCodeArg) {
                    //print out IR code
                    String name = filename.substring(0, filename.length() - 6) + ".ir";
                    File file = new File(name);
                    file.createNewFile();
                    FileWriter writer = new FileWriter(file);
                    writer.write(programIR.toString());
                    writer.close();

                }

                //Phase 3
                MyCodeAnalyzer codeAnalyzer = new MyCodeAnalyzer(programIR);
                List<CFG> cfgList = codeAnalyzer.getCfgList();


                if (printCFGArg){
                    String name = filename.substring(0, filename.length() - 6) + ".cfg.gv";
                    System.out.println("Print CFG");
                    File file = new File(name);
                    file.createNewFile();
                    FileWriter writer = new FileWriter(file);
                    writer.write("digraph CFG {\n");
                    for (CFG cfg: cfgList){
                        writer.write(cfg.printCFGAsDOT());
                    }
                    writer.write("}\n");
                    writer.close();
                    System.out.println("Done Printing");
                }

                if (printLivenessArg){
                    String name = filename.substring(0, filename.length() - 6) + ".liveness";
                    System.out.println("Print liveness analysis");
                    File file = new File(name);
                    file.createNewFile();
                    FileWriter writer = new FileWriter(file);
                    for (CFG cfg: cfgList){
                        writer.write(cfg.printLivenessAnalysis());
                    }
                    writer.close();
                    System.out.println("Done Printing");
                }

                if (printtInterfernceGraph) {
                    String name = filename.substring(0, filename.length() - 6) + "ifg.gv";
                    File file = new File(name);
                    file.createNewFile();
                    FileWriter writer = new FileWriter(file);
                    writer.write("digraph IFG {\n");
                    for (CFG cfg: cfgList){
                        writer.write(cfg.printInterferenceGraph());
                    }
                    writer.write("}\n");
                    writer.close();
                }

                if (runBriggs){
                    MyMIPSGenerator generator = new MyMIPSGenerator(programIR,"briggs");
                    List<Instruction> MIPSInstruction = generator.generateMIPS(cfgList);

                    String name = filename.substring(0, filename.length() - 6) + ".briggs.s";
                    File file = new File(name);
                    file.createNewFile();
                    FileWriter writer = new FileWriter(file);
                    for (Instruction instruction: MIPSInstruction){
                        writer.write(instruction.toString());
                    }
                    writer.close();
                    System.out.println("Done Printing");
                }

                if (runNaive){

                    MyMIPSGenerator generator = new MyMIPSGenerator(programIR,"naive");
                    List<Instruction> MIPSInstruction = generator.generateMIPS(cfgList);

                    String name = filename.substring(0, filename.length() - 6) + ".naive.s";
                    File file = new File(name);
                    file.createNewFile();
                    FileWriter writer = new FileWriter(file);
                    for (Instruction instruction: MIPSInstruction){
                        writer.write(instruction.toString());
                    }
                    writer.close();
                    System.out.println("Done Printing");
                }


            } catch (RuntimeException e) {
                //check for lexer or parser error
                if (e.getMessage() == null){
                    e.printStackTrace();
                    System.exit(1);
                }
                if (e.getMessage().equals("ParseCancellationException")) {
                    System.exit(3);
                } else if (e.getMessage().equals("LexerNoViableAltException")) {
                    System.exit(2);
                } else if (e.getMessage().equals("SemanticError")) {
                    System.exit(4);
                } else {
                    System.err.println("error: " + e.getMessage());
                    System.exit(1);
                }
            } catch (IOException e) {
                //Write error message and exit with return code 1
                System.err.println("error: " + e.getMessage());
                System.exit(1);
            }
        }


    }

    private static TigerParser getParser(String filename) throws RuntimeException {
        TigerParser parser;

        //Create Parser
        //Convert IOException to RuntimeException
        try {
            CharStream input = CharStreams.fromFileName(filename);
            MyTigerLexer lexer = new MyTigerLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            parser = new TigerParser(tokens);

            return parser;
        } catch (IOException e) {
            throw new RuntimeException("File not found");
        }

    }

    private static String traverseTable(ScopedSymbolTable symbolTable) {
        if (symbolTable == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(symbolTable);
        for (ScopedSymbolTable tab : symbolTable.getChildScopes()) {
            sb.append(traverseTable(tab));
        }
        return sb.toString();
    }
}

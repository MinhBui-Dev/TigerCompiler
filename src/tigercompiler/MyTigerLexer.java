package tigercompiler;

import antlr.TigerLexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.LexerNoViableAltException;

//Implementing lexical error handling to throw a runtime exception at first lexical error
//Allow the program to return to the main method and exit with code 2
public class MyTigerLexer extends TigerLexer {
    public MyTigerLexer(CharStream input) {
        super(input);
    }

    @Override
    public void recover(LexerNoViableAltException e) {
        System.err.println("Lexer Error: line " + getLine() + ":" + getCharPositionInLine() + " " + e.getMessage());
        throw new RuntimeException("LexerNoViableAltException"); // Bail out
    }
}

package tiger;

import antlr.TigerLexer;
import antlr.TigerParser;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class TigerErrorListener extends BaseErrorListener {
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        // check if recognizer is a parser or lexer
        //System.err.println("Syntax Error: line " + line + ":" + charPositionInLine + " " + msg);
        System.err.println("Parser Error: line " + line + ":" + charPositionInLine + " " + msg);
        throw new RuntimeException("ParseCancellationException");
    }
}

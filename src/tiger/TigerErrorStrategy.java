package tiger;

import org.antlr.v4.runtime.*;
//TODO Implement the error strategy
public class TigerErrorStrategy extends DefaultErrorStrategy {
    @Override
    public void sync(Parser recognizer) throws RecognitionException {
        super.sync(recognizer);
    }

    @Override
    public void recover(Parser recognizer, RecognitionException e) {
        super.recover(recognizer, e);
    }

    @Override
    public Token recoverInline(Parser recognizer) throws RecognitionException {
        return super.recoverInline(recognizer);
    }

    @Override
    protected void reportNoViableAlternative(Parser recognizer, NoViableAltException e) {
        super.reportNoViableAlternative(recognizer, e);
    }

    @Override
    protected void reportInputMismatch(Parser recognizer, InputMismatchException e) {
        super.reportInputMismatch(recognizer, e);
    }

    @Override
    protected void reportFailedPredicate(Parser recognizer, FailedPredicateException e) {
        super.reportFailedPredicate(recognizer, e);
    }
}

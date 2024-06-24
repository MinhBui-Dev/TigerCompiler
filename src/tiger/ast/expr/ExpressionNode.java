package tiger.ast.expr;

import tiger.ast.AstNode;

public abstract class ExpressionNode extends AstNode {
    public String toString(){
        return this.getClass().getSimpleName();
    }
}

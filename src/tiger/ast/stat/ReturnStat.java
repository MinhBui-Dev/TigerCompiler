package tiger.ast.stat;

import tiger.ast.ASTVisitor;
import tiger.ast.expr.ExpressionNode;

public class ReturnStat extends StatNode {
    private ExpressionNode returnExpr;

    public ReturnStat(ExpressionNode returnExpr) {
        this.returnExpr = returnExpr;
    }

    public boolean HasReturn() {
        return returnExpr != null;
    }

    public ExpressionNode getReturnExpr() {
        return returnExpr;
    }

    public void setReturnExpr(ExpressionNode returnExpr) {
        this.returnExpr = returnExpr;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

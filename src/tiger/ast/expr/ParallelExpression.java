package tiger.ast.expr;


import tiger.ast.ASTVisitor;

public class ParallelExpression extends ExpressionNode {

    private ExpressionNode expr;

    public ParallelExpression(ExpressionNode expr) {
        this.expr = expr;
    }

    public ExpressionNode getExpr() {
        return expr;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

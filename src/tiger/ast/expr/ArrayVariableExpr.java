package tiger.ast.expr;

import tiger.ast.ASTVisitor;

public class ArrayVariableExpr extends ExpressionNode {
    private String ID;
    private ExpressionNode indexExpression;
    public ArrayVariableExpr(String ID, ExpressionNode index) {
        this.ID = ID;
        this.indexExpression = index;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public ExpressionNode getIndexExpression() {
        return indexExpression;
    }

    public void setIndexExpression(ExpressionNode indexExpression) {
        this.indexExpression = indexExpression;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

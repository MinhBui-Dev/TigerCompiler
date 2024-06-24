package tiger.ast.expr;

import tiger.ast.ASTVisitor;

public class BinaryOpExpression extends ExpressionNode{
    private ExpressionNode leftExpression;
    private ExpressionNode rightExpression;
    private String operator;

    public BinaryOpExpression(ExpressionNode leftExpression, ExpressionNode rightExpression, String operator){
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.operator = operator;
    }

    public ExpressionNode getLeftExpression() {
        return leftExpression;
    }

    public ExpressionNode getRightExpression() {
        return rightExpression;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

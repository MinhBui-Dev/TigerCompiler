package tiger.ast.expr;

import tiger.ast.ASTVisitor;

public class Comparison extends ExpressionNode {
    private ExpressionNode left;
    private ExpressionNode right;
    private String operator;
    public Comparison(ExpressionNode left, ExpressionNode right, String op){
        this.left = left;
        this.right = right;
        this.operator = op;
    }

    public ExpressionNode getLeft() {
        return left;
    }

    public ExpressionNode getRight() {
        return right;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

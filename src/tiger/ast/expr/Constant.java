package tiger.ast.expr;

import tiger.ast.ASTVisitor;

public class Constant extends ExpressionNode {
    private final int num;
    public Constant(int num){
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

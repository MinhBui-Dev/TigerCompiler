package tiger.ast.expr;


import tiger.ast.ASTVisitor;

public class VariableExpr extends ExpressionNode {
    private final String ID;
    private final String type;
    public VariableExpr(String ID, String type) {
        this.ID = ID;
        this.type = type;
    }

    public String getID() {
        return ID;
    }

    public String getType() {
        return type;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

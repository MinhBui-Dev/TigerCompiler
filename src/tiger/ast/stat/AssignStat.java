package tiger.ast.stat;

import tiger.ast.ASTVisitor;
import tiger.ast.expr.ExpressionNode;
import tiger.ast.expr.VariableExpr;

public class AssignStat extends StatNode {
    private ExpressionNode variable;
    private ExpressionNode expressionNode;
    public AssignStat(ExpressionNode variable, ExpressionNode expressionNode){
        this.variable = variable;
        this.expressionNode = expressionNode;
    }

    public ExpressionNode getVariable() {
        return variable;
    }

    public ExpressionNode getExpressionNode() {
        return expressionNode;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

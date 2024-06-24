package tiger.ast.stat;

import tiger.ast.ASTVisitor;
import tiger.ast.expr.ExpressionNode;

import java.util.List;

public class IfElseStat extends StatNode {
    private ExpressionNode condition;
    private List<StatNode> thenBody;
    private List<StatNode> elseBody;
    public IfElseStat(ExpressionNode condition, List<StatNode> thenBody, List<StatNode> elseBody) {
        this.condition = condition;
        this.thenBody = thenBody;
        this.elseBody = elseBody;
    }

    public ExpressionNode getCondition() {
        return condition;
    }

    public List<StatNode> getThenBody() {
        return thenBody;
    }

    public List<StatNode> getElseBody() {
        return elseBody;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

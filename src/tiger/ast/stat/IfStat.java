package tiger.ast.stat;

import tiger.ast.ASTVisitor;
import tiger.ast.expr.ExpressionNode;

import java.util.List;

public class IfStat extends StatNode {
    private ExpressionNode condition;
    private List<StatNode> thenBody;
    public IfStat(ExpressionNode condition, List<StatNode> thenBody) {
        this.condition = condition;
        this.thenBody = thenBody;
    }

    public ExpressionNode getCondition() {
        return condition;
    }

    public List<StatNode> getThenBody() {
        return thenBody;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

}

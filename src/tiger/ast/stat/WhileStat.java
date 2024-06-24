package tiger.ast.stat;

import tiger.ast.ASTVisitor;
import tiger.ast.expr.ExpressionNode;

import java.util.List;

public class WhileStat extends StatNode {
    private ExpressionNode condition;
    private List<StatNode> body;
    public WhileStat(ExpressionNode condition, List<StatNode> body) {
        this.condition = condition;
        this.body = body;
    }

    public ExpressionNode getCondition() {
        return condition;
    }

    public List<StatNode> getBody() {
        return body;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

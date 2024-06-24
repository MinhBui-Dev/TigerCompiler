package tiger.ast.stat;

import tiger.ast.ASTVisitor;
import tiger.ast.expr.ExpressionNode;

import java.util.List;

public class ForStat extends StatNode {
    private String index;
    private ExpressionNode lowerBound;
    private ExpressionNode upperBound;
    private List<StatNode> body;
    public ForStat(String index, ExpressionNode lowerBound, ExpressionNode upperBound, List<StatNode> body) {
        this.index = index;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.body = body;
    }

    public String getIndex() {
        return index;
    }

    public ExpressionNode getLowerBound() {
        return lowerBound;
    }

    public ExpressionNode getUpperBound() {
        return upperBound;
    }

    public List<StatNode> getBody() {
        return body;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

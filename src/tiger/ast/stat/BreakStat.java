package tiger.ast.stat;

import tiger.ast.ASTVisitor;

public class BreakStat extends StatNode {
    public BreakStat() {
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

}

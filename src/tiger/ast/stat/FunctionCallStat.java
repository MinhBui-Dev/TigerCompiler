package tiger.ast.stat;

import tiger.ast.ASTVisitor;
import tiger.ast.expr.ExpressionNode;
import tiger.ast.expr.VariableExpr;

import java.util.List;

public class FunctionCallStat extends StatNode {
    private final String functionName;
    private final ExpressionNode assignTo;
    private final List<ExpressionNode> arguments;
    public FunctionCallStat(String functionName , ExpressionNode assignTo, List<ExpressionNode> arguments) {
        this.functionName = functionName;
        this.assignTo = assignTo;
        this.arguments = arguments;
    }

    public String getFunctionName() {
        return functionName;
    }

    public ExpressionNode getAssignTo() {
        return assignTo;
    }

    public List<ExpressionNode> getArguments() {
        return arguments;
    }

    public boolean isAssignment() {
        return assignTo != null;
    }

    public boolean hasArgs(){
        return arguments != null;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

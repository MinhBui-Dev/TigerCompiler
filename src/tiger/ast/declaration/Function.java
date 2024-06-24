package tiger.ast.declaration;

import java.util.ArrayList;
import java.util.List;

import tiger.ast.ASTVisitor;
import tiger.ast.stat.StatNode;
import tiger.ast.type.Type;

public class Function extends DeclarationNode {
    private final String id;
    private final List<Variable> args;
    private final Type returnType;
    private final List<StatNode> body;

    public Function(String id, List<Variable> args, Type returnType, List<StatNode> body) {
        this.id = id;
        this.args = args;
        this.returnType = returnType;
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public List<Variable> getArgs() {
        return args;
    }

    public Type getReturnType() {
        return returnType;
    }

    public List<StatNode> getBody() {
        return body;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

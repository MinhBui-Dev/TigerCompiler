package tiger.ast.type;

import tiger.ast.ASTVisitor;

public class BaseType extends Type {
    private final String typeName;

    public BaseType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    @Override
    public String toString() {
        return typeName;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

package tiger.ast.type;

import tiger.ast.ASTVisitor;

public class TypeID extends Type {

    private final String id;
    private final Type type;

    public TypeID(java.lang.String id, Type type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return type.toString();
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

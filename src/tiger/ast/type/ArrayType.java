package tiger.ast.type;

import tiger.ast.ASTVisitor;

public class ArrayType extends Type {
    private final int size;
    private final BaseType baseType;

    public ArrayType(int size, BaseType baseType) {
        this.size = size;
        this.baseType = baseType;
    }

    public int getSize() {
        return size;
    }

    public BaseType getBaseType() {
        return baseType;
    }

    @Override
    public String toString() {
        return "ArrayType," + " size=" + size + ", baseType=" + baseType;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

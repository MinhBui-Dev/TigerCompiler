package tiger.ast.declaration;

import tiger.ast.ASTVisitor;
import tiger.ast.AstNode;
import tiger.ast.expr.Constant;
import tiger.ast.type.Type;

import java.util.List;

public class Variable extends DeclarationNode {
    private String storageClass;
    private List<String> ID;
    private Type type;
    private Constant value;

    public Variable(java.lang.String storageClass, List<java.lang.String> ID, Type type, Constant value) {
        this.storageClass = storageClass;
        this.ID = ID;
        this.type = type;
        this.value = value;
    }

    public String getStorageClass() {
        return storageClass;
    }

    public List<String> getID() {
        return ID;
    }

    public Type getType() {
        return type;
    }

    public Constant getValue() {
        return value;
    }

    //Generate IR
    //If has value, generate IR for value and store in a register
    //Check Type
    //If type is int, assign, a, b,
    //If type is array: assign, a, 100, b (100 is the size of the array)


    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

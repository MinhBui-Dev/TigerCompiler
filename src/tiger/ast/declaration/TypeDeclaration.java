package tiger.ast.declaration;

import tiger.ast.ASTVisitor;
import tiger.ast.type.Type;

public class TypeDeclaration extends DeclarationNode {
    public String name;
    public Type type;
    public TypeDeclaration(String name, Type type){
        this.name = name;
        this.type = type;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

}

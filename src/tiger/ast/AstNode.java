package tiger.ast;

public abstract class AstNode {

    public abstract <T> T accept(ASTVisitor<T> visitor);
}

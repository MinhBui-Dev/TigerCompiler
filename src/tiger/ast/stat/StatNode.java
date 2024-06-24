package tiger.ast.stat;

import tiger.ast.AstNode;

public abstract class StatNode extends AstNode {

    public String toString(){
        return this.getClass().getSimpleName();
    }
}

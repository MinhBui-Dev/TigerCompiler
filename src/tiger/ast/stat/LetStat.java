package tiger.ast.stat;

import tiger.ast.ASTVisitor;
import tiger.ast.declaration.DeclarationNode;
import tiger.ast.declaration.TypeDeclaration;
import tiger.ast.declaration.Variable;

import java.util.List;

public class LetStat extends StatNode {
    private List<Variable> variablesDeclared;
    private List<TypeDeclaration> typesDeclared;
    private List<StatNode> statements;

    public LetStat(List<Variable> variablesDeclared, List<TypeDeclaration> typesDeclared, List<StatNode> statements) {
        this.variablesDeclared = variablesDeclared;
        this.typesDeclared = typesDeclared;
        this.statements = statements;
    }

    public List<Variable> getVariablesDeclared() {
        return variablesDeclared;
    }

    public List<TypeDeclaration> getTypesDeclared() {
        return typesDeclared;
    }

    public List<StatNode> getStatements() {
        return statements;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

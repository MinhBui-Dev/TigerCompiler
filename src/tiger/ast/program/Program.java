package tiger.ast.program;

import tiger.ast.ASTVisitor;
import tiger.ast.AstNode;
import tiger.ast.declaration.Function;
import tiger.ast.declaration.TypeDeclaration;
import tiger.ast.declaration.Variable;

import java.util.List;

public class Program extends AstNode {
    private final String programName;
    private final List<Variable> globals;
    private final List<TypeDeclaration> types;
    private final List<Function> functions;


    public Program(String programName, List<Variable> globals, List<TypeDeclaration> types, List<Function> functions) {
        this.programName = programName;
        this.globals = globals;
        this.types = types;
        this.functions = functions;
    }

    public String getProgramName() {
        return programName;
    }

    public List<Variable> getGlobals() {
        return globals;
    }

    public List<TypeDeclaration> getTypes() {
        return types;
    }

    public List<Function> getFunctions() {
        return functions;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

package tiger.ast;

import tiger.ast.declaration.*;
import tiger.ast.expr.*;
import tiger.ast.program.Program;
import tiger.ast.stat.*;
import tiger.ast.type.*;

public interface ASTVisitor<T> {
    T visit(Program node);
    T visit(Function function);
    T visit(TypeDeclaration typeDeclaration);
    T visit(Variable variable);
    T visit(ArrayVariableExpr arrayVariableExpr);
    T visit(Comparison comparison);
    T visit(Constant constant);
    T visit(VariableExpr variableExpr);
    T visit(AssignStat assignStat);
    T visit(BreakStat breakStat);
    T visit(ForStat forStat);
    T visit(FunctionCallStat functionCallStat);
    T visit(IfElseStat ifElseStat);
    T visit(IfStat ifStat);
    T visit(LetStat letStat);
    T visit(ReturnStat returnStat);
    T visit(WhileStat whileStat);
    T visit(ArrayType arrayType);
    T visit(BaseType baseType);
    T visit(TypeID typeID);

    T visit(BinaryOpExpression binaryOpExpression);
    T visit(ParallelExpression parallelExpression);
}

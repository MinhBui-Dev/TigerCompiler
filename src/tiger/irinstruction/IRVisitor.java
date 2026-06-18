package tiger.irinstruction;

public interface IRVisitor<T>{

    T visit(IRArrayLoad irArrayLoad);

    T visit(IRArrayStore irArrayStore);

    T visit(IRAssign irAssign);

    T visit(IRBinaryOp irBinaryOp);

    T visit(IRBreak irBreak);

    T visit(IRComparisonBranch irComparisonBranch);

    T visit(IRFunction irFunction);

    T visit(IRFunctionCall irFunctionCall);

    T visit(IRLabel irLabel);

    T visit(IRProgram irProgram);

    T visit(IRReturn irReturn);

    T visit(IRStaticData irStaticData);

    T visit(IRVarData irVarData);

    T visit(IRAssignArray irAssignArray);

    T visit(IRProcedureCall irProcedureCall);

    T visit(IRAssignConstant irAssignConstant);
}

package tigercompiler;

import tiger.ast.ASTVisitor;
import tiger.ast.AstNode;
import tiger.ast.declaration.Function;
import tiger.ast.declaration.TypeDeclaration;
import tiger.ast.declaration.Variable;
import tiger.ast.expr.*;
import tiger.ast.program.Program;
import tiger.ast.stat.*;
import tiger.ast.type.ArrayType;
import tiger.ast.type.BaseType;
import tiger.ast.type.Type;
import tiger.ast.type.TypeID;
import tiger.irinstruction.*;
import tiger.symbol.ScopedSymbolTable;
import tiger.symbol.TypeSymbol;
import tiger.symbol.VariableSymbol;

import java.util.*;

public class MyIRGenerator implements ASTVisitor<Void> {

    private List<IRInstruction> instructions;
    private List<IRInstruction> tempInstructions;
    private List<IRInstruction> staticInitInstructions;
    private ScopedSymbolTable symbolTable;
    private int labelCounter = 0;
    private int tempCounter = 0;
    private Stack<String> endLoopLabels;
    private String lastTempVar;

    private StringBuilder intListBuilder;
    private StringBuilder staticIntList;

    private List<ArrayList<IRInstruction>> functionIRList;


    public MyIRGenerator(ScopedSymbolTable symbolTable) {
        this.symbolTable = symbolTable;
        this.instructions = new ArrayList<>();
        this.functionIRList = new ArrayList<>();
        this.endLoopLabels = new Stack<>();
    }

    public List<IRInstruction> generate(AstNode root) {
        root.accept(this);
        return instructions;
    }


    @Override
    public Void visit(Program node) {
        String programName = node.getProgramName();
        this.instructions.add(new IRCode("start_program " + programName));
        this.staticIntList = new StringBuilder();
        staticIntList.append("\tstatic-int-list: ");


        //visit variable
        this.staticInitInstructions = new ArrayList<>();
        for (Variable var : node.getGlobals()) {
            var.accept(this);
        }

        //Iterate through scope and function
        List<ScopedSymbolTable> childScope = this.symbolTable.getChildScopes();
        List<Function> function = node.getFunctions();
        if (function.size() != childScope.size()) {
            throw new IllegalArgumentException("Both lists must have the same number of elements.");
        }
        ListIterator<ScopedSymbolTable> scopeIterator = childScope.listIterator();
        ListIterator<Function> functionIterator = function.listIterator();

        while (functionIterator.hasNext() && scopeIterator.hasNext()) {
            this.symbolTable = scopeIterator.next();
            functionIterator.next().accept(this);
        }

        //Add static variable list
        staticIntList.append(System.lineSeparator());
        staticIntList.append("\tstatic-float-list: \n");
        this.instructions.add(new IRCode(this.staticIntList.toString()));

        //Add IRcode from each function
        for (ArrayList<IRInstruction> functionIR : this.functionIRList) {
            this.instructions.addAll(functionIR);
        }
        this.instructions.add(new IRCode("end_program " + programName));
        return null;
    }

    //TODO Function Declaration
    @Override
    public Void visit(Function function) {
        String functionName = function.getId();
        StringBuilder builder = new StringBuilder();
        this.tempInstructions = new ArrayList<>();


        builder.append("start_function " + functionName);
        builder.append(System.lineSeparator());

        if (function.getReturnType() != null) {
            String returnType = function.getReturnType().toString();
            builder.append("\t" + returnType + " " + functionName + "(");

        } else {
            builder.append("\tvoid " + functionName + "(");
        }

        if (functionName.equals("main") && staticInitInstructions != null) {
            this.tempInstructions.addAll(this.staticInitInstructions);
        }

        this.intListBuilder = new StringBuilder();
        this.intListBuilder.append("\tint-list: ");

        //visit variable
        if (function.getArgs() != null) {
            for (Variable var : function.getArgs()) {
                String id = var.getID().get(0);
                VariableSymbol symbol = (VariableSymbol) this.symbolTable.resolve(id);
                id = symbol.getNameMangling();
                builder.append(var.getType().toString() + " " + id + ", ");
                var.accept(this);
            }
            builder.setLength(builder.length() - 2);
            builder.append(")");
        } else {
            builder.append(")");
        }
        builder.append(System.lineSeparator());


        //visit function
        ListIterator<ScopedSymbolTable> scopeIterator = this.symbolTable.getChildScopes().listIterator();
        for (StatNode stat : function.getBody()) {
            if (stat instanceof LetStat) {
                this.symbolTable = scopeIterator.next();
                stat.accept(this);
                this.symbolTable = symbolTable.getParentScope();
            } else {
                stat.accept(this);
            }
        }

        builder.append(intListBuilder);
        builder.append("\n\tfloat-list:\n");
        builder.append("\t" + functionName + ":");
        List<IRInstruction> functionIR = new ArrayList<>();
        functionIR.add(new IRCode(builder.toString()));
        functionIR.addAll(this.tempInstructions);
        if (function.getReturnType() == null) {
            functionIR.add(new IRReturn());
        }
        functionIR.add(new IRCode("end_function " + functionName + "\n"));

        //add temp instruction to function
        this.functionIRList.add((ArrayList<IRInstruction>) functionIR);
        return null;
    }

    //TODO Type Declaration
    @Override
    public Void visit(TypeDeclaration typeDeclaration) {
        return null;
    }

    //Done
    @Override
    public Void visit(Variable variable) {
        String storageClass = variable.getStorageClass();
        List<String> vars = variable.getID();
        StringBuilder sb = new StringBuilder();
        if (storageClass.equals("static")) {
            Constant init = variable.getValue();
            Type type = variable.getType();
            if (init.getNum() != 0) {
                String constant = String.valueOf(init.getNum());
                if (type instanceof BaseType) {
                    for (String var : vars) {
                        sb.append("_0_" + var + ", ");
                        this.staticInitInstructions.add(new IRAssign(("_0_" + var), constant));
                    }
                } else {
                    TypeID typeID = (TypeID) type;
                    TypeSymbol symbol = (TypeSymbol) this.symbolTable.resolve(typeID.getId());
                    while (symbol.getType() instanceof TypeID) {
                        symbol = (TypeSymbol) this.symbolTable.resolve(((TypeID) symbol.getType()).getId());
                    }

                    if (symbol.getType() instanceof BaseType) {
                        for (String var : vars) {
                            sb.append("_0_" + var + ", ");
                            this.staticInitInstructions.add(new IRAssign(("_0_" + var), constant));
                        }
                    } else {
                        ArrayType arrayType = (ArrayType) symbol.getType();
                        for (String var : vars) {
                            sb.append("_0_" + var + "[" + arrayType.getSize() + "], ");
                            this.staticInitInstructions.add(new IRAssign(("_0_" + var), String.valueOf(arrayType.getSize()), constant));
                        }
                    }
                }
            } else {
                if (type instanceof BaseType) {
                    for (String var : vars) {
                        sb.append("_0_" + var + ", ");
                    }
                } else {
                    TypeID typeID = (TypeID) type;
                    TypeSymbol symbol = (TypeSymbol) this.symbolTable.resolve(typeID.getId());
                    while (symbol.getType() instanceof TypeID) {
                        symbol = (TypeSymbol) this.symbolTable.resolve(((TypeID) symbol.getType()).getId());
                    }

                    if (symbol.getType() instanceof BaseType) {
                        for (String var : vars) {
                            sb.append("_0_" + var + ", ");
                        }
                    } else {
                        ArrayType arrayType = (ArrayType) symbol.getType();
                        for (String var : vars) {
                            sb.append("_0_" + var + "[" + arrayType.getSize() + "], ");
                        }
                    }
                }
            }
            this.staticIntList.append(sb);
        } else {
            Type type = variable.getType();
            if (variable.getValue() != null) {
                //Initialize variable IR code
                String constant = String.valueOf(variable.getValue().getNum());
                if (type instanceof BaseType) {

                    for (String var : vars) {
                        VariableSymbol symbol = (VariableSymbol) this.symbolTable.resolve(var);
                        sb.append(symbol.getNameMangling() + ", ");
                        tempInstructions.add(new IRAssign(symbol.getNameMangling(), constant));
                    }
                } else {
                    TypeID typeID = (TypeID) type;
                    TypeSymbol typeSymbol = (TypeSymbol) this.symbolTable.resolve(typeID.getId());
                    while (typeSymbol.getType() instanceof TypeID) {
                        typeSymbol = (TypeSymbol) this.symbolTable.resolve(((TypeID) typeSymbol.getType()).getId());
                    }

                    if (typeSymbol.getType() instanceof BaseType) {
                        for (String var : vars) {
                            VariableSymbol varSymbol = (VariableSymbol) this.symbolTable.resolve(var);
                            sb.append(varSymbol.getNameMangling() + ", ");
                            tempInstructions.add(new IRAssign(varSymbol.getNameMangling(), constant));
                        }
                    } else {
                        ArrayType arrayType = (ArrayType) typeSymbol.getType();
                        for (String var : vars) {
                            VariableSymbol varSymbol = (VariableSymbol) this.symbolTable.resolve(var);
                            sb.append(varSymbol.getNameMangling() + "[" + arrayType.getSize() + "], ");
                            tempInstructions.add(new IRAssign(varSymbol.getNameMangling(), String.valueOf(arrayType.getSize()), constant));
                        }
                    }
                }
            } else {
                if (type instanceof BaseType) {

                    for (String var : vars) {
                        VariableSymbol symbol = (VariableSymbol) this.symbolTable.resolve(var);
                        sb.append(symbol.getNameMangling() + ", ");
                    }
                } else {
                    TypeID typeID = (TypeID) type;
                    TypeSymbol typeSymbol = (TypeSymbol) this.symbolTable.resolve(typeID.getId());
                    while (typeSymbol.getType() instanceof TypeID) {
                        typeSymbol = (TypeSymbol) this.symbolTable.resolve(((TypeID) typeSymbol.getType()).getId());
                    }

                    if (typeSymbol.getType() instanceof BaseType) {
                        for (String var : vars) {
                            VariableSymbol varSymbol = (VariableSymbol) this.symbolTable.resolve(var);
                            sb.append(varSymbol.getNameMangling() + ", ");
                        }
                    } else {
                        ArrayType arrayType = (ArrayType) typeSymbol.getType();
                        for (String var : vars) {
                            VariableSymbol varSymbol = (VariableSymbol) this.symbolTable.resolve(var);
                            sb.append(varSymbol.getNameMangling() + "[" + arrayType.getSize() + "], ");
                        }
                    }
                }
            }
            this.intListBuilder.append(sb);
        }
        return null;
    }

        //Done
        @Override
        public Void visit (ArrayVariableExpr arrayVariableExpr){
            arrayVariableExpr.getIndexExpression().accept(this);
            String index = this.lastTempVar;

            String id = arrayVariableExpr.getID();
            VariableSymbol symbol = (VariableSymbol) this.symbolTable.resolve(id);
            id = symbol.getNameMangling();


            String result = getNextTemp();
            tempInstructions.add(new IRArrayLoad(result, id, index));
            this.lastTempVar = result;

            return null;
        }

        //Done
        @Override
        public Void visit (Comparison comparison){
            comparison.getLeft().accept(this);
            String left = lastTempVar;
            comparison.getRight().accept(this);
            String right = lastTempVar;
            String op = comparison.getOperator();
            String label = getNextLabel();
            String result = getNextTemp();

            tempInstructions.add(new IRComparisonBranch(left, right, result, op, label));
            this.lastTempVar = result;
            return null;
        }

        //Done
        @Override
        public Void visit (Constant constant){
            lastTempVar = String.valueOf(constant.getNum());
            return null;
        }

        //Done
        @Override
        public Void visit (VariableExpr variableExpr){
            String id = variableExpr.getID();
            VariableSymbol symbol = (VariableSymbol) this.symbolTable.resolve(id);

            this.lastTempVar = symbol.getNameMangling();
            return null;
        }

        //Done
        @Override
        public Void visit (AssignStat assignStat){
            ExpressionNode variable = assignStat.getVariable();
            assignStat.getExpressionNode().accept(this);
            String value = this.lastTempVar;
            if (variable instanceof VariableExpr variableExpr) {
                variableExpr.accept(this);
                String id = this.lastTempVar;
                tempInstructions.add(new IRAssign(id, value));
            } else if (variable instanceof ArrayVariableExpr array) {
                VariableSymbol symbol = (VariableSymbol) this.symbolTable.resolve(array.getID());
                String id = symbol.getNameMangling();
                array.getIndexExpression().accept(this);
                String index = this.lastTempVar;
                tempInstructions.add(new IRArrayStore(value, id, index));
            }
            return null;
        }

        //Done
        @Override
        public Void visit (BreakStat breakStat){
            tempInstructions.add(new IRBreak(this.endLoopLabels.pop()));
            return null;
        }

        //Done
        @Override
        public Void visit (ForStat forStat){
            //Get Index
            String index = forStat.getIndex();
            VariableSymbol symbol = (VariableSymbol) this.symbolTable.resolve(index);
            index = symbol.getNameMangling();

            //evaluate lower bound
            forStat.getLowerBound().accept(this);
            //assign lower bound to index
            tempInstructions.add(new IRAssign(index, this.lastTempVar));
            //get start and end label
            String startLabel = getNextLabel();
            String endLabel = getNextLabel();
            //push current end label to stack for break statement
            endLoopLabels.push(endLabel);
            tempInstructions.add(new IRCode("\t" + startLabel + ":"));
            //evaluate upperbound
            forStat.getUpperBound().accept(this);
            //compare lower > upper => go endloop
            tempInstructions.add(new IRCode("\t\tbrgt, " + index + ", " + this.lastTempVar + ", " + endLabel));
            //evaluate for-loop body

            ListIterator<ScopedSymbolTable> childScopes = this.symbolTable.getChildScopes().listIterator();
            for (StatNode stat : forStat.getBody()) {
                if (stat instanceof LetStat) {
                    this.symbolTable = childScopes.next();
                    stat.accept(this);
                    this.symbolTable = this.symbolTable.getParentScope();
                } else {
                    stat.accept(this);
                }
            }
            //add 1 to index
            tempInstructions.add(new IRBinaryOp("+", index, String.valueOf(1), index));
            //go to start label
            tempInstructions.add(new IRBreak(startLabel));
            //end loop
            tempInstructions.add(new IRCode("\t" + endLabel + ":"));
            return null;
        }

        //Done
        @Override
        public Void visit (FunctionCallStat functionCallStat){

            String functionName = functionCallStat.getFunctionName();
            StringBuilder argsSB = new StringBuilder();
            if (functionCallStat.hasArgs()) {
                for (ExpressionNode expr : functionCallStat.getArguments()) {
                    expr.accept(this);
                    argsSB.append(", " + this.lastTempVar);
                }
            } else {
                argsSB.append(",");
            }

            if (functionCallStat.isAssignment()) {
                functionCallStat.getAssignTo().accept(this);
                String assign = this.lastTempVar;
                tempInstructions.add(new IRFunctionCall(functionName, argsSB.toString(), assign));
            } else {
                tempInstructions.add(new IRFunctionCall(functionName, argsSB.toString()));
            }
            return null;
        }

        //Done
        @Override
        public Void visit (IfElseStat ifElseStat){
            String elseLabel = getNextLabel();
            String endLabel = getNextLabel();
            //evaluation expression
            ifElseStat.getCondition().accept(this);
            String temp = lastTempVar;
            tempInstructions.add(new IRCode("\t\tbreq, " + temp + ", 0, " + elseLabel));
            ListIterator<ScopedSymbolTable> childScopes = this.symbolTable.getChildScopes().listIterator();
            for (StatNode stat : ifElseStat.getThenBody()) {
                if (stat instanceof LetStat) {
                    this.symbolTable = childScopes.next();
                    stat.accept(this);
                    this.symbolTable = this.symbolTable.getParentScope();
                } else {
                    stat.accept(this);
                }
            }
            tempInstructions.add(new IRBreak(endLabel));
            tempInstructions.add(new IRCode("\t" + elseLabel + ":"));
            for (StatNode stat : ifElseStat.getElseBody()) {
                if (stat instanceof LetStat) {
                    this.symbolTable = childScopes.next();
                    stat.accept(this);
                    this.symbolTable = this.symbolTable.getParentScope();
                } else {
                    stat.accept(this);
                }
            }
            tempInstructions.add(new IRCode("\t" + endLabel + ":"));
            return null;
        }

        //Done
        @Override
        public Void visit (IfStat ifStat){

            String endlabel = getNextLabel();
            //evaluation expression
            ifStat.getCondition().accept(this);
            String temp = lastTempVar;
            tempInstructions.add(new IRCode("\t\tbreq, " + temp + ", 0, " + endlabel));
            ListIterator<ScopedSymbolTable> childScopes = this.symbolTable.getChildScopes().listIterator();
            for (StatNode stat : ifStat.getThenBody()) {
                if (stat instanceof LetStat) {
                    this.symbolTable = childScopes.next();
                    stat.accept(this);
                    this.symbolTable = this.symbolTable.getParentScope();
                } else {
                    stat.accept(this);
                }
            }
            tempInstructions.add(new IRCode("\t" + endlabel + ":"));
            return null;
        }

        @Override
        public Void visit (LetStat letStat){

            //visit variable declared
            if (letStat.getTypesDeclared() != null) {
                for (Variable var : letStat.getVariablesDeclared()) {
                    var.accept(this);
                }
            }

            //visit statement
            ListIterator<ScopedSymbolTable> childScopes = this.symbolTable.getChildScopes().listIterator();

            for (StatNode stat : letStat.getStatements()) {
                if (stat instanceof LetStat) {
                    this.symbolTable = childScopes.next();
                    stat.accept(this);
                    this.symbolTable = this.symbolTable.getParentScope();
                } else {
                    stat.accept(this);
                }
            }

            return null;
        }

        @Override
        public Void visit (ReturnStat returnStat){
            if (returnStat.HasReturn()) {
                returnStat.getReturnExpr().accept(this);
                tempInstructions.add(new IRReturn(this.lastTempVar));
            } else {
                tempInstructions.add(new IRReturn());
            }
            return null;
        }

        //Done
        @Override
        public Void visit (WhileStat whileStat){

            //get start and end label
            String startLabel = getNextLabel();
            String endLabel = getNextLabel();
            tempInstructions.add(new IRCode("\t" + startLabel + ":"));
            //evaluate condition
            whileStat.getCondition().accept(this);
            //if false -> go to end label
            tempInstructions.add(new IRCode("\t\tbreq, " + this.lastTempVar + ", 0, " + endLabel));
            //execute while body
            this.endLoopLabels.push(endLabel);
            for (StatNode stat : whileStat.getBody()) {
                stat.accept(this);
            }
            //send back to start label
            tempInstructions.add(new IRBreak(startLabel));
            //endlabel
            tempInstructions.add(new IRCode("\t" + endLabel + ":"));
            return null;
        }

        @Override
        public Void visit (ArrayType arrayType){
            return null;
        }

        @Override
        public Void visit (BaseType baseType){
            return null;
        }

        @Override
        public Void visit (TypeID typeID){
            return null;
        }

        //Done
        @Override
        public Void visit (BinaryOpExpression binaryOpExpression){
            binaryOpExpression.getLeftExpression().accept(this);
            String left = this.lastTempVar;
            binaryOpExpression.getRightExpression().accept(this);
            String right = this.lastTempVar;

            String result = this.getNextTemp();
            String operator = binaryOpExpression.getOperator();
            if (operator.equals("**")) {
                String startLabel = getNextLabel();
                String endLabel = getNextLabel();
                if (binaryOpExpression.getRightExpression() instanceof Constant) {
                    String temp = this.getNextTemp();
                    tempInstructions.add(new IRAssign(temp, right));
                    right = temp;
                }
                tempInstructions.add(new IRPowerOp(left, right, result, startLabel, endLabel));

            } else {
                tempInstructions.add(new IRBinaryOp(operator, left, right, result));
            }
            this.lastTempVar = result;
            return null;
        }

        //Done
        @Override
        public Void visit (ParallelExpression parallelExpression){
            parallelExpression.getExpr().accept(this);
            return null;
        }


    /*
        -------------------------------------------------------------------------------------
            HELPER FUNCTIONS
        ------------------------------------------------------------------------------------
    */

        private String getNextTemp () {
            String sb = "_t" + this.tempCounter;
            this.tempCounter++;
            this.intListBuilder.append(sb + ", ");
            return sb;
        }

        private String getNextLabel () {
            String sb = "_L" + this.labelCounter;
            this.labelCounter++;
            return sb;
        }

        private String getCurrentLabel () {
            return "_L" + (this.labelCounter--);
        }

    }

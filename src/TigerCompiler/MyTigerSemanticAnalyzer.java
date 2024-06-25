package TigerCompiler;

import antlr.TigerBaseVisitor;
import antlr.TigerParser;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import tiger.ast.AstNode;
import tiger.ast.program.Program;
import tiger.ast.declaration.*;
import tiger.ast.expr.*;
import tiger.ast.stat.*;
import tiger.ast.type.*;
import tiger.symbol.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MyTigerSemanticAnalyzer extends TigerBaseVisitor<AstNode> {
    private final ScopeManager scopeManager;
    private final List<java.lang.String> semanticError;
    private int loopCounter;
    private String currentFunction;

    public MyTigerSemanticAnalyzer() {
        this.scopeManager = new ScopeManager();
        this.semanticError = new ArrayList<>();
        this.loopCounter = 0;
    }

    @Override
    public AstNode visitTiger_program(TigerParser.Tiger_programContext ctx) {
        String programName = ctx.ID().getText();

        List<TypeDeclaration> typeDeclarations = ctx.declaration_segment().type_declaration_list().type_declaration()
                .stream().map(this::visitType_declaration).map(node -> (TypeDeclaration) node).collect(Collectors.toList());

        List<Variable> globalVariables = ctx.declaration_segment().var_declaration_list().var_declaration()
                .stream().map(this::visitVar_declaration).map(node -> (Variable) node).collect(Collectors.toList());


        List<Function> functions = ctx.funct_list().funct().stream()
                .map(this::visitFunct).map(node -> (Function) node).collect(Collectors.toList());
        return new Program(programName, globalVariables, typeDeclarations, functions);
    }


    //Done
    @Override
    public AstNode visitType_declaration(TigerParser.Type_declarationContext ctx) {
        // type_declaration: TYPE ID TASSIGN type SEMICOLON;
        // type: base_type | ARRAY OPENBRACK INTLIT CLOSEBRACK OF base_type | ID;
        // base_type: INT;
        String id = ctx.ID().getText();
        TypeSymbol symbol = new TypeSymbol(id,(Type) visit(ctx.type()));
        scopeManager.define(symbol);
        return new TypeDeclaration(id, (Type) visit(ctx.type()));
    }

    //Done
    @Override
    public AstNode visitType_base(TigerParser.Type_baseContext ctx) {
        return new BaseType(ctx.base_type().getChild(0).getText());
    }

    //Done
    @Override
    public AstNode visitType_array(TigerParser.Type_arrayContext ctx) {
        int index = Integer.parseInt(ctx.INTLIT().getText());
        BaseType baseType =new BaseType(ctx.base_type().getChild(0).getText());
        return new ArrayType(index,baseType);
    }

    //Done
    @Override
    public AstNode visitType_id(TigerParser.Type_idContext ctx) {
        //type myInt = int;
        //type alsoAnInt = myInt
        //resolve(myInt) -> int
        //Store alsoAnInt as int type
        //Same for array type
        String id = ctx.ID().getText();
        TypeSymbol symbol = (TypeSymbol) scopeManager.resolve(id);
        if (symbol == null){
            Token idToken = ctx.ID().getSymbol();
            AddSemanticError(idToken, "Type "+id+" not declared");
            System.err.println("line "+idToken.getLine()+":"+(idToken.getCharPositionInLine()+1)+" "+id+" not declared");
            throw new RuntimeException("SemanticError");
        }
        Type type = symbol.getType();
        return new TypeID(id,type);
    }

    //Done
    @Override
    public AstNode visitVar_declaration(TigerParser.Var_declarationContext ctx) {
        String storageClass = ctx.storage_class().getChild(0).getText();

        if (scopeManager.getScopeLevel() == 0 && storageClass.equals("var")) {
            Token storageToken = ctx.storage_class().getStart();
            AddSemanticError(storageToken, "Variable must be declared as static in global scope");
        } else if (scopeManager.getScopeLevel() != 0 && storageClass.equals("static")) {
            Token storageToken = ctx.storage_class().getStart();
            AddSemanticError(storageToken, "Variable must be declared as var in let scope");
        }


        List<TerminalNode> nodes = ctx.id_list().ID();
        Type type = (Type) visit(ctx.type());

        if (type instanceof ArrayType) {
            Token typeToken = ctx.type().getStart();
            String error = "Array type has not been created";
            AddSemanticError(typeToken, error);
            System.err.println("line "+typeToken.getLine()+":"+(typeToken.getCharPositionInLine()+1)+" "+error);
            throw new RuntimeException("SemanticError");
        }

        Constant optionalInit = ctx.optional_init() != null ? (Constant) visit(ctx.optional_init().const_()) : null;
        if (scopeManager.getScopeLevel() == 0 && optionalInit == null) {
            //Initialize static variables to 0
            optionalInit = new Constant(0);
        }
        for (TerminalNode node : nodes) {
            String id = node.getText();
            if (scopeManager.existsInCurrentScope(id)) {
                Token idToken = node.getSymbol();
                AddSemanticError(idToken, "Variable " + id + " already declared in current scope");
            } else {
                if (type instanceof TypeID) {
                    scopeManager.define(new VariableSymbol(id, ((TypeID) type).getId(), storageClass));
                } else{
                    scopeManager.define(new VariableSymbol(id,type.toString(),storageClass));
                }
            }
        }

        List<String> ids = ctx.id_list().ID().stream().map(ParseTree::getText).collect(Collectors.toList());
        return new Variable(storageClass, ids, type, optionalInit);
    }

    //Done
    @Override
    public AstNode visitFunct(TigerParser.FunctContext ctx) {
        String id = ctx.ID().getText();
        this.currentFunction = id;
        //Get number of parameters

        List<TigerParser.ParamContext> params = ctx.param_list() != null ? ctx.param_list().param() : null;


        int numParams = params == null ? 0 : params.size();

        //Get Return Type
        Type returnType = ctx.ret_type().type() != null ? (Type) visit(ctx.ret_type().type()) : null;

        //create new function symbol in global scope
        FunctionSymbol symbol = new FunctionSymbol(id,numParams,returnType);
        scopeManager.define(symbol);
        scopeManager.addFunction(symbol);

        //enter function scope
        scopeManager.enterScope(id);
        List<Variable> parametersDeclared = null;
        if (ctx.param_list() != null) {
            parametersDeclared = ctx.param_list().param().stream().map(this::visitParam)
                    .map(node -> (Variable) node).collect(Collectors.toList());
        }
        List<StatNode> body = ctx.stat_seq().statNode().stream().map(this::visit)
                .map(node -> (StatNode) node).collect(Collectors.toList());

        //exit function scope to global
        scopeManager.exitScope();
        return new Function(id,parametersDeclared,returnType,body);
    }

    //Done
    @Override
    public AstNode visitParam(TigerParser.ParamContext ctx) {
        //parameter of function, no need to check if variable declared
        String id = ctx.ID().getText();
        String storageClass = "var";
        Type type = (Type) visit(ctx.type());
        VariableSymbol symbol = new VariableSymbol(id,type.toString(),storageClass);
        scopeManager.define(symbol);
        List<String> ids = new ArrayList<>();
        ids.add(id);
        return new Variable(storageClass,ids,type,null);
    }

    //Done
    @Override
    public AstNode visitAssignStat(TigerParser.AssignStatContext ctx) {
        ExpressionNode value = (ExpressionNode) visit(ctx.value());
        ExpressionNode expr = (ExpressionNode) visit(ctx.expr());
        if (value instanceof VariableExpr valueExpr) {
            //left side is variable
            String leftType = valueExpr.getType();
            if (expr instanceof VariableExpr) {
                //Right side is also variable
                //Check type compatibilities
                String rightType = ((VariableExpr) expr).getType();
                TypeSymbol leftTypeSymbol = (TypeSymbol) scopeManager.resolve(leftType);
                TypeSymbol rightTypeSymbol = (TypeSymbol) scopeManager.resolve(rightType);
                if (!leftTypeSymbol.getType().toString().equals(rightTypeSymbol.getType().toString())) {
                    Token leftToken = ctx.getStart();
                    AddSemanticError(leftToken, leftType + " is not assignable to " + rightType);
                }
            } else {
                //Right side is integers
                if (isNotTypeInt(leftType)){
                    Token leftToken = ctx.getStart();
                    AddSemanticError(leftToken, leftType + " is not assignable to int");
                }
            }
        }
        return new AssignStat(value,expr);
    }

    //Done
    @Override
    public AstNode visitIfStat(TigerParser.IfStatContext ctx) {
        //IF expr THEN stat_seq ENDIF SEMICOLON
        ExpressionNode condition = (ExpressionNode) visit(ctx.expr());
        if (condition instanceof VariableExpr variableExpr){
            String type = variableExpr.getType();
            if (isNotTypeInt(type)){
                Token exprToken = ctx.getStart();
                AddSemanticError(exprToken, "condition must return an integer");
            }
        }

        List<StatNode> body = ctx.stat_seq().statNode().stream().map(this::visit)
                .map(node -> (StatNode) node).collect(Collectors.toList());

        return new IfStat(condition,body);
    }

    //Done
    @Override
    public AstNode visitIfElseStat(TigerParser.IfElseStatContext ctx) {
        ExpressionNode condition = (ExpressionNode) visit(ctx.expr());
        if (condition instanceof VariableExpr variableExpr){
            String type = variableExpr.getType();
            if (isNotTypeInt(type)){
                Token exprToken = ctx.getStart();
                AddSemanticError(exprToken, "condition must return an integer");
            }
        }

        List<StatNode> thenBody = ctx.lblock.statNode().stream().map(this::visit)
                .map(node -> (StatNode) node).collect(Collectors.toList());
        List<StatNode> elseBody = ctx.rblock.statNode().stream().map(this::visit)
                .map(node -> (StatNode) node).collect(Collectors.toList());

        return new IfElseStat(condition,thenBody,elseBody);
    }

    //Done
    @Override
    public AstNode visitWhileStat(TigerParser.WhileStatContext ctx) {
        loopCounter++;
        ParallelExpression expression = (ParallelExpression) visit(ctx.expr());
        ExpressionNode condition = expression.getExpr();
        if (condition instanceof VariableExpr variableExpr){
            String type = variableExpr.getType();
            if (isNotTypeInt(type)){
                Token exprToken = ctx.getStart();
                AddSemanticError(exprToken, "condition must return an integer");
            }
        }
        List<StatNode> whileBody = ctx.stat_seq().statNode().stream().map(this::visit)
                .map(node -> (StatNode) node).collect(Collectors.toList());

        loopCounter--;
        return new WhileStat(condition,whileBody);
    }

    //Done
    @Override
    public AstNode visitForStat(TigerParser.ForStatContext ctx) {

        loopCounter++;
        String id = ctx.ID().getText();
        if (scopeManager.resolve(id) == null){
            Token idToken = ctx.getStart();
            AddSemanticError(idToken, "loop index "+id+" has not been declared");
        }

        ExpressionNode start = (ExpressionNode) visit(ctx.start);
        ExpressionNode end = (ExpressionNode) visit(ctx.end);
        if (start instanceof VariableExpr variableExpr){
            String type = variableExpr.getType();
            if (isNotTypeInt(type)){
                Token exprToken = ctx.getStart();
                AddSemanticError(exprToken, "lower bound must return an integer");
            }
        } else if (end instanceof VariableExpr variableExpr){
            String type = variableExpr.getType();
            if (isNotTypeInt(type)){
                Token exprToken = ctx.getStart();
                AddSemanticError(exprToken, "upper bound must return an integer");
            }
        }

        List<StatNode> body = ctx.stat_seq().statNode().stream().map(this::visit)
                .map(node -> (StatNode) node).collect(Collectors.toList());

        loopCounter--;
        return new ForStat(id, start, end, body);
    }

    //Done
    @Override
    public AstNode visitFunctCallStat(TigerParser.FunctCallStatContext ctx) {
        //check symbol table for function ID
        //check if there is optprefix, yes -> check if function has return type
        String id = ctx.ID().getText();
        Token idToken = ctx.ID().getSymbol();
        FunctionSymbol symbol = scopeManager.getFunction(id);
        List<ExpressionNode> args = ctx.expr_list()!= null? ctx.expr_list().expr().stream().map(this::visit)
                .map(node -> (ExpressionNode) node).collect(Collectors.toList()) : null;

        int size = args != null ? args.size() : 0;

        if (symbol.getNumberOfParameters() != size){
            AddSemanticError(idToken, "function "+id+" has "+symbol.getNumberOfParameters()+" arguments");
        }

        ExpressionNode value = ctx.optprefix() != null ? (ExpressionNode) visit(ctx.optprefix().value()) : null;
        if (value != null && symbol.getReturnType() == null){
            AddSemanticError(idToken, "function "+id+" has no return type");
        }

        return new FunctionCallStat(id,value,args);
    }

    //Done
    @Override
    public AstNode visitBreakStat(TigerParser.BreakStatContext ctx) {
        if(loopCounter == 0){
            Token breakToken = ctx.getStart();
            AddSemanticError(breakToken, "Usage of break statement outside of loop");
        }
        return new BreakStat();
    }

    //Done
    @Override
    public AstNode visitReturnStat(TigerParser.ReturnStatContext ctx) {

        //check if expression is array or not
        ExpressionNode returnExpr = ctx.optreturn() != null ? (ExpressionNode) visit(ctx.optreturn().expr()) : null;
        if (returnExpr instanceof VariableExpr variableExpr){
            String type = variableExpr.getType();
            if (isNotTypeInt(type)){
                Token exprToken = ctx.getStart();
                AddSemanticError(exprToken, "return type must be an integer");
            }
        }

        //Get current function scope name and resolve to see if function or procedure
        //Semantic error if procedure has return and vice versa


        String functionID = this.currentFunction;
        FunctionSymbol symbol = scopeManager.getFunction(functionID);
        if (symbol == null){
            System.err.println("Function "+functionID+" has not been declared, Impossible");
        }

        assert symbol != null;
        if (symbol.getReturnType() == null && returnExpr != null){
            Token idToken = ctx.optreturn().getStart();
            AddSemanticError(idToken, "Cannot return a value for procedures "+ functionID );
        } else if (symbol.getReturnType() != null && returnExpr == null){
            Token idToken = ctx.getStart();
            AddSemanticError(idToken, "Missing returned value for function "+functionID);
        }
        return new ReturnStat(returnExpr);
    }

    //Done
    @Override
    public AstNode visitLetStat(TigerParser.LetStatContext ctx) {
        //Implement entering scope for let-block
        String currentScopeName = scopeManager.getCurrentScopeName();
        int currentScopeLevel = scopeManager.getScopeLevel();
        String newScopeName = currentScopeName + "_" + currentScopeLevel;
        scopeManager.enterScope(newScopeName);

        List<TypeDeclaration> typeDeclared = null;
        if (ctx.declaration_segment().type_declaration_list() != null){
            typeDeclared = ctx.declaration_segment().type_declaration_list().type_declaration()
                    .stream().map(this::visitType_declaration).map(node-> (TypeDeclaration) node).collect(Collectors.toList());
        }

        List<Variable> variables = null;
        if (ctx.declaration_segment().var_declaration_list() != null){
            variables = ctx.declaration_segment().var_declaration_list().var_declaration()
                    .stream().map(this::visitVar_declaration).map(node-> (Variable) node).collect(Collectors.toList());
        }


        List<StatNode> stats = ctx.stat_seq().statNode().stream().map(this::visit).map(node -> (StatNode) node).collect(Collectors.toList());

        scopeManager.exitScope();
        return new LetStat(variables,typeDeclared,stats);
    }

    //Done
    @Override
    public AstNode visitAndExpr(TigerParser.AndExprContext ctx) {
        ExpressionNode left =  (ExpressionNode) visit(ctx.getChild(0));
        ExpressionNode right = (ExpressionNode) visit(ctx.getChild(2));
        if (left instanceof VariableExpr variableExpr) {
            String type = variableExpr.getType();
            if (isNotTypeInt(type)){
                Token exprToken = ctx.getStart();
                AddSemanticError(exprToken, "operands must be integers");
            }
        } else if (right instanceof VariableExpr variableExpr) {
            String type = variableExpr.getType();
            if (isNotTypeInt(type)){
                Token exprToken = ctx.getStart();
                AddSemanticError(exprToken, "operands must be integers");
            }
        }
        String op = ctx.getChild(1).getText();
        return new BinaryOpExpression(left,right,op);
    }

    //Done
    @Override
    public AstNode visitValueExpr(TigerParser.ValueExprContext ctx) {
        return visit(ctx.value());
    }

    //Done
    @Override
    public AstNode visitPowerExpr(TigerParser.PowerExprContext ctx) {
        ExpressionNode left =  (ExpressionNode) visit(ctx.getChild(0));
        ExpressionNode right = (ExpressionNode) visit(ctx.getChild(2));
        if (left instanceof VariableExpr variableExpr) {
            String type = variableExpr.getType();
            if (isNotTypeInt(type)){
                Token exprToken = ctx.getStart();
                AddSemanticError(exprToken, "operands must be integers");
            }
        } else if (right instanceof VariableExpr variableExpr) {
            String type = variableExpr.getType();
            if (isNotTypeInt(type)){
                Token exprToken = ctx.getStart();
                AddSemanticError(exprToken, "operands must be integers");
            }
        }
        String op = ctx.getChild(1).getText();
        return new BinaryOpExpression(left,right,op);
    }

    //Done
    @Override
    public AstNode visitConstExpr(TigerParser.ConstExprContext ctx) {
        return visit(ctx.const_());
    }

    //Done
    @Override
    public AstNode visitMultExpr(TigerParser.MultExprContext ctx) {
        ExpressionNode left =  (ExpressionNode) visit(ctx.getChild(0));
        ExpressionNode right = (ExpressionNode) visit(ctx.getChild(2));
        if (left instanceof VariableExpr variableExpr) {
            String type = variableExpr.getType();
            if (isNotTypeInt(type)){
                Token exprToken = ctx.getStart();
                AddSemanticError(exprToken, "operands must be integers");
            }
        } else if (right instanceof VariableExpr variableExpr) {
            String type = variableExpr.getType();
            if (isNotTypeInt(type)){
                Token exprToken = ctx.getStart();
                AddSemanticError(exprToken, "operands must be integers");
            }
        }
        String op = ctx.getChild(1).getText();
        return new BinaryOpExpression(left,right,op);
    }

    //Done
    @Override
    public AstNode visitAddExpr(TigerParser.AddExprContext ctx) {
        ExpressionNode left =  (ExpressionNode) visit(ctx.getChild(0));
        ExpressionNode right = (ExpressionNode) visit(ctx.getChild(2));
        if (left instanceof VariableExpr variableExpr) {
            String type = variableExpr.getType();
            if (isNotTypeInt(type)){
                Token exprToken = ctx.getStart();
                AddSemanticError(exprToken, "operands must be integers");
            }
        } else if (right instanceof VariableExpr variableExpr) {
            String type = variableExpr.getType();
            if (isNotTypeInt(type)){
                Token exprToken = ctx.getStart();
                AddSemanticError(exprToken, "operands must be integers");
            }
        }
        String op = ctx.getChild(1).getText();
        return new BinaryOpExpression(left,right,op);
    }

    //Done
    @Override
    public AstNode visitCompExpr(TigerParser.CompExprContext ctx) {
        ExpressionNode left =  (ExpressionNode) visit(ctx.getChild(0));
        ExpressionNode right = (ExpressionNode) visit(ctx.getChild(2));
        // Check if operands are integer
        if (left instanceof VariableExpr variableExpr) {
            String type = variableExpr.getType();
            if (isNotTypeInt(type)){
                Token exprToken = ctx.getStart();
                AddSemanticError(exprToken, "operands must be integers");
            }
        } else if (right instanceof VariableExpr variableExpr) {
            String type = variableExpr.getType();
            if (isNotTypeInt(type)){
                Token exprToken = ctx.getStart();
                AddSemanticError(exprToken, "operands must be integers");
            }
        }
        // if either left or right is also a comparison, semantically incorrect
        if (left instanceof Comparison || right instanceof Comparison) {
            Token exprToken = ctx.getStart();
            AddSemanticError(exprToken, "Comparison operations are not associative");
        }

        String op = ctx.getChild(1).getText();
        return new Comparison(left, right, op);
    }

    //Done
    @Override
    public AstNode visitParenExpr(TigerParser.ParenExprContext ctx) {
        return new ParallelExpression( (ExpressionNode) visit(ctx.expr()));
    }

    //Done
    @Override
    public AstNode visitOrExpr(TigerParser.OrExprContext ctx) {
        ExpressionNode left =  (ExpressionNode) visit(ctx.getChild(0));
        ExpressionNode right = (ExpressionNode) visit(ctx.getChild(2));
        if (left instanceof VariableExpr variableExpr) {
            String type = variableExpr.getType();
            if (isNotTypeInt(type)){
                Token exprToken = ctx.getStart();
                AddSemanticError(exprToken, "operands must be integers");
            }
        } else if (right instanceof VariableExpr variableExpr) {
            String type = variableExpr.getType();
            if (isNotTypeInt(type)){
                Token exprToken = ctx.getStart();
                AddSemanticError(exprToken, "operands must be integers");
            }
        }
        String op = ctx.getChild(1).getText();
        return new BinaryOpExpression(left,right,op);
    }

    //Done
    @Override
    public AstNode visitConst(TigerParser.ConstContext ctx) {
        int value = Integer.parseInt(ctx.INTLIT().getText());
        return new Constant(value);
    }

    //Done
    @Override
    public AstNode visitVariableAccess(TigerParser.VariableAccessContext ctx) {
        String id = ctx.ID().getText();
        // Check if variable is in symbol table
        if (scopeManager.resolve(id) == null){
            Token idToken = ctx.ID().getSymbol();
            System.err.println("line "+idToken.getLine()+":"+(idToken.getCharPositionInLine()+1)+" Variable "+id+" not declared");
            throw new RuntimeException("SemanticError");
        }
        VariableSymbol var = (VariableSymbol) scopeManager.resolve(id);
        return new VariableExpr(id, var.getType());
    }

    //Done
    @Override
    public AstNode visitArrayAccess(TigerParser.ArrayAccessContext ctx) {
        String id = ctx.ID().getText();
        ExpressionNode index = (ExpressionNode) visit(ctx.expr());

        // Check if variable is declared in current scope or any parent scope
        if (scopeManager.resolve(id) == null){
            Token idToken = ctx.ID().getSymbol();
            System.err.println("line "+idToken.getLine()+":"+(idToken.getCharPositionInLine()+1)+" Variable "+id+" not declared");
            throw new RuntimeException("SemanticError");
        }
        return new ArrayVariableExpr(id,index);
    }

    @Override
    public AstNode visitParam_list(TigerParser.Param_listContext ctx) {
        return super.visitParam_list(ctx);
    }

    /*
        -------------------------------------------------------------------------------------
            HELPER FUNCTIONS
        ------------------------------------------------------------------------------------
        */
    private void AddSemanticError(Token token, String description){
        int line = token.getLine();
        int column = token.getCharPositionInLine() + 1;
        String error = "line "+line+":"+column+" "+description;
        semanticError.add(error);
    }

    public List<String> getSemanticError() {
        return semanticError;
    }

    //Get Symbol Table
    public ScopedSymbolTable getSymbolTable() {
        return scopeManager.getTable();
    }

    // Helper function to determined if a type is "int" by looking up on symbol table
    private boolean isNotTypeInt(String typeID){
        TypeSymbol symbol = (TypeSymbol) scopeManager.resolve(typeID);
        Type type = symbol.getType();
        while (type instanceof TypeID){
            symbol = (TypeSymbol) scopeManager.resolve(((TypeID) type).getId());
            type = symbol.getType();
        }
        return !type.toString().equals("int");
    }
}

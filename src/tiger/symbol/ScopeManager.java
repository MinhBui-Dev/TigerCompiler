package tiger.symbol;

import java.util.HashMap;
import java.util.Map;

public class ScopeManager {
    private final ScopedSymbolTable globalScope;
    private ScopedSymbolTable currentScope;
    private int scopeLevel, scopeCounter;
    private Map<String, FunctionSymbol> functionsList;

    public ScopeManager(){
        this.scopeLevel = 0;
        this.scopeCounter = 0;
        this.globalScope = new ScopedSymbolTable("global",scopeLevel, scopeCounter, null);
        this.currentScope = this.globalScope;
        this.functionsList = new HashMap<>();
        init();
    }

    //Create new scope with parent scope being the current scope. Set current scope to new scope
    public void enterScope(String name){
        this.scopeLevel++;
        this.scopeCounter++;
        this.currentScope = new ScopedSymbolTable(name, this.scopeLevel, this.scopeCounter, this.currentScope);
    }
    //Set current scope to parent scope and add child scope to current scope
    public void exitScope(){
        ScopedSymbolTable childScope = this.currentScope;
        this.currentScope = this.currentScope.getParentScope();
        this.currentScope.addChildScope(childScope);
        this.scopeLevel--;
    }

    public void define(Symbol symbol){
        this.currentScope.insertSymbol(symbol);
    }

    public Symbol resolve(String name){
        return this.currentScope.resolve(name);
    }

    public boolean existsInCurrentScope(String name){
        return this.currentScope.existsInCurrentScope(name);
    }

    public int getScopeLevel(){
        return scopeLevel;
    }

    public ScopedSymbolTable getTable(){
        return this.globalScope;
    }

    public String getCurrentScopeName(){
        return this.currentScope.getScopeName();
    }

    public void addFunction(FunctionSymbol function){
        this.functionsList.put(function.getName(), function);
    }

    public FunctionSymbol getFunction(String id){
        return this.functionsList.get(id);
    }

    private void init(){
        FunctionSymbol printiFunction = (FunctionSymbol) this.currentScope.resolve("printi");
        FunctionSymbol notFunction = (FunctionSymbol) this.currentScope.resolve("not");
        FunctionSymbol exitFunction = (FunctionSymbol) this.currentScope.resolve("exit");

        this.functionsList.put(printiFunction.getName(), printiFunction);
        this.functionsList.put(notFunction.getName(), notFunction);
        this.functionsList.put(exitFunction.getName(), exitFunction);
    }
}

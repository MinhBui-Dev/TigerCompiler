package tiger.symbol;

import tiger.ast.type.BaseType;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class ScopedSymbolTable {
    private String scopeName;
    private ScopedSymbolTable parentScope;
    private LinkedList<ScopedSymbolTable> childScopes;
    private int scopeLevel;
    private int scopeCounter;
    private Map<String, Symbol> symbols;

    public ScopedSymbolTable(String scopeName, int scopeLevel, int scopeCounter, ScopedSymbolTable enclosingScope) {
        this.scopeName = scopeName;
        this.scopeLevel = scopeLevel;
        this.scopeCounter = scopeCounter;
        this.parentScope = enclosingScope;
        this.childScopes = new LinkedList<ScopedSymbolTable>();
        this.symbols = new LinkedHashMap<String, Symbol>();
        this.initBuiltins();
    }

    public ScopedSymbolTable getParentScope() {
        return this.parentScope;
    }

    public LinkedList<ScopedSymbolTable> getChildScopes() {
        return this.childScopes;
    }

    public void addChildScope(ScopedSymbolTable childScope) {
        this.childScopes.add(childScope);
    }

    public int getScopeLevel() {
        return this.scopeLevel;
    }

    public String getScopeName() {
        return this.scopeName;
    }

    public void insertSymbol(Symbol symbol) {
        if (symbol instanceof VariableSymbol variableSymbol){
            StringBuilder sb = new StringBuilder();
            sb.append("_").append(scopeCounter).append("_").append(variableSymbol.getName());
            variableSymbol.setNameMangling(sb.toString());
        }
        this.symbols.put(symbol.getName(), symbol);
    }

    public Symbol resolve(String name) {
        Symbol symbol = symbols.get(name);
        if (symbol != null) {
            return symbol;
        } else if (parentScope != null) {
            return parentScope.resolve(name);
        } else {
            return null;
        }
    }

    public boolean existsInCurrentScope(String name) {
        return symbols.containsKey(name);
    }

    private void initBuiltins() {
        // Add built-in types\

        TypeSymbol integer = new TypeSymbol("int", new BaseType("int"));
        this.insertSymbol(integer);

        // Add built-in functions
        // printi(i: int)
        FunctionSymbol printi = new FunctionSymbol("printi", 1, null);

        // not(i: int): int
        FunctionSymbol not = new FunctionSymbol("not", 1, new BaseType("int"));

        // exit(i: int)
        FunctionSymbol exit = new FunctionSymbol("exit", 1, null);

        this.insertSymbol(printi);
        this.insertSymbol(not);
        this.insertSymbol(exit);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(("\t").repeat(scopeLevel)).append(scopeName).append(":").append("\n");
        for (String key : symbols.keySet()) {
            if (key.equals("printi")){
                continue;
            } else if (key.equals("not")){
                continue;
            } else if (key.equals("exit")){
                continue;
            } else if (key.equals("int")){
                continue;
            }
            sb.append(("\t").repeat(scopeLevel+1));
            Symbol symbol = symbols.get(key);
            if (symbol instanceof TypeSymbol typeSymbol){
                sb.append(key);
                sb.append(", ");
                sb.append(typeSymbol.getType());
            } else if (symbol instanceof VariableSymbol variableSymbol){
                sb.append(key);
                sb.append(", ");
                sb.append(variableSymbol.getStorageClass());
                sb.append(", ");
                sb.append(variableSymbol.getType());
            } else if (symbol instanceof FunctionSymbol functionSymbol){
                sb.append(key);
                sb.append(", func, ");
                sb.append(functionSymbol.getReturnType());
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}

package tiger.symbol;

import tiger.ast.type.Type;

import java.util.List;

public class FunctionSymbol extends Symbol {
    private Type returnType;
    private int numberOfParameters;

    public FunctionSymbol(String name, int numberOfParameters, Type returnType) {
        super(name);
        this.returnType = returnType;
        this.numberOfParameters = numberOfParameters;
    }

    public Type getReturnType() {
        return this.returnType;
    }

    public boolean hasReturnType() {
        return this.returnType != null;
    }

    public int getNumberOfParameters() {
        return this.numberOfParameters;
    }

    public String toString() {
        return this.getName()+", Funct, "+this.getReturnType();
    }

}


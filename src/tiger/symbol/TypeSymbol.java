package tiger.symbol;

import tiger.ast.type.Type;

public class TypeSymbol extends Symbol {
    private final Type type;

    public TypeSymbol(String name, Type type) {
        super(name);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public String toString() {
        return this.getName()+" "+type.toString();
    }
}

package tiger.symbol;

public abstract class Symbol {
    private String name;

    public Symbol(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    public String getSymbolClass() {
        return this.getClass().getSimpleName();
    }

    public abstract String toString();
}


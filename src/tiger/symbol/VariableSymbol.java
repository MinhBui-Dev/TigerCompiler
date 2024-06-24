package tiger.symbol;


public class VariableSymbol extends Symbol {
    private String type;
    private String storageClass;
    private String nameMangling;
    public VariableSymbol(String name, String type, String storageClass) {
        super(name);
        this.type = type;
        this.storageClass = storageClass;
    }

    public void setNameMangling (String nameMangling) {
        this.nameMangling = nameMangling;
    }

    public String getNameMangling() {
        return nameMangling;
    }

    public String getType() {
        return type;
    }

    public String getStorageClass() {
        return storageClass;
    }

    public String toString(){
        return this.getName()+", "+type+", "+storageClass;
    }
}

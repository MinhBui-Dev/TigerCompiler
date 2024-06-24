package tiger;

public class TokenTuple {
    private String key;
    private String text;

    public TokenTuple(String key, String text) {
        this.key = key;
        this.text = text;

    }

    public String getKey() {
        return key;
    }

    public String getText() {
        return text;
    }
}

package models.general.people;

public class studentLookup {
    public String key;
    public student value;

    public studentLookup() {
    }

    public studentLookup(String key, student val) {
        this.key = key;
        this.value = val;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String getKey() {
        return key;
    }

    public void setValue(student value) {
        this.value = value;
    }
    public student getValue() {
        return value;
    }
}

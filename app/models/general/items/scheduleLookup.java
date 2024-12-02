package models.general.items;

public class scheduleLookup {
    public String key;
    public schedule value;

    public scheduleLookup() {
    }

    public scheduleLookup(String key, schedule val) {
        this.key = key;
        this.value = val;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String getKey() {
        return key;
    }

    public void setValue(schedule value) {
        this.value = value;
    }
    public schedule getValue() {
        return value;
    }
}

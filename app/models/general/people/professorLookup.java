package models.general.people;

public class professorLookup {
    public String key;
    public professor value;

    public professorLookup() {
    }

    public professorLookup(String key, professor val) {
        this.key = key;
        this.value = val;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String getKey() {
        return key;
    }

    public void setValue(professor value) {
        this.value = value;
    }
    public professor getValue() {
        return value;
    }
}

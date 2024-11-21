package models.general.items;

public class courseLookup {
    public String key;
    public Course value;

    public courseLookup() {
    }

    public courseLookup(String key, Course val) {
        this.key = key;
        this.value = val;
    }
}

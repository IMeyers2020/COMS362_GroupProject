package models.general.items;

public class selectedCoursesLookup {
    public String key;
    public selectedCourse value;

    public selectedCoursesLookup(){}

    public selectedCoursesLookup(String key, selectedCourse val) {
        this.key = key;
        this.value = val;
    }
}

package models.general.items;

import java.util.HashSet;
import java.util.Set;

public class Course {
    private String cid;
    private int creditHours;
    private Set<String> prereqs;

    public Course() {
    }

    public Course(String cid, int creditHours, Set<String> prereqs){
        this.cid = cid;
        this.creditHours = creditHours;
        this.prereqs = new HashSet<>();
        this.prereqs.addAll(prereqs);
    }

    public Course(String cid, int creditHours){
        this.cid = cid;
        this.creditHours = creditHours;
        this.prereqs = new HashSet<>();
    }

    public Set<String> getPrereqs() {
        return prereqs;
    }
    public int getCreditHours() {
        return creditHours;
    }
    public String getCID() {
        return cid;
    }
}

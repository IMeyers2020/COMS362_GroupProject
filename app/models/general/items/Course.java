package models.general.items;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import src.constants.DAYS;
import src.constants.TIMES;

public class Course {
    private String cid;
    private int creditHours;
    private Set<String> prereqs;
    private ArrayList<DAYS> daysOfClass;
    private TIMES timeOfClass;

    public Course() {
    }

    public Course(String cid, int creditHours, Set<String> prereqs, TIMES timeOfClass, ArrayList<DAYS> daysOfClass){
        this.cid = cid;
        this.creditHours = creditHours;
        this.prereqs = new HashSet<>();
        this.prereqs.addAll(prereqs);
        this.timeOfClass = timeOfClass;
        this.daysOfClass = daysOfClass;
    }

    public Course(String cid, int creditHours, TIMES timeOfClass, ArrayList<DAYS> daysOfClass){
        this.cid = cid;
        this.creditHours = creditHours;
        this.prereqs = new HashSet<>();
        this.timeOfClass = timeOfClass;
        this.daysOfClass = daysOfClass;
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

    public TIMES getTimeOfClass() {
        return timeOfClass;
    }
    public void setTimeOfClass(TIMES time) {
        this.timeOfClass = time;
    }

    public ArrayList<DAYS> getDaysOfClass() {
        return daysOfClass;
    }
    public void setDaysOfClass(ArrayList<DAYS> days) {
        this.daysOfClass = days;
    }
}

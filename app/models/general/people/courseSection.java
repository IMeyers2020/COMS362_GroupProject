package models.general.people;

import java.util.ArrayList;

import src.constants.DAYS;
import src.constants.TIMES;

public class courseSection {
    public String sectionId;
    public ArrayList<String> courseDays;
    public String courseTime;

    public courseSection(){};

    public courseSection(String sectionId, ArrayList<DAYS> days, TIMES times) {
        this.courseDays = stringsFromDays(days);
        this.courseTime = times.label;
        this.sectionId = sectionId;
    }
    public courseSection(String sectionId, ArrayList<String> days, String times) {
        this.courseDays = days;
        this.courseTime = times;
        this.sectionId = sectionId;
    }

    private ArrayList<String> stringsFromDays(ArrayList<DAYS> days) {
        ArrayList<String> stringified = new ArrayList<>();
        for (DAYS d : days) {
            stringified.add(d.label);
        }
        return stringified;
    }

    public void setDays(ArrayList<DAYS> days) {
        this.courseDays = stringsFromDays(days);
    }
    public ArrayList<String> getDays() {
        return this.courseDays;
    }

    public void setTime(TIMES time) {
        this.courseTime = time.label;
    }
    public void setTime(String time) {
        this.courseTime = time;
    }
    public String getTime() {
        return this.courseTime;
    }

    public void setSectionId(String sec) {
        this.sectionId = sec;
    }
    public String getSectionId() {
        return this.sectionId;
    }
}

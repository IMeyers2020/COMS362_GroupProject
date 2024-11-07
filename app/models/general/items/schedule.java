package models.general.items;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private final int maxCreditHours = 18;
    private int creditHours;
    private List<String> courses;

    public Schedule() {
        creditHours = 0;
        courses = new ArrayList<>();
    }

    public List<String> getCourses() {
        return courses;
    }

    public boolean addCourse(Course c) {
        if(courses.contains(c.getCID()))
            return false;
        if(c.getCreditHours() + creditHours > maxCreditHours)
            return false;
        courses.add(c.getCID());
        creditHours += c.getCreditHours();
        return true;
    }

    public boolean removeCourse(Course c) {
        if(courses.contains(c.getCID())) {
            courses.remove(c.getCID());
            creditHours -= c.getCreditHours();
            return true;
        }
        return false;
    }
}

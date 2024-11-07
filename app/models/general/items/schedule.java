package models.general.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Schedule {
    private final int maxCreditHours = 18;
    private int creditHours;
    private HashMap<String, Course> courses;

    public Schedule() {
        creditHours = 0;
        courses = new HashMap<>();
    }

    public HashMap<String, Course> getCourses() {
        return courses;
    }

    public boolean addCourse(Course c) {
        if(courses.containsKey(c.getCID()))
            return false;
        if(c.getCreditHours() + creditHours > maxCreditHours)
            return false;
        courses.put(c.getCID(), c);
        creditHours += c.getCreditHours();
        return true;
    }

    public boolean removeCourse(Course c) {
        if(courses.containsKey(c.getCID())) {
            courses.remove(c.getCID());
            creditHours -= c.getCreditHours();
            return true;
        }
        return false;
    }
}

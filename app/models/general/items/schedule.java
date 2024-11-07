package app.models.general.items;

import java.util.ArrayList;
import java.util.List;

public class schedule {
    private final int maxCreditHours = 18;
    private int creditHours;
    private List<String> courses;

    public schedule() {
        creditHours = 0;
        courses = new ArrayList<>();
    }

    public List<String> getCourses() {
        return courses;
    }

    public boolean addCourse(course c) {
        if(courses.contains(c.getName()))
            return false;
        if(c.getCreditHours() + creditHours > maxCreditHours)
            return false;
        courses.add(c.getName());
        creditHours += c.getCreditHours();
        return true;
    }

    public boolean removeCourse(course c) {
        if(courses.contains(c.getName())) {
            courses.remove(c.getName());
            creditHours -= c.getCreditHours();
            return true;
        }
        return false;
    }
}

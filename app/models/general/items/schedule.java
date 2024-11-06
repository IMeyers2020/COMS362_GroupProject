package app.models.general.items;

import java.util.List;

public class schedule {
    private final int maxCreditHours = 18;
    private int creditHours;

    private List<course> courses;

    public List<course> getCourses() {
        return courses;
    }

    public boolean addCourse(course c) {
        if(courses.contains(c))
            return false;
        if(c.getCreditHours() + creditHours > maxCreditHours)
            return false;
        courses.add(c);
        creditHours += c.getCreditHours();
        return true;
    }
}

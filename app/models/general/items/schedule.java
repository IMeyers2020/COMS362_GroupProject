package app.models.general.items;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private final int maxCreditHours = 18;
    private int creditHours;
    private List<String> courseIDs;

    public Schedule() {
        creditHours = 0;
        courseIDs = new ArrayList<>();
    }

    public List<String> getCourses() {
        return courseIDs;
    }

    public boolean addCourse(Course c) {
        if(courseIDs.contains(c.getCID()))
            return false;
        if(c.getCreditHours() + creditHours > maxCreditHours)
            return false;
        courseIDs.add(c.getCID());
        creditHours += c.getCreditHours();
        return true;
    }

    public boolean removeCourse(Course c) {
        if(courseIDs.contains(c.getCID())) {
            courseIDs.remove(c.getCID());
            creditHours -= c.getCreditHours();
            return true;
        }
        return false;
    }
}

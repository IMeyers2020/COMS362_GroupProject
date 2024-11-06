package app.models.general.people;

import java.util.List;

import app.models.general.items.course;
import app.models.general.items.schedule;

public class student<T> extends genericPerson {
    private schedule sched;
    private List<course> completedCourses;


    public List<course> getCurrentCourses() {
        return sched.getCourses();
    }

    public boolean addCourse(course course) {
        if (completedCourses.containsAll(course.getPrereqs())){
            return sched.addCourse(course);
        }
        return false;
    }
}

package app.models.general.people;

import java.util.ArrayList;
import java.util.List;

import app.models.general.items.course;
import app.models.general.items.schedule;

public class student extends genericPerson {
    private schedule sched;
    private List<String> completedCourses;

    public student() {
        sched = new schedule();
        completedCourses = new ArrayList<>();
    }

    public List<String> getCurrentCourses() {
        return sched.getCourses();
    }

    public boolean addCourse(course c) {
        if (completedCourses.containsAll(c.getPrereqs())){
            return sched.addCourse(c);
        }
        return false;
    }

    public boolean removeCourse(course c) {
        return sched.removeCourse(c);
    }

    public void setSchedule(schedule s) {
        sched = s;
    }
    public schedule getSchedule() {
        return sched;
    }
}

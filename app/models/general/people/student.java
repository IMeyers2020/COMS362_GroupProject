package app.models.general.people;

import java.util.ArrayList;
import java.util.List;

import app.models.general.items.course;
import app.models.general.items.schedule;

public class student extends genericPerson {
    private schedule sched;
    private List<course> completedCourses;

    public student() {
        sched = new schedule();
        completedCourses = new ArrayList<>();
    }

    public List<course> getCurrentCourses() {
        return sched.getCourses();
    }

    public boolean addCourse(course course) {
        if (completedCourses.containsAll(course.getPrereqs())){
            return sched.addCourse(course);
        }
        return false;
    }

    public void setSchedule(schedule schedule) {
        sched = schedule;
    }
    public schedule getSchedule() {
        return sched;
    }
}

package models.academics;
import java.util.ArrayList;

import models.general.items.Course;
import models.general.items.Major;
import models.general.items.scheduleLookup;
import models.general.people.studentLookup;
import src.DatabaseSupport;

public class RegistrationController {

    DatabaseSupport db;

    public RegistrationController(DatabaseSupport _db) {
        this.db = _db;
    }

    public boolean addCourse(String sid, Course c) {
        studentLookup stud = this.db.getStudent(sid);
        if(stud == null || stud.value.getScheduleId() == null) {
            System.err.println("Failed to add course, unable to get Schedule from given StudentId");
            return false;
        }

        scheduleLookup sched = this.db.getSchedule(stud.value.getScheduleId());
        if(sched == null || sched.value.getScheduleId() == null) {
            System.err.println("Failed to add course, unable to find a Schedule matching the ScheduleId on that student");
            return false;
        }
        sched.value.addCourse(c);
        this.db.updateSchedule(sched.key, sched.value);
        this.db.updateStudent(stud.key, stud.value);
        return true;
    }

    public boolean removeCourse(String sid, Course c) {
        studentLookup stud = this.db.getStudent(sid);
        if(stud == null || stud.value.getScheduleId() == null) {
            System.err.println("Failed to remove course, unable to get Schedule from given StudentId");
            return false;
        }

        scheduleLookup sched = this.db.getSchedule(stud.value.getScheduleId());
        if(sched == null || sched.value.getScheduleId() == null) {
            System.err.println("Failed to remove course, unable to find a Schedule matching the ScheduleId on that student");
            return false;
        }
        sched.value.removeCourse(c);
        this.db.updateSchedule(sched.key, sched.value);
        this.db.updateStudent(stud.key, stud.value);
        return true;
    }

    public ArrayList<String> viewRegisteredCourses(String sid) {
        return this.db.getCoursesForStudent(sid);
    }

    public boolean addMajor(String sid, Major m) {
        studentLookup stud = this.db.getStudent(sid);
        if (!stud.value.addMajor(m))
            return false;
        this.db.updateStudent(sid, stud.value);
        m.createRequiredCourses();
        return true;
    }

    public boolean removeMajor(String sid, Major m) {
        studentLookup stud = this.db.getStudent(sid);
        if (!stud.value.removeMajor(m))
            return false;
        this.db.updateStudent(sid, stud.value);
        return true;
    }

    public ArrayList<String> viewRegisteredMajors(String sid) {
        studentLookup stud = this.db.getStudent(sid);
        return stud.value.getMajors();
    }
}

package models.academics;
import java.util.ArrayList;

import models.academics.items.Registration;
import models.general.items.Course;
import models.general.items.courseLookup;
import models.general.people.studentLookup;
import src.DatabaseSupport;

public class RegistrationController {

    DatabaseSupport db;

    public RegistrationController(DatabaseSupport _db) {
        this.db = _db;
    }

    public boolean addCourse(Course cid, String sid, Integer credHours) {
        Registration r = new Registration();
        studentLookup stud = this.db.getStudent(sid);
        stud.value.addCourse(cid);
        this.db.updateStudent(sid, stud.value);
        return true;
    }

    public boolean removeCourse(String sid, Course c) {
        Registration r = new Registration();
        studentLookup stud = this.db.getStudent(sid);
        stud.value.removeCourse(c);
        this.db.updateStudent(sid, stud.value);
        return true;
    }

    public ArrayList<courseLookup> viewRegisteredCourses(String sid) {
        studentLookup stud = this.db.getStudent(sid);
        return stud.value.getCurrentCourses();
    }
}

package models.academics;
import java.util.ArrayList;

import models.academics.items.Registration;
import models.general.items.Course;
import models.general.items.Major;
import models.general.items.courseLookup;
import models.general.items.majorLookup;
import models.general.people.studentLookup;
import src.DatabaseSupport;

public class RegistrationController {

    DatabaseSupport db;

    public RegistrationController(DatabaseSupport _db) {
        this.db = _db;
    }

    public boolean addCourse(String sid, Course c) {
        studentLookup stud = this.db.getStudent(sid);
        stud.value.addCourse(c);
        this.db.updateStudent(sid, stud.value);
        return true;
    }

    public boolean removeCourse(String sid, Course c) {
        studentLookup stud = this.db.getStudent(sid);
        stud.value.removeCourse(c);
        this.db.updateStudent(sid, stud.value);
        return true;
    }

    public ArrayList<String> viewRegisteredCourses(String sid) {
        studentLookup stud = this.db.getStudent(sid);
        return stud.value.getCurrentCourses();
    }

    public boolean addMajor(String sid, Major m) {
        studentLookup stud = this.db.getStudent(sid);
        if (!stud.value.addMajor(m))
            return false;
        this.db.updateStudent(sid, stud.value);
        return true;
    }

    public boolean removeMajor(String sid, Major m) {
        studentLookup stud = this.db.getStudent(sid);
        if (!stud.value.removeMajor(m))
            return false;
        this.db.updateStudent(sid, stud.value);
        return true;
    }

    public ArrayList<majorLookup> viewRegisteredMajors(String sid) {
        studentLookup stud = this.db.getStudent(sid);
        return stud.value.getMajors();
    }
}

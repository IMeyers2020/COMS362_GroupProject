package models.academics;

import java.util.ArrayList;

import models.general.items.Course;
import models.general.items.courseLookup;
import models.general.people.studentLookup;
import src.DatabaseSupport;

public class CourseController {
    DatabaseSupport db;

    public CourseController(DatabaseSupport _db) {
        this.db = _db;
    }
    
    public ArrayList<courseLookup> getAllCourses() {
        return this.db.getCourses();
    }

    public courseLookup getCourse(String cid) {
        return this.db.getAvailableCourse(cid);
    }

    public ArrayList<courseLookup> getAllValidCourses() {
        return this.db.GetAllValidCourses();
    }

    public boolean addProfessorToCourse(String pId, courseLookup c) {
        courseLookup crsClone = c;
        crsClone.value.SetProfessorId(pId);
        return this.db.updateCourses(c.key, c.value);
    }

    public boolean addCourse(Course c) {
        return this.db.addCourse(c);
    }

    public void OutputCoursesForStudent(String sid) {
        studentLookup stud = this.db.getStudent(sid);
        this.db.PrintScheduleForStudent(stud.value);
    }
}

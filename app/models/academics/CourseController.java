package models.academics;

import java.util.HashMap;

import models.general.items.Course;
import models.general.people.studentLookup;
import src.DatabaseSupport;

public class CourseController {
    DatabaseSupport db;

    public CourseController(DatabaseSupport _db) {
        this.db = _db;
    }
    
    public HashMap<String, Course> getAllCourses() {
        return DatabaseSupport.getAllCourses();
    }

    public void OutputCoursesForStudent(String sid) {
        studentLookup stud = this.db.getStudent(sid);
        this.db.PrintScheduleForStudent(stud.value);
    }
}

package models.academics;

import java.util.ArrayList;
import java.util.HashMap;

import models.general.items.Course;
import models.general.items.courseLookup;
import src.DatabaseSupport;

public class CourseController {
    DatabaseSupport db;

    public CourseController(DatabaseSupport _db) {
        this.db = _db;
    }
    
    public HashMap<String, Course> getAllCourses() {
        return DatabaseSupport.getAllCourses();
    }

    public ArrayList<courseLookup> getRegisteredCoursesForStudent(String sid) {
        ArrayList<courseLookup> s = this.db.getRegisteredCoursesForStudent(sid);
        return s;
    }
}

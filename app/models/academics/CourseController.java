package models.academics;

import java.util.HashMap;

import models.general.items.Course;
import src.DatabaseSupport;

public class CourseController {
    
    public HashMap<String, Course> getAllCourses() {
        return DatabaseSupport.getAllCourses();
    }
}

package models.academics.items;

import java.util.HashMap;

import models.general.items.Course;
import src.DatabaseSupport;

public class Registration {
    public boolean addCourse(String sid, String cid, Integer credHours) {
        Course c = new Course(cid, credHours);
        DatabaseSupport ds = new DatabaseSupport();
        
        return ds.putCourse(sid, c);
    }

    public boolean removeCourse(String sid, Course c) {
        DatabaseSupport ds = new DatabaseSupport();

        return ds.removeCourse(sid, c);
    }

    public HashMap<String, Course> getCourses(String sid) {
        return DatabaseSupport.getCoursesForStudent(sid);
    }
}

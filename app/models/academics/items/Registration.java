package models.academics.items;

import java.util.HashMap;

import models.general.items.Course;
import src.DatabaseSupport;

public class Registration {
    //subject to change once saved files have been set up
    public boolean addCourse(Course c, String sid, Integer credHours) {
        // Course c = new Course(cid, credHours);
        DatabaseSupport ds = new DatabaseSupport();
        //will be replaced by 'student s = ds.getStudent(sid)' if (s.addCourse(c)) {return ds.putStudent(s)}
        if(ds.getStudent(sid).addCourse(c)) {
            return true;
        }
        return false;
    }

    //subject to change once saved files have been set up
    public boolean removeCourse(String sid, Course c) {
        DatabaseSupport ds = new DatabaseSupport();
        
        //will be replaced by 'student s = ds.getStudent(sid)' if (s.removeCourse(c)) {return ds.putStudent(s)}
        if(ds.getStudent(sid).removeCourse(c)) {
            return true;
        }
        return false;
    }

    public HashMap<String, Course> getRegisteredCoursesForStudent(String sid) {
        return DatabaseSupport.getRegisteredCoursesForStudent(sid);
    }
}

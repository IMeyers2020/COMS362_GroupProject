package models.academics;
import java.util.HashMap;

import models.academics.items.Registration;
import models.general.items.Course;

public class RegistrationController {
    public boolean addCourse(Course cid, String sid, Integer credHours) {
        Registration r = new Registration();
        return r.addCourse(cid, sid, credHours);
    }

    public boolean removeCourse(String sid, Course c) {
        Registration r = new Registration();
        return r.removeCourse(sid, c);
    }

    public HashMap<String, Course> getCoursesForStudent(String sid) {
        Registration r = new Registration();
        return r.getCoursesForStudent(sid);
    }
}

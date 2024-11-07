package models.academics;

import java.util.List;

import models.general.items.Course;
import models.general.people.student;
import src.UniversityProject;

public class RegistrationController {
    public boolean addCourse(String sid, Course c) {
        if (UniversityProject.test.containsKey(sid)) {
            student tmp = UniversityProject.test.get(sid);
            tmp.addCourse(c);
            UniversityProject.test.put(sid, tmp);
            return true;
        }
        return false;
    }

    public boolean removeCourse(String sid, Course c) {
        if (UniversityProject.test.containsKey(sid)) {
            student tmp = UniversityProject.test.get(sid);
            tmp.removeCourse(c);
            UniversityProject.test.put(sid, tmp);
            return true;
        }
        return false;
    }

    public List<String> getCurrentCourses(String sid) {
        return UniversityProject.test.get(sid).getCurrentCourses();
    }
}

package app.models.academics;

import java.util.List;

import app.models.general.items.Course;
import app.models.general.people.student;
import app.src.UniversityProject;

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
        student tmp = null;
        if (UniversityProject.test.containsKey(sid)) {
            tmp = UniversityProject.test.get(sid);
            tmp.removeCourse(c);
            UniversityProject.test.put(sid, tmp);
            return true;
        }
        return false;
    }

    public List<String> getCurrentCourses(String sid) {
        student tmp = UniversityProject.test.get(sid);
        System.out.println(tmp.getName());
        return UniversityProject.test.get(sid).getCurrentCourses();
    }
}

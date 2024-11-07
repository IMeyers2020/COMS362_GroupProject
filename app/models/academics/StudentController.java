package models.academics;

import models.general.people.student;
import src.DatabaseSupport;

public class StudentController {
    public student getStudent(String sid) {
        return DatabaseSupport.getStudents().get(sid);
    }
}

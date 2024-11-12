package models.dorms;

import java.util.Scanner;

import models.academics.StudentController;
import models.general.people.student;
import src.DatabaseSupport;

public class DormController {
    private DormManager dormManager;
    public StudentController sc;

    public DormController(DatabaseSupport db, StudentController _sc) {
        dormManager = new DormManager(db);
        this.sc = _sc;
    }

    public boolean newDorm(String dormId) {
        return dormManager.addDorm(dormId);
    }

    public boolean addDorm(String dormId, String studentId) {
        student student = this.sc.getStudent(studentId);
        return dormManager.addStudentToDorm(dormId, student);
    }

    public boolean removeDorm(String dormId, student student) {
        return dormManager.removeStudentFromDorm(dormId, student);
    }
    
}

package models.dorms;

import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Collectors;

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

    public boolean removeDorm(String dormId) {
        HashMap<String, student> students = this.sc.getAllStudents();
        for(student stud : students.values()) {
            if(stud.getDormId() == dormId) {
                stud.setDormId(null);
            }
        }

        return dormManager.removeDorm(dormId);
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

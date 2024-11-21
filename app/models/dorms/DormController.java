package models.dorms;

import java.util.ArrayList;

import models.academics.StudentController;
import models.general.people.student;
import models.general.people.studentLookup;
import src.DatabaseSupport;

public class DormController {
    private DormManager dormManager;
    public StudentController sc;

    public DormController(DatabaseSupport db, StudentController _sc) {
        dormManager = new DormManager(db);
        this.sc = _sc;
    }

    public boolean removeDorm(String dormId) {
        ArrayList<studentLookup> students = this.sc.getAllStudents();
        for(studentLookup stud : students) {
            if(stud.value.getDormId().equals(dormId)) {
                stud.value.setDormId(null);
                this.sc.updateStudent(stud.value);
            }
        }

        return dormManager.removeDorm(dormId);
    }

    public boolean newDorm(String dormId) {
        return dormManager.addDorm(dormId);
    }

    public boolean addDorm(String dormId, String studentId) {
        studentLookup student = this.sc.getStudent(studentId);
        return dormManager.addStudentToDorm(dormId, student.value);
    }

    public boolean removeDorm(String dormId, student student) {
        return dormManager.removeStudentFromDorm(dormId, student);
    }
    
}

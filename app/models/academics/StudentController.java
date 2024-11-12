package models.academics;

import java.util.HashMap;

import models.general.people.student;
import src.DatabaseSupport;

public class StudentController {
    DatabaseSupport db;

    public StudentController(DatabaseSupport _db) {
        this.db = _db;
    }

    public student getStudent(String sid) {
        return this.db.getStudents().get(sid);
    };

    public HashMap<String, student> getAllStudents() {
        return this.db.getStudents();
    }
}

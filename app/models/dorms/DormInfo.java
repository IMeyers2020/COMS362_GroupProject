package models.dorms;

import models.general.people.student;
import src.DatabaseSupport;

public class DormInfo {
    private static final double DORM_PRICE = 500.0;
    private String dormId;
    private DatabaseSupport db;

    // Constructor with dependency injection of DatabaseSupport
    public DormInfo(String dormId, DatabaseSupport db) {
        this.dormId = dormId;
        this.db = db;
    }

    public String getDormId() {
        return this.dormId;
    }

    public boolean addStudent(student student) {
        student.setDormId(dormId);
        return true;
    }

    public boolean removeStudent(student student) {
        student existingStudent = db.getStudent(student.getStudentId()).value;
        if (existingStudent != null) {
            existingStudent.clearDormId();
            db.updateStudent(existingStudent.getStudentId(), existingStudent);
            return true;
        }
        return false;
    }
    
}

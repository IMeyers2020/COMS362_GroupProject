package models.dorms;

import java.util.HashMap;

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

    public boolean addStudent(student student) {
        if (db.getDormssize() < 1 && student.canAffordDorm(DORM_PRICE)) {
            db.addStudent(student.getStudentId(), student);
            student.setDormId(dormId);
            student.deductPayment(DORM_PRICE);
            return true;
        }
        return false;
    }

    public boolean removeStudent(student student) {
        student existingStudent = db.getStudent(student.getStudentId());
        if (existingStudent != null) {
            db.getStudent(existingStudent.getStudentId()).clearDormId();
            return true;
        }
        return false;
    }
    
}

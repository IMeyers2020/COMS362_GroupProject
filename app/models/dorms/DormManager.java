package models.dorms;

import java.util.HashMap;

import models.general.people.student;
import src.DatabaseSupport;

public class DormManager {
    private DatabaseSupport db;

    public DormManager(DatabaseSupport db) {
        this.db = db;
    }

    public boolean addStudentToDorm(String dormId, student student) {
        DormInfo dorm = db.getDorm(dormId);
        if (dorm != null) {
            return dorm.addStudent(student);
        }
        return false;
    }

    public boolean removeStudentFromDorm(String dormId, student student) {
        DormInfo dorm = db.getDorm(dormId);
        if (dorm != null) {
            return dorm.removeStudent(student);
        }
        return false;
    }

    public boolean addDorm(String dormId) {
        return db.addDorm(dormId);
    }

    public boolean removeDorm(String dormId) {
        return db.removeDorm(dormId);
    }
    
}

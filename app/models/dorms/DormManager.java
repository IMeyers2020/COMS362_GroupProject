package models.dorms;

import models.general.people.student;
import src.DatabaseSupport;

public class DormManager {
    private DatabaseSupport db;

    public DormManager(DatabaseSupport db) {
        this.db = db;
    }

    public boolean addStudentToDorm(String dormId, student student) {
        DormInfo dorm = db.getDorm(dormId).value;
        if (dorm != null) {
            return dorm.addStudent(student);
        }
        System.out.println("FAILEDDD");
        return false;
    }

    public boolean removeStudentFromDorm(String dormId, student student) {
        DormInfo dorm = db.getDorm(dormId).value;
        if (dorm != null) {
            return dorm.removeStudent(student);
        }
        return false;
    }

    public boolean addDorm(String dormId) {
        DormInfo di = new DormInfo(dormId, db);
        return db.addDorm(dormId, di);
    }

    public boolean removeDorm(String dormId) {
        return db.removeDorm(dormId);
    }
    
}

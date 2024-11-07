package models.dorms;

import models.general.people.student;
import src.DatabaseSupport;

public class DormController {
    private DormManager dormManager;

    public DormController(DatabaseSupport db) {
        dormManager = new DormManager(db);
    }

    public boolean addDorm(String dormId, student student) {
        return dormManager.addStudentToDorm(dormId, student);
    }

    public boolean removeDorm(String dormId, student student) {
        return dormManager.removeStudentFromDorm(dormId, student);
    }
    
}

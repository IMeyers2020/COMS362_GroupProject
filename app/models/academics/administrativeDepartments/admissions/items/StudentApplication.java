package models.academics.administrativeDepartments.admissions.items;

import models.general.people.student;
import src.DatabaseSupport;

public class StudentApplication {

    public DatabaseSupport db;

    public StudentApplication(DatabaseSupport _db) {
        this.db = _db;
    }

    public boolean addStudent(String sId, String name, String address, String SSN) {
        student stud = new student(sId, name, address, SSN, null, 0.00, null);

        return this.db.addStudent(sId, stud);
    }
}

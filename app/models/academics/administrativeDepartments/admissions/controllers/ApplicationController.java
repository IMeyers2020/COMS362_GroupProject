package models.academics.administrativeDepartments.admissions.controllers;

import src.DatabaseSupport;

public class ApplicationController {
    DatabaseSupport db;

    public ApplicationController(DatabaseSupport _db) {
        this.db = _db;
    }

    public boolean addStudent(String sId, String name, String address, String SSN) {
        StudentApplication sa = new StudentApplication(this.db);

        return sa.addStudent(sId, name, address, SSN);
    }
}
package models.academics.administrativeDepartments.admissions.controllers;

import models.academics.administrativeDepartments.admissions.items.StudentApplication;

public class ApplicationController {
    public boolean addStudent(String sId, String name, String address, String SSN) {
        StudentApplication sa = new StudentApplication();

        return sa.addStudent(sId, name, address, SSN);
    }
}
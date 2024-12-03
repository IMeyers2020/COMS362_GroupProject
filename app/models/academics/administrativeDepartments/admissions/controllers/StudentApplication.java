package models.academics.administrativeDepartments.admissions.controllers;

import models.general.items.schedule;
import models.general.people.student;
import src.DatabaseSupport;

public class StudentApplication {

    public DatabaseSupport db;

    public StudentApplication(DatabaseSupport _db) {
        this.db = _db;
    }

    public boolean addStudent(String sId, String name, String address, String SSN) {
        student stud = new student(sId, name, address, SSN, null, 0.00, null);

        if(stud == null || stud.getScheduleId() == null) {
            System.err.println("Failed to add student. Unable to get a Schedule associated with that Student");
            return false;
        }

        this.db.addSchedule(stud.getScheduleId(), new schedule(stud.getScheduleId()));

        return this.db.addStudent(sId, stud);
    }
}

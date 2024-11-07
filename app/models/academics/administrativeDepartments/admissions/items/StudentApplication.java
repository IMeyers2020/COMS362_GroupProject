package models.academics.administrativeDepartments.admissions.items;

import models.general.people.student;
import src.DatabaseSupport;

public class StudentApplication {
    public boolean addStudent(String sId, String name, String address, String SSN) {
        student newStudent = new student();
        newStudent.setStudentId(sId);
        newStudent.setName(name);
        newStudent.setAddress(address);
        newStudent.setSSN(SSN);

        DatabaseSupport db = new DatabaseSupport();

        return db.putStudent(newStudent);
    }
}

package models.academics.administrativeDepartments.humanResources.items;

import models.general.people.professor;
import models.general.people.student;
import src.DatabaseSupport;

public class Offer {
    public boolean addProfessor(String pId, String name, String address, String AOS) {
        professor newStudent = new professor(pId, name, address, AOS);

        DatabaseSupport db = new DatabaseSupport();

        return db.putProfessor(newStudent);
    }
}

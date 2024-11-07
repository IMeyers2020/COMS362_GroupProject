package models.academics.administrativeDepartments.humanResources.items;

import models.general.people.professor;
import src.DatabaseSupport;

public class Offer {
    public boolean addProfessor(String pId, String name, String address, String AOS) {
        professor newProf = new professor(pId, name, address, AOS);

        DatabaseSupport db = new DatabaseSupport();

        return db.putProfessor(newProf);
    }
}

package models.academics.administrativeDepartments.humanResources.items;

import models.general.people.professor;
import src.DatabaseSupport;

public class Offer {
    public DatabaseSupport db;

    public Offer(DatabaseSupport _db) {
        this.db = _db;
    }

    public boolean addProfessor(String pId, String name, String address, String AOS) {
        professor newProf = new professor(pId, name, address, AOS);

        return this.db.addProfessor(newProf);
    }
}

package models.general.items;

import java.util.ArrayList;

import models.general.people.genericPerson;
import models.general.people.professor;
import src.DatabaseSupport;

public class Offer {
    public DatabaseSupport db;

    public Offer(DatabaseSupport _db) {
        this.db = _db;
    }

    public boolean addProfessor(String pId, String name, String address, String AOS, ArrayList<genericPerson> refs) {
        professor newProf = new professor(pId, name, address, AOS, refs);

        this.db.PrintProfessorOffer(newProf);

        return this.db.addProfessor(pId, newProf);
    }
}

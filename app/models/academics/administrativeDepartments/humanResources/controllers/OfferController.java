package models.academics.administrativeDepartments.humanResources.controllers;

import java.util.ArrayList;

import models.general.items.Offer;
import models.general.people.genericPerson;
import src.DatabaseSupport;

public class OfferController {

    DatabaseSupport db;

    public OfferController(DatabaseSupport _db) {
        this.db = _db;
    }

    public boolean addProfessor(String pId, String name, String address, String AOS, ArrayList<genericPerson> refs) {
        Offer sa = new Offer(this.db);

        return sa.addProfessor(pId, name, address, AOS, refs);
    }
}

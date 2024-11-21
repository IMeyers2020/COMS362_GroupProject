package models.academics.administrativeDepartments.humanResources.controllers;

import models.general.items.Offer;
import src.DatabaseSupport;

public class OfferController {

    DatabaseSupport db;

    public OfferController(DatabaseSupport _db) {
        this.db = _db;
    }

    public boolean addProfessor(String pId, String name, String address, String AOS) {
        Offer sa = new Offer(this.db);

        return sa.addProfessor(pId, name, address, AOS);
    }
}

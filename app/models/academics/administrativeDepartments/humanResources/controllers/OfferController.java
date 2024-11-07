package models.academics.administrativeDepartments.humanResources.controllers;

import models.academics.administrativeDepartments.humanResources.items.Offer;

public class OfferController {
    public boolean addProfessor(String pId, String name, String address, String AOS) {
        Offer sa = new Offer();

        return sa.addProfessor(pId, name, address, AOS);
    }
}

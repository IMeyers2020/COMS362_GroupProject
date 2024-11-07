package models.academics.administrativeDepartments.humanResources.controllers;

import models.academics.administrativeDepartments.admissions.items.StudentApplication;
import models.academics.administrativeDepartments.humanResources.items.Offer;

public class OfferController {
    public boolean addProfessor(String sId, String name, String address, String SSN) {
        Offer sa = new Offer();

        return sa.addProfessor(sId, name, address, SSN);
    }
}

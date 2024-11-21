package models.finances.controllers;

import java.util.ArrayList;
import models.general.people.student;
import models.finances.paymentServices.Scholarship;
import models.finances.paymentServices.ScholarshipLookup;
import src.DatabaseSupport;

public class ScholarshipController {

    public boolean validateScholarship(String scholarshipName, student s) {
        DatabaseSupport db = new DatabaseSupport();
        boolean scholarshipExists = false;

        ArrayList<ScholarshipLookup> existingScholarships = db.getScholarships();
        
        for(ScholarshipLookup scholarship : existingScholarships) {
            if(scholarship.value.getScholarshipName().equalsIgnoreCase(scholarshipName)) {
                scholarshipExists = true;
            } 
        }

        ArrayList<Scholarship> scholarships = s.getScholarships();
        
        for (Scholarship scholarship : scholarships) {
            // Check if the current scholarship's name matches the provided name
            if (scholarship.getScholarshipName().equalsIgnoreCase(scholarshipName)) {
                System.out.println("This student has already added this scholarship.");
                return false;
            }
        }

        if (scholarshipExists) {
            return true;
        } else {
            return false;
        }

    }
    
}

package models.finances.controllers;

import java.util.ArrayList;
import models.general.people.student;
import models.finances.paymentServices.Scholarship;
import src.DatabaseSupport;

/**
 * The ScholarshipController class provides functionality for validating 
 * and managing scholarships for students. It verifies the existence of a 
 * scholarship and checks whether the student has already added the specified scholarship.
 * 
 * This class interacts with the database through DatabaseSupport to retrieve available scholarships 
 * and with the student class to access the student's scholarships.
*/
public class ScholarshipController {

    /**
     * Validates the specified scholarship for a given student.
     * 
     * The method performs the following checks:
     * - Checks if the scholarship exists in the database.
     * - Ensures the student has not already added the specified scholarship.
     * If both conditions are met, the scholarship is considered valid.
     * @param scholarshipName The name of the scholarship to validate.
     * @param s The student object representing the student for whom the scholarship is being validated.
     * @return true if the scholarship exists and the student has not already added it; false otherwise.
    */
    public boolean validateScholarship(String scholarshipName, student s) {
        DatabaseSupport db = new DatabaseSupport();
        boolean scholarshipExists = false;

        ArrayList<Scholarship> existingScholarships = db.getScholarships();
        
        for(Scholarship scholarship : existingScholarships) {
            if(scholarship.getScholarshipName().equalsIgnoreCase(scholarshipName)) {
                scholarshipExists = true;
            } 
        }

        ArrayList<Scholarship> scholarships = s.getScholarships();

        if (!scholarships.contains("")) {
            for (Scholarship scholarship : scholarships) {
                // Check if the current scholarship's name matches the provided name
                if (scholarship.getScholarshipName().equalsIgnoreCase(scholarshipName)) {
                    System.out.println("This student has already added this scholarship.");
                    return false;
                }
            }
        }
        
        if (scholarshipExists) {
            return true;
        } else {
            return false;
        }

    }
    
}

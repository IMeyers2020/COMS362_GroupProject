package models.Diningplan;

import java.io.IOException;

import src.DatabaseSupport;     // Assuming DatabaseSupport handles DB operations for storing dining plans

/**
 * Controller class responsible for managing dining plans.
 * Provides methods to add and remove dining plans in the database.
 */
public class Dplancontroller {

    /** The DatabaseSupport object used to interact with the database */
    private DatabaseSupport db;

    /**
     * Constructor for DplanController class.
     * Initializes the controller with the provided DatabaseSupport object.
     *
     * @param db the DatabaseSupport object used to interact with the database
     */
    public Dplancontroller(DatabaseSupport db) {
        this.db = db;
    }

    /**
     * Adds a new dining plan to the database.
     * Creates a dining plan object with the provided details and stores it in the database.
     *
     * @param type the type of the dining plan (e.g., "Meal Plan", "All-You-Can-Eat")
     * @param studentId the ID of the student associated with the dining plan
     * @param term the academic term for which the plan is valid (e.g., "Fall 2024")
     * @param diningDollars the amount of dining dollars available in the plan
     * @param mealSwipes the number of meal swipes available in the plan
     * @param id the unique identifier for the dining plan
     * @return true if the dining plan was successfully added, otherwise false
     * @throws IOException if an error occurs during database interaction
     */
    public boolean addDiningPlan(String type, String studentId, String term, int diningDollars, 
                                 int mealSwipes, String id) throws IOException 
    {
        // Create a new Dplan object with the provided details
        Dplan dplan = new Dplan(type, studentId, term, diningDollars, mealSwipes, id);
        
        // Store the dining plan in the database
        this.db.addDiningPlan(id, dplan);
        
        // Return true to indicate successful addition
        return true;
    }

    /**
     * Removes a dining plan from the database using its unique identifier.
     *
     * @param dplanId the unique identifier of the dining plan to be removed
     * @return true if the dining plan was successfully removed, otherwise false
     */
    public boolean removeDiningPlan(String dplanId) {
        // Calls the database method to remove the dining plan by its ID
        return db.removeDiningPlan(dplanId);
    }
}

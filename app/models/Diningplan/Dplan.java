package models.Diningplan;

import models.general.people.student;  // Importing the student class from the people package.

/**
 * Represents a dining plan for a student.
 * This class stores information about the type of dining plan, the associated student,
 * the academic term, the amount of dining dollars, and the number of meal swipes available.
 */
public class Dplan {
    
    /** The type of the dining plan (e.g., Meal Plan, All-You-Can-Eat, etc.) */
    private String type;

    /** The student associated with this dining plan */
    private student student;

    /** The academic term (e.g., Fall 2024, Spring 2025, etc.) for this dining plan */
    private String term;

    /** The amount of dining dollars available in the dining plan */
    private int diningdollars;

    /** The number of meal swipes available in the dining plan */
    private int mealswipes;
    private String id;
    
        private String studentId;
    
        /**
         * Default constructor. Initializes the dining plan with default values.
         */
        public Dplan() {
            this.type = "";
            this.student = null;
            this.term = "";
            this.diningdollars = 0;
            this.mealswipes = 0;
        }
    
        public Dplan(String type, String studentId, String term, int diningDollars, int mealSwipes, String id) {
        this.type = type;            // Set the dining plan type (e.g., "Meal Plan", "All-You-Can-Eat")
        this.studentId = studentId;  // Set the student ID associated with the dining plan
        this.term = term;            // Set the academic term for which the dining plan is valid (e.g., "Fall 2024")
        this.diningdollars = diningDollars;  // Set the number of dining dollars available in the plan
        this.mealswipes = mealSwipes;        // Set the number of meal swipes available in the plan
        this.id = id;                // Set the unique identifier for the dining plan
    }

    /**
     * Gets the type of the dining plan.
     *
     * @return the type of the dining plan (e.g., "Meal Plan", "All-You-Can-Eat")
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the dining plan.
     *
     * @param type the type of the dining plan (e.g., "Meal Plan", "All-You-Can-Eat")
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the student associated with this dining plan.
     *
     * @return the student object associated with the dining plan
     */
    public student getStudent() {
        return student;
    }

    /**
     * Sets the student associated with this dining plan.
     *
     * @param student the student to associate with this dining plan
     */
    public void setStudent(student student) {
        this.student = student;
    }

    /**
     * Gets the academic term for the dining plan.
     *
     * @return the academic term (e.g., "Fall 2024", "Spring 2025")
     */
    public String getTerm() {
        return term;
    }

    /**
     * Sets the academic term for the dining plan.
     *
     * @param term the academic term (e.g., "Fall 2024", "Spring 2025")
     */
    public void setTerm(String term) {
        this.term = term;
    }

    /**
     * Gets the number of dining dollars available in the dining plan.
     *
     * @return the amount of dining dollars in the plan
     */
    public int getDiningdollars() {
        return diningdollars;
    }

    /**
     * Sets the number of dining dollars available in the dining plan.
     *
     * @param diningdollars the amount of dining dollars to set
     */
    public void setDiningdollars(int diningdollars) {
        this.diningdollars = diningdollars;
    }

    /**
     * Gets the number of meal swipes available in the dining plan.
     *
     * @return the number of meal swipes available in the dining plan
     */
    public int getMealswipes() {
        return mealswipes;
    }

    /**
     * Sets the number of meal swipes available in the dining plan.
     *
     * @param mealswipes the number of meal swipes to set
     */
    public void setMealswipes(int mealswipes) {
        this.mealswipes = mealswipes;
    }

    /**
     * Displays the details of the dining plan.
     * This includes the type, associated student, term, dining dollars, and meal swipes.
     */
    public void displayDPlanInfo() {
        System.out.println("Dining Plan Type: " + type);
        System.out.println("Student: " + (student != null ? student.toString() : "No student assigned"));
        System.out.println("Term: " + term);
        System.out.println("Dining Dollars: " + diningdollars);
        System.out.println("Meal Swipes: " + mealswipes);
    }
}

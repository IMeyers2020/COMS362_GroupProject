package models.finances.paymentServices;

/**
 * The Scholarship class represents a scholarship associated with a student.
 * It stores details such as the scholarship ID, name, amount, and whether the scholarship has been applied.
*/
public class Scholarship {
    private String scholarshipId;
    private String scholarshipName;
    private double scholarshipAmount;
    private boolean isApplied;

    /**
     * Default constructor for creating an instance of Scholarship with no initial values.
     */
    public Scholarship () {}

    /**
     * Constructs a Scholarship object with specified details.
     * @param scholarshipId The unique identifier for the scholarship.
     * @param scholarshipName The name of the scholarship.
     * @param scholarshipAmount The amount of the scholarship.
     * @param isApplied The status of the scholarship application (false by default).
    */
    public Scholarship (String scholarshipId, String scholarshipName, double scholarshipAmount, boolean isApplied) {
        this.scholarshipId = scholarshipId;
        this.scholarshipName = scholarshipName;
        this.scholarshipAmount = scholarshipAmount;
        this.isApplied = false;
    }

    /**
     * Marks the scholarship as applied.
    */
    public void setApplied() {
        this.isApplied = true;
    }

    /**
     * Checks if the scholarship has been applied.
     * @return true if the scholarship has been applied, false otherwise.
    */
    public boolean checkIsApplied() {
        if (this.isApplied == true) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the unique identifier for the scholarship.
     * @return The scholarship ID.
    */
    public String getScholarshipId() {
        return scholarshipId;
    }

    /**
     * Gets the name of the scholarship.
     * @return The scholarship name.
    */
    public String getScholarshipName() {
        return scholarshipName;
    }

    /**
     * Gets the amount of the scholarship.
     * @return The scholarship amount.
    */
    public double getScholarshipAmount() {
        return scholarshipAmount;
    }

    /**
     * Sets the unique identifier for the scholarship.
     * @param scholarshipId The scholarship ID to set.
    */
    public void setScholarshipId(String scholarshipId) {
        this.scholarshipId = scholarshipId;
    }

    /**
     * Sets the name of the scholarship. 
     * @param scholarshipName The scholarship name to set.
    */
    public void setScholarshipName(String scholarshipName) {
        this.scholarshipName = scholarshipName;
    }

    /**
     * Sets the amount of the scholarship.
     * @param scholarshipAmount The scholarship amount to set.
    */
    public void setScholarshipAmount(double scholarshipAmount) {
        this.scholarshipAmount = scholarshipAmount;
    }
}

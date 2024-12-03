package models.finances.paymentServices;

public class Scholarship {
    private String scholarshipId;
    private String scholarshipName;
    private double scholarshipAmount;
    private boolean isApplied;

    public Scholarship () {}

    public Scholarship (String scholarshipId, String scholarshipName, double scholarshipAmount, boolean isApplied) {
        this.scholarshipId = scholarshipId;
        this.scholarshipName = scholarshipName;
        this.scholarshipAmount = scholarshipAmount;
        this.isApplied = false;
    }

    public void setApplied() {
        this.isApplied = true;
    }

    public boolean checkIsApplied() {
        if (this.isApplied == true) {
            return true;
        } else {
            return false;
        }
    }

    public String getScholarshipId() {
        return scholarshipId;
    }

    public String getScholarshipName() {
        return scholarshipName;
    }

    public double getScholarshipAmount() {
        return scholarshipAmount;
    }

    public void setScholarshipId(String scholarshipId) {
        this.scholarshipId = scholarshipId;
    }

    public void setScholarshipName(String scholarshipName) {
        this.scholarshipName = scholarshipName;
    }

    public void setScholarshipAmount(double scholarshipAmount) {
        this.scholarshipAmount = scholarshipAmount;
    }
}

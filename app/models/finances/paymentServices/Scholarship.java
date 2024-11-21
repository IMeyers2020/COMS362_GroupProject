package models.finances.paymentServices;

public class Scholarship {
    private String scholarshipId;
    private String scholarshipName;
    private double scholarshipAmount;

    public Scholarship () {}

    public Scholarship (String scholarshipId, String scholarshipName, double scholarshipAmount) {
        this.scholarshipId = scholarshipId;
        this.scholarshipName = scholarshipName;
        this.scholarshipAmount = scholarshipAmount;
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

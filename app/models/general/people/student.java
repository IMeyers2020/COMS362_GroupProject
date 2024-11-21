package models.general.people;

import java.util.ArrayList;
import java.util.List;

import models.finances.paymentServices.FinancialInfo;
import models.finances.paymentServices.Scholarship;
import models.general.items.Course;
import models.general.items.Major;
import models.general.items.courseLookup;
import models.general.items.majorLookup;
import models.general.items.schedule;

public class student {
    private schedule sched;
    private List<String> completedCourses;
    private ArrayList<majorLookup> majors;
    private String studentId;      // Unique identifier for the student
    private String name;           // Full name of the student
    private FinancialInfo financialInfo; // Financial info of the student (linked to the FinancialInfo class)
    private double accountBalance; // The current balance in the student's account
    private ArrayList<Scholarship> scholarships;
    private String address;
    private String ssn;
    private String dormId;


    public student(String studentId, String name, String address, String ssn, FinancialInfo financialInfo, double accountBalance, ArrayList<Scholarship> scholarships) {
        this.studentId = studentId;
        this.name = name;
        this.financialInfo = financialInfo;
        this.accountBalance = accountBalance;
        sched = new schedule();
        completedCourses = new ArrayList<>();
        majors = new ArrayList<>();
        this.address = address;
        this.ssn = ssn;
    }

    public student() {
        sched = new schedule();
        completedCourses = new ArrayList<>();
        majors = new ArrayList<>();
    }

    public boolean canAffordDorm(double dormPrice) {
        return accountBalance >= dormPrice;
    }

    public void setDormId(String dormId) {
        this.dormId = dormId;
    }

    public void clearDormId() {
        this.dormId = null;
    }

    public String getDormId() { return dormId; }

    public ArrayList<courseLookup> getCurrentCourses() { return sched.getCourses(); }

    public boolean addCourse(Course c) {
        if (completedCourses.containsAll(c.getPrereqs())){
            return sched.addCourse(c);
        }
        return false;
    }

    public boolean removeCourse(Course c) { return sched.removeCourse(c); }

    public void setschedule(schedule s) { sched = s; }

    public schedule getschedule() { return sched; }

    public ArrayList<majorLookup> getMajors() { return majors; }

    public boolean addMajor(Major m) {
        if (majors.size() >= 2)
            return false;

        ArrayList<majorLookup> majorClone = getMajors();

        for (majorLookup maj : majorClone) {
            if (maj.value.getMajorID().equals(m.getMajorID()))
                return false;
        }

        majorLookup majorToAdd = new majorLookup(m.getMajorID(), m);
        majorClone.add(majorToAdd);

        this.setMajors(majorClone);
        return true;
    }

    public boolean removeMajor(Major m) {
        if (majors.size() < 2)
            return false;
        ArrayList<majorLookup> ms = this.getMajors();
        ms.removeIf(major -> major.value.getMajorID().equals(m.getMajorID()));
        setMajors(ms);
        return true;
    }

    public void setMajors(ArrayList<majorLookup> majors) { this.majors = majors; }
    
    // Getters and Setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSSN() { return this.ssn; }
    public void setSSN(String ssn) { this.ssn = ssn; }

    public String getAddress() { return this.address; }
    public void setAddress(String address) { this.address = address; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FinancialInfo getFinancialInfo() {
        return financialInfo;
    }

    public void setFinancialInfo(FinancialInfo financialInfo) {
        this.financialInfo = financialInfo;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public ArrayList<Scholarship> getScholarships() {
        return scholarships;
    }

    public void setScholarships(Scholarship scholarship) {
        scholarships.add(scholarship);
    }

    // Optional: Method to check if the student has a positive account balance
    public boolean hasPositiveBalance() {
        return accountBalance > 0;
    }

    // // Method to deduct a payment from the student's account balance
    // public void deductPayment(double amount) {
    //     if (amount > 0 && amount <= accountBalance) {
    //         accountBalance -= amount;
    //     } else {
    //         throw new IllegalArgumentException("Invalid payment amount.");
    //     }
    // }

    // Optional: toString method for debugging purposes
    @Override
    public String toString() {
        return "Student [studentId=" + studentId + ", name=" + name + ", financialInfo=" + financialInfo + ", accountBalance=" + accountBalance + "]";
    }

}

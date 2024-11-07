package app.models.general.people;

import java.util.ArrayList;
import java.util.List;

import app.models.finances.paymentServices.FinancialInfo;
import app.models.general.items.Course;
import app.models.general.items.Schedule;

public class student extends genericPerson {
    private Schedule sched;
    private List<String> completedCourses;
    private String studentId;      // Unique identifier for the student
    private String name;           // Full name of the student
    private FinancialInfo financialInfo; // Financial info of the student (linked to the FinancialInfo class)
    private double accountBalance; // The current balance in the student's account

    public student(String studentId, String name, FinancialInfo financialInfo, double accountBalance) {
        this.studentId = studentId;
        this.name = student.super.getFirstName() + student.super.getLastName();
        this.financialInfo = financialInfo;
        this.accountBalance = accountBalance;
    }

    public student() {
        sched = new Schedule();
        completedCourses = new ArrayList<>();
    }

    public List<String> getCurrentCourses() {
        return sched.getCourses();
    }

    public boolean addCourse(Course c) {
        if (completedCourses.containsAll(c.getPrereqs())){
            return sched.addCourse(c);
        }
        return false;
    }

    public boolean removeCourse(Course c) {
        return sched.removeCourse(c);
    }

    public void setSchedule(Schedule s) {
        sched = s;
    }
    public Schedule getSchedule() {
        return sched;
    }

    // Getters and Setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

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

    // Optional: Method to check if the student has a positive account balance
    public boolean hasPositiveBalance() {
        return accountBalance > 0;
    }

    // Method to deduct a payment from the student's account balance
    public void deductPayment(double amount) {
        if (amount > 0 && amount <= accountBalance) {
            accountBalance -= amount;
        } else {
            throw new IllegalArgumentException("Invalid payment amount.");
        }
    }

    // Optional: toString method for debugging purposes
    @Override
    public String toString() {
        return "Student [studentId=" + studentId + ", name=" + name + ", financialInfo=" + financialInfo + ", accountBalance=" + accountBalance + "]";
    }

}

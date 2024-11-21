package models.finances.paymentServices;

import models.general.people.student;

public class FinancialInfo {
    private String cardType; // "debit" or "credit"
    private String cardNumber;
    private String billingAddress;
    private student Student;

    public FinancialInfo() { }

    // Constructor
    public FinancialInfo(String cardType, String cardNumber, String billingAddress, student Student) {
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.billingAddress = billingAddress;
        this.Student = Student;
    }

    // Getters and Setters
    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public student getStudent() {
        return Student;
    }

    public void setStudent(student Student) {
        this.Student = Student;
    }
}

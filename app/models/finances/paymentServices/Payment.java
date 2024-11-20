package models.finances.paymentServices;

import java.io.IOException;

import src.DatabaseSupport;

public class Payment {
    private String paymentId;      // Unique identifier for the payment
    private double amount;         // Amount of the payment
    private String paymentType;    // Type of payment: "debit" or "credit"
    private boolean isConfirmed;   // Whether the payment has been confirmed

    public Payment() {
        this.paymentId = null;
        this.amount = 0;
        this.paymentType = null;
        this.isConfirmed = false;
    }

    // Constructor
    public Payment(String paymentId, double amount, String paymentType, boolean isConfirmed) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentType = paymentType;
        this.isConfirmed = isConfirmed;
    }

    // Getters and Setters
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public boolean addStudentPayment (String paymentId, double amount, String paymentType, boolean isConfirmed) throws IOException{
        Payment p = new Payment(paymentId,amount,paymentType,isConfirmed);
        DatabaseSupport db = new DatabaseSupport();

        return db.putPayment(p);
    }
}

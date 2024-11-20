package models.finances.controllers;

import java.io.IOException;

import models.finances.paymentServices.Payment;

public class PaymentController {
    public boolean addStudentPayment (String paymentId, double amount, String paymentType, boolean isConfirmed) throws IOException{
        Payment p = new Payment();

        return p.addStudentPayment(paymentId,amount,paymentType,isConfirmed);
    }

    // Method to check if the payment amount is valid
    public boolean isValidAmount(Payment p) {
        // Check if the amount is greater than 0
        return p.getAmount() > 0;
    }

    // Method to check if payment type is valid
    public boolean isValidPaymentType(Payment p) {
        // Payment type should either be "debit" or "credit"
        return "debit".equals(p.getPaymentType()) || "credit".equals(p.getPaymentType());
    }

    // Method to confirm the payment
    public void confirmPayment(Payment p) {
        if (isValidAmount(p) && isValidPaymentType(p)) {
            p.setConfirmed( true);
        } else {
            throw new IllegalArgumentException("Invalid payment details.");
        }
    }
}

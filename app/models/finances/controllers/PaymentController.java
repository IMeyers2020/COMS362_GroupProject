package models.finances.controllers;

import models.finances.paymentServices.Payment;

/**
 * This class acts as a controller for handling various operations related to the Payment class.
 *      Provides functionality for managing and validating payments. 
 *      Ensures that payment details such as amount and payment type 
 *      meet the required criteria and provides methods to confirm and process payments.
*/
public class PaymentController {

    /**
     * Validates if the payment amount is valid.
     * @param p the Payment object to validate.
     * @return true if the payment amount is greater than 0; 
     *         false otherwise.
    */
    public boolean isValidAmount(Payment p) {
        return p.getAmount() > 0;
    }

     /**
     * Validates if the payment type is valid.
     * A valid payment type is either "debit" or "credit".
     * @param p the Payment object to validate.
     * @return true if the payment type is "debit" or "credit"; 
     *         false otherwise.
    */
    public boolean isValidPaymentType(Payment p) {
        return "debit".equals(p.getPaymentType()) || "credit".equals(p.getPaymentType());
    }

    /**
     * Confirms the payment after validating its details.
     * The payment is confirmed if:
     *      The payment amount is valid (greater than 0).
     *      The payment type is valid ("debit" or "credit").
     * If both conditions are met, the payment's status is set to confirmed.
     * @param p the Payment object to confirm.
     * @return true if the payment is successfully confirmed;
     *         false otherwise.
    */
    public boolean confirmPayment(Payment p) {
        if (isValidAmount(p) && isValidPaymentType(p)) {
            p.setConfirmed( true);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Handles payment processing logic. If the payment amount 
     * is not valid, it prints an error message and 
     * returns false. Otherwise, it processes the payment successfully.
     * @param payment the Payment object to process.
     * @return true if the payment is successfully processed; 
     *         false if the payment amount is invalid.
    */
    public boolean processPayment(Payment payment) {
        if (payment.getAmount() <= 0) {
            System.out.println("Invalid payment amount.");
            return false;
        }
        return true;
    }
}

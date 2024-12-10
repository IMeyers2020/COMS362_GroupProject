package models.finances.paymentServices;

/**
 * The Payment class represents a financial payment made by a student.
 * It stores details such as payment ID, amount, payment type (debit/credit), 
 * and whether the payment has been confirmed.
*/
public class Payment {
    private String paymentId;      // Unique identifier for the payment
    private double amount;         // Amount of the payment
    private String paymentType;    // Type of payment: "debit" or "credit"
    private boolean isConfirmed;   // Whether the payment has been confirmed

    /**
     * Default constructor for creating an instance of Payment with no initial values.
    */
    public Payment() {
        this.paymentId = null;
        this.amount = 0;
        this.paymentType = null;
        this.isConfirmed = false;
    }

    /**
     * Constructs a Payment object with specified payment details.
     * @param paymentId The unique identifier for the payment.
     * @param amount The amount of the payment.
     * @param paymentType The type of payment, either "debit" or "credit".
     * @param isConfirmed The confirmation status of the payment.
    */
    public Payment(String paymentId, double amount, String paymentType, boolean isConfirmed) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentType = paymentType;
        this.isConfirmed = isConfirmed;
    }

    /**
     * Gets the unique identifier for the payment.
     * @return The payment ID.
    */
    public String getPaymentId() {
        return paymentId;
    }

    /**
     * Sets the unique identifier for the payment.
     * @param paymentId The payment ID to set.
    */
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * Gets the amount of the payment.
     * @return The amount of the payment.
    */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the payment.
     * @param amount The amount to set for the payment.
    */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Gets the type of payment (either "debit" or "credit").
     * @return The payment type.
    */
    public String getPaymentType() {
        return paymentType;
    }

    /**
     * Sets the type of payment (either "debit" or "credit").
     * @param paymentType The payment type to set.
    */
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * Checks if the payment has been confirmed.
     * @return true if the payment is confirmed, false otherwise.
    */
    public boolean isConfirmed() {
        return isConfirmed;
    }

    /**
     * Sets the confirmation status of the payment.
     * @param confirmed The confirmation status to set.
    */
    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }
}

package app.models.finances.paymentServices;

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

    // Optional: Method to check if the payment amount is valid
    public boolean isValidAmount() {
        // Check if the amount is greater than 0
        return amount > 0;
    }

    // Method to check if payment type is valid
    public boolean isValidPaymentType() {
        // Payment type should either be "debit" or "credit"
        return "debit".equals(paymentType) || "credit".equals(paymentType);
    }

    // Method to confirm the payment
    public void confirmPayment() {
        if (isValidAmount() && isValidPaymentType()) {
            isConfirmed = true;
        } else {
            throw new IllegalArgumentException("Invalid payment details.");
        }
    }

    // toString method for debugging purposes
    @Override
    public String toString() {
        return "Payment [paymentId=" + paymentId + ", amount=" + amount + ", paymentType=" + paymentType + ", isConfirmed=" + isConfirmed + "]";
    }
}

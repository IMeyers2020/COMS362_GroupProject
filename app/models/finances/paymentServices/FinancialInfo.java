package models.finances.paymentServices;

public class FinancialInfo {
    private String cardType; // "debit" or "credit"
    private String cardNumber;
    private String billingAddress;

    // Constructor
    public FinancialInfo(String cardType, String cardNumber, String billingAddress) {
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.billingAddress = billingAddress;
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

    // Method to validate card number (simple validation)
    public boolean isCardNumberValid() {
        // A simple check for the card number length (assuming 16 digits)
        return cardNumber != null && cardNumber.length() == 16 && cardNumber.matches("\\d+");
    }

    // Method to display the financial info (for debugging purposes)
    @Override
    public String toString() {
        return "FinancialInfo [cardType=" + cardType + ", cardNumber=" + cardNumber + ", billingAddress=" + billingAddress + "]";
    }
}

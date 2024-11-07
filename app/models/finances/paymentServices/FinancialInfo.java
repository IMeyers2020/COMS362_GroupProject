package models.finances.paymentServices;

import src.DatabaseSupport;

public class FinancialInfo {
    private String cardType; // "debit" or "credit"
    private String cardNumber;
    private String billingAddress;

    public FinancialInfo() { }

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

    public boolean addStudentFinancialInfo(String cardType, String cardNumber, String billingAddress) {
        FinancialInfo fi = new FinancialInfo(cardType, cardNumber, billingAddress);
        DatabaseSupport db = new DatabaseSupport();

        return db.putFinancialInfo(fi);
    }

}

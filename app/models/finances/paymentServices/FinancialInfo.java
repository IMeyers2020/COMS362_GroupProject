package models.finances.paymentServices;

/**
 * The FinancialInfo class represents financial information associated 
 * with a student. This includes details such as card type, card number, 
 * billing address, and the associated student ID.
*/
public class FinancialInfo {
    private String cardType; 
    private String cardNumber;
    private String billingAddress;
    private String studentId;

    /**
     * Default constructor for creating an instance of FinancialInfo with no initial values.
    */
    public FinancialInfo() { }

    /**
     * Constructs a FinancialInfo object with specified details.
     * @param cardType The type of card (e.g., credit, debit).
     * @param cardNumber The card number associated with the financial information.
     * @param billingAddress The billing address linked to the card.
     * @param studentId The ID of the student associated with the financial information.
    */
    public FinancialInfo(String cardType, String cardNumber, String billingAddress, String studentId) {
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.billingAddress = billingAddress;
        this.studentId = studentId;
    }

    /**
     * Gets the type of card.
     * @return The card type (e.g., credit, debit).
    */    
    public String getCardType() {
        return cardType;
    }

    /**
     * Sets the type of card.
     * @param cardType The card type to set (e.g., credit, debit).
    */
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    /**
     * Gets the card number.
     * @return The card number.
    */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Sets the card number.
     * @param cardNumber The card number to set.
    */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }


    /**
     * Gets the billing address.
     * @return The billing address.
    */
    public String getBillingAddress() {
        return billingAddress;
    }

    /**
     * Sets the billing address.
     * @param billingAddress The billing address to set.
    */
    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    /**
     * Gets the student ID associated with the financial information.
     * @return The student ID.
    */
    public String getStudent() {
        return studentId;
    }

    /**
     * Sets the student ID associated with the financial information.
     * @param studentId The student ID to set.
    */
    public void setStudent(String studentId) {
        this.studentId = studentId;
    }
}

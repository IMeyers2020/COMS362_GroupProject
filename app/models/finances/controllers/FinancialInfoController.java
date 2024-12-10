package models.finances.controllers;

/**
 * The FinancialInfoController class provides functionality to validate
 * financial information, such as verifying the card type and card number format.
*/
public class FinancialInfoController {
    
    /**
     * Validates the provided card number and card type.
     * Card type must be either "credit" or "debit".
     * Card number must be exactly 16 digits long.
     * If both validations pass, the method returns true. If either validation 
     * fails, an appropriate message is displayed, and the method returns false.
     * @param cardNum the card number to validate. It must be 16 characters long.
     * @param cardType the card type to validate. It must be either "credit" or "debit".
     * @return true if both card type and card number are valid; 
     *         false otherwise.
     */
    public boolean isCardValid(String cardNum, String cardType) {
        // Simple validation, checks if card type is valid
        if (cardType.equalsIgnoreCase("credit") || cardType.equalsIgnoreCase("debit")) {
            System.out.println("Card type is valid.");
        } else {
            System.out.println("Invalid card type provided.");
            return false;
        }

        // Simple validation, checks if card number is of valid length
        if (cardNum.length() == 16) {
            System.out.println("Card number is valid.");
        } else {
            System.out.println("Invalid card number provided.");
            return false;
        }
        return true;
    }
}

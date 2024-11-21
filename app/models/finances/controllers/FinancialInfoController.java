package models.finances.controllers;

public class FinancialInfoController {
    public boolean isCardValid(String cardNum, String cardType) {
        if (cardType.equalsIgnoreCase("credit") || cardType.equalsIgnoreCase("debit")) {
            System.out.println("Card type is valid.");
        } else {
            System.out.println("Invalid card type provided.");
            return false;
        }

        // Simple validation example: checks if card number is of valid length
        if (cardNum.length() == 16) {
            System.out.println("Card number is valid.");
        } else {
            System.out.println("Invalid card number provided.");
            return false;
        }
        return true;
    }
}

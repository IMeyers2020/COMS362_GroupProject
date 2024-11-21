package models.finances.controllers;

public class FinancialInfoController {
    public boolean isCardNumberValid(String cardNum) {
        // Simple validation example: checks if card number is of valid length
        if (cardNum.length() == 16) {
            System.out.println("Financial information is valid.");
            return true;
        } else {
            System.out.println("Invalid financial information provided.");
            return false;
        }
    }
}

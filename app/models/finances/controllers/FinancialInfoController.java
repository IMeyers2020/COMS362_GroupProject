package models.finances.controllers;

import java.io.IOException;

import models.finances.paymentServices.FinancialInfo;

public class FinancialInfoController {
    public boolean addStudentFinancialInfo(String cardType, String cardNumber, String billingAddress) throws IOException{
        FinancialInfo fi = new FinancialInfo();

        return fi.addStudentFinancialInfo(cardType, cardNumber, billingAddress);
    }

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

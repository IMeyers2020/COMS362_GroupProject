package models.finances.offices;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import models.finances.paymentServices.Scholarship;
import models.finances.paymentServices.FinancialInfo;
import models.finances.paymentServices.Payment;
import models.general.people.student;
import models.general.people.studentLookup;
import src.DatabaseSupport;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AccountReceivableOffice {

    public boolean addStudentFinancialInfo(student student, FinancialInfo financialInfo) throws IOException{
        if (validateFinancialInfo(financialInfo)) {    
            student.setFinancialInfo(financialInfo);
            financialInfo.setStudent(student.getStudentId());
            DatabaseSupport db = new DatabaseSupport();
            db.updateStudent(student.getStudentId(), student);

            return db.putFinancialInfo(financialInfo);
        } else {
            System.out.println("Invalid financial information provided.");
            return false;
        }
    }

    public void printFinancialInfoReceipt(student student) {
        String studentName = student.getName();
        String studentId = student.getStudentId();
        String cardType = student.getFinancialInfo().getCardType();
        String cardNum = student.getFinancialInfo().getCardNumber();
        String billingAddress = student.getFinancialInfo().getBillingAddress();
        
        String receiptContent = "------ FINANCIAL INFORMATION RECEIPT ------\n" +
                            "Student Name: " + studentName + "\n" +
                            "Student ID: " + studentId + "\n" +
                            "Card Type: " + cardType + "\n" +
                            "Card Number: " + cardNum + "\n" +
                            "Billing Address: " + billingAddress + "\n" +
                            "-----------------------------------------------\n";
        // Define the file name with the student ID
        String baseFileName = "FI_receipt_" + studentId + ".txt";
        File file = new File("models/finances/receipts", baseFileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Write the receipt content to the file
            writer.write(receiptContent);
            System.out.println("Receipt has been written to " + baseFileName);
        } catch (IOException e) {
            System.err.println("Error writing the receipt to file: " + e.getMessage());
        }
        
    }

    public boolean editStudentFinancialInfo(student student, FinancialInfo financialInfo) throws IOException {
        DatabaseSupport db = new DatabaseSupport();
        financialInfo.setStudent(student.getStudentId());
        student.setFinancialInfo(financialInfo);
        db.updateStudent(student.getStudentId(), student);
        System.out.println("Financial information successfully updated.");
    
        return db.updateFinancialInfo(financialInfo);
    }

    public boolean addStudentPayment(student student, Payment payment, double scholarshipAmount) throws IOException{
        if (student.getFinancialInfo() == null) {
            System.out.println("Student does not have financial information on file.");
            return false;
        }
        
        if (!confirmPaymentDetails(payment)) {
            System.out.println("Payment details could not be confirmed.");
            return false;
        }

        if (processPayment(payment)) {
            student.setAccountBalance(student.getAccountBalance() - payment.getAmount() - scholarshipAmount);
            DatabaseSupport db = new DatabaseSupport();
            db.updateStudent(student.getStudentId(), student);

            System.out.println("Payment processed successfully. New balance: $" + student.getAccountBalance());
            return db.putPayment(payment);
        } else {
            System.out.println("Payment failed.");
            return false;
        }
    }

    public void printPaymentReceipt(Payment payment, student student) {
        // Get the current date
        LocalDate currentDate = LocalDate.now();
        
        // Format the date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = currentDate.format(formatter); 
        String studentName = student.getName();
        String studentId = student.getStudentId();
        double currentBalance = student.getAccountBalance();
        
        // Get payment details
        double paymentAmount = payment.getAmount();
        String paymentMethod = payment.getPaymentType();

        String receiptContent = "------ PAYMENT RECEIPT ------\n" +
                            "Date: " + formattedDate + "\n" +
                            "Student Name: " + studentName + "\n" +
                            "Student ID: " + studentId + "\n" +
                            "Payment Amount: $" + paymentAmount + "\n" +
                            "Payment Method: " + paymentMethod + "\n" +
                            "Current Account Balance: $" + currentBalance + "\n" +
                            "----------------------------\n";
        // Define the file name with the student ID
        String baseFileName = "payment_receipt_" + studentId + ".txt";
        File file = new File("models/finances/receipts", baseFileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Write the receipt content to the file
            writer.write(receiptContent);
            System.out.println("Receipt has been written to " + baseFileName);
        } catch (IOException e) {
            System.err.println("Error writing the receipt to file: " + e.getMessage());
        }
    }

    public boolean addStudentScholarship(student student, Scholarship scholarship) {
        student.setScholarships(scholarship);
        DatabaseSupport db = new DatabaseSupport();

        return db.updateStudent(student.getStudentId(), student);
    }


    private boolean validateFinancialInfo(FinancialInfo financialInfo) {
        // Add validation logic (e.g., regex for card number, check card type)
        return financialInfo.getCardNumber() != null && !financialInfo.getCardNumber().isEmpty();
    }

    private boolean confirmPaymentDetails(Payment payment) {
        // Simulate confirmation process with user
        payment.setConfirmed(true);
        return payment.isConfirmed();
    }

    private boolean processPayment(Payment payment) {
        // Simulate payment processing logic, handle alternate flows for declined payments
        if (payment.getAmount() <= 0) {
            System.out.println("Invalid payment amount.");
            return false;
        }

        // For simplicity, assume all payments go through unless explicitly declined
        return true;
    }
}
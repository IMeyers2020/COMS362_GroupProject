package models.finances.offices;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import models.finances.paymentServices.Scholarship;
import models.finances.controllers.PaymentController;
import models.finances.paymentServices.FinancialInfo;
import models.finances.paymentServices.Payment;
import models.general.people.student;
import src.DatabaseSupport;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Handles operations related to accounts receivable for students, including managing 
 * financial information, processing payments, and handling scholarships. This class 
 * coordinates between various objects such as financial information, payments, and 
 * scholarships, ensuring that students' financial transactions and obligations are properly 
 * tracked and processed.
*/
public class AccountReceivableOffice {

    /**
     * Adds financial information for a student after validating the data. 
     * Updates the database with the new financial information and links it to the student.
     * @param student        The student for whom the financial information is being added.
     * @param financialInfo  The financial information to associated with said student.
     * @return true if the financial information is successfully added, false otherwise.
     * @throws IOException if there is an error updating the database.
    */
    public boolean addStudentFinancialInfo(student student, FinancialInfo financialInfo) throws IOException{
        student.setFinancialInfo(financialInfo);
        financialInfo.setStudent(student.getStudentId());
        DatabaseSupport db = new DatabaseSupport();
        db.updateStudent(student.getStudentId(), student);

        return db.addFinancialInfo(financialInfo);
    }

    /**
     * Generates a receipt for a student's financial information and saves it to a file.
     * @param student The student whose financial information receipt will be generated.
    */
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

    /**
     * Updates a student's financial information and updates the database accordingly afterwards.
     * @param student        The student whose financial information is being updated.
     * @param financialInfo  The new financial information to associate with the student.
     * @return true if the update is successful, false otherwise.
     * @throws IOException if there is an error updating the database.
    */
    public boolean editStudentFinancialInfo(student student, FinancialInfo financialInfo) throws IOException {
        DatabaseSupport db = new DatabaseSupport();
        financialInfo.setStudent(student.getStudentId());
        student.setFinancialInfo(financialInfo);
        db.updateStudent(student.getStudentId(), student);
        System.out.println("Financial information successfully updated.");
    
        return db.updateFinancialInfo(financialInfo);
    }

    /**
     * Deletes a student's financial information from the system and updates the database.
     * @param student        The student whose financial information is being deleted.
     * @param financialInfo  The financial information to remove.
     * @return true if the deletion is successful, false otherwise.
     * @throws IOException if there is an error updating the database.
    */
    public boolean deleteStudentFinancialInfo(student student, FinancialInfo financialInfo) throws IOException {
        DatabaseSupport db = new DatabaseSupport();
        student.removeFinancialInfo();
        db.updateStudent(student.getStudentId(), student);
        System.out.println("Financial information successfully deleted.");

        return db.deleteFinancialInfo(financialInfo);
    }

    /**
     * Processes a payment for a student, applying any applicable scholarship amount, 
     * and updates the student's account balance in the database.
     * @param student           The student making the payment.
     * @param payment           The payment details, including amount and method.
     * @param scholarshipAmount The amount of scholarship to apply to the balance.
     * @return true if the payment is processed successfully, false otherwise.
     * @throws IOException if there is an error updating the database.
    */
    public boolean addStudentPayment(student student, Payment payment, double scholarshipAmount) throws IOException{
        PaymentController pc = new PaymentController();
        if (student.getFinancialInfo() == null) {
            System.out.println("Student does not have financial information on file.");
            return false;
        }
        
        if (!pc.confirmPayment(payment)) {
            System.out.println("Payment details could not be confirmed.");
            return false;
        }

        if (pc.processPayment(payment)) {
            student.setAccountBalance(student.getAccountBalance() - payment.getAmount() - scholarshipAmount);
            DatabaseSupport db = new DatabaseSupport();
            db.updateStudent(student.getStudentId(), student);

            return db.addPayment(payment);
        } else {
            return false;
        }
    }

    /**
     * Generates a receipt for a payment and saves it to a file.
     * @param payment The payment details to include in the receipt.
     * @param student The student associated with the payment.
    */
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

        // Create formatted recipe 
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

    /**
     * Adds a scholarship to the student's account and updates the database.
     * @param student the {@code student} object receiving the scholarship
     * @param scholarship the {@code Scholarship} object representing the scholarship details
     * @return {@code true} if the scholarship was successfully added, otherwise {@code false}
    */
    public boolean addStudentScholarship(student student, Scholarship scholarship) {
        student.setScholarships(scholarship);
        DatabaseSupport db = new DatabaseSupport();

        return db.updateStudent(student.getStudentId(), student);
    }

    /**
     * Removes a scholarship from the student's account and updates the database.
     * @param student the {@code student} object whose scholarship is to be removed
     * @param scholarship the {@code Scholarship} object representing the scholarship to be removed
     * @return {@code true} if the scholarship was successfully removed, otherwise {@code false}
    */
    public boolean deleteStudentScholarship(student student, Scholarship scholarship) {
        student.removeScholarship(scholarship) ;
        DatabaseSupport db = new DatabaseSupport();

        return db.updateStudent(student.getStudentId(), student);
    }
}
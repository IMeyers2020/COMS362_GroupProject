package models.finances.offices;

import java.io.IOException;

import models.finances.paymentServices.FinancialInfo;
import models.finances.paymentServices.Payment;
import models.general.people.student;
import models.general.people.studentLookup;
import src.DatabaseSupport;

public class AccountReceivableOffice {

    public boolean addStudentFinancialInfo(student student, FinancialInfo financialInfo) throws IOException{
        if (validateFinancialInfo(financialInfo)) {    
            student.setFinancialInfo(financialInfo);
            DatabaseSupport db = new DatabaseSupport();
            db.updateStudent(student.getStudentId(), student);

            return db.putFinancialInfo(financialInfo);
        } else {
            System.out.println("Invalid financial information provided.");
            return false;
        }
    }

    public boolean addStudentPayment(student student, Payment payment) throws IOException{
        if (student.getFinancialInfo() == null) {
            System.out.println("Student does not have financial information on file.");
            return false;
        }
        
        if (!confirmPaymentDetails(payment)) {
            System.out.println("Payment details could not be confirmed.");
            return false;
        }

        if (processPayment(payment)) {
            student.setAccountBalance(student.getAccountBalance() - payment.getAmount());
            DatabaseSupport db = new DatabaseSupport();
            db.updateStudent(student.getStudentId(), student);

            System.out.println("Payment processed successfully. New balance: $" + student.getAccountBalance());
            return db.putPayment(payment);
        } else {
            System.out.println("Payment failed.");
            return false;
        }
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
package models.finances.offices;

import models.finances.paymentServices.FinancialInfo;
import models.finances.paymentServices.Payment;
import models.general.people.student;

public class AccountReceivableOffice {

    public boolean addStudentFinancialInfo(student student, FinancialInfo financialInfo) {
        System.out.println(student.getName());
        if (validateFinancialInfo(financialInfo)) {
            student.setFinancialInfo(financialInfo);
            return true;
        } else {
            System.out.println("Invalid financial information provided.");
            return false;
        }
    }

    public boolean addStudentPayment(student student, Payment payment) {
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
            System.out.println("Payment processed successfully. New balance: $" + student.getAccountBalance());
            return true;
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
package app.src;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import app.models.finances.offices.AccountReceivableOffice;
import app.models.finances.paymentServices.FinancialInfo;
import app.models.finances.paymentServices.Payment;
import app.models.general.items.course;
import app.models.general.people.student;

public class UniversityProject {
    public static student exampleStudent;
    public static Scanner s;

    enum Departments {
        HUMAN_RESOURCES, ADMISSIONS, REGISTRATION, ACCOUNT_RECEIVABLE, EXIT
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    };

    public static Departments getDepartment() {
        System.out.println("What department are you a part of?");
        System.out.println("1. Human Resources");
        System.out.println("2. Admissions");
        System.out.println("3. Registration");
        System.out.println("4. Account Receivable");
        System.out.println("exit");

        String selection = s.nextLine();

        switch (selection) {
            case "1":
            case "Human Resources":
            case "HR":
            case "1. Human Resources":
            case "1.":
                clearScreen();
                return Departments.HUMAN_RESOURCES;
            case "2":
            case "Admissions":
            case "2. Admissions":
            case "2.":
                clearScreen();
                return Departments.ADMISSIONS;
            case "3":
            case "Registration":
            case "3. Registration":
            case "3.":
                clearScreen();
                return Departments.REGISTRATION;
            case "4":
            case "Account Receivable":
            case "4. Account Receivable":
            case "4.":
                clearScreen();
                return Departments.ACCOUNT_RECEIVABLE;
            case "exit":
                clearScreen();
                return Departments.EXIT;
            default:
                clearScreen();
                System.out.println("That is not a valid option.");
                return getDepartment();
        }
    }

    public static HashMap<String, course> initializeCourses() {
        HashMap<String, course> map = new HashMap<String, course>();
        course one = new course("one", 3);
        map.put("one", one);
        course two = new course("two", 3);
        map.put("two", two);
        course three = new course("three", 3);
        map.put("three", three);
        course four = new course("four", 3, Set.of("one", "two"));
        map.put("four", four);
        course five = new course("five", 3, Set.of("two", "three"));
        map.put("five", five);
        return map;
    } 

    public static void CourseRegistration() {
        HashMap<String, course> offeredCourses = initializeCourses();
        student exampleStudent = new student();
        System.out.println("What class would you like to register for?");
        System.out.println("one");
        System.out.println("two");
        System.out.println("three");
        System.out.println("four");
        System.out.println("five");
        
        String selection = null;
        course selected;

        System.out.println("Add or remove a course?");
        System.out.println("1. Add");
        System.out.println("2. Remove");

        if (s.hasNextLine())
          selection = s.nextLine();
        
        switch(selection) {
            case "1":
                System.out.println("What class would you like to register for?");
                System.out.println("one");
                System.out.println("two");
                System.out.println("three");
                System.out.println("four");
                System.out.println("five");
                if (s.hasNextLine())
                selection = s.nextLine();
                selected = offeredCourses.get(selection);
                clearScreen();
                if (selected != null && exampleStudent.addCourse(selected)){
                    System.out.println("Operation succeeded, " + selected.getName() + " has been added to the schedule.");
                }
                else {
                    System.out.println("Operation failed, " + selected.getName() + " has not been added to the schedule.");
                    System.out.println("Three potential causes: already taking course, don't have prereqs, or already have too many credit hours.");        
                }
                break;
            case "2":
                System.out.println("What class would you like to remove?");
                for (String course : exampleStudent.getCurrentCourses()) {
                    System.out.println(course);
                }
                if (s.hasNextLine())
                    selection = s.nextLine();
                selected = offeredCourses.get(selection);
                clearScreen();
                if (selected != null && exampleStudent.removeCourse(selected))
                    System.out.println("Operation succeeded, " + selected.getName() + " has been removed from the schedule.");
                else {
                    System.out.println("Operation failed, " + selected.getName() + " has not been removed from the schedule.");
                    System.out.println("This course isn't in the schedule.");
                }
                break;
            default:
                clearScreen();
                System.out.println("That is not a valid option.");
                return;
        }

        

    }

    public static void addFinancialInfo() {
        student student = new student(null, null, null, 0.0);
        FinancialInfo financialInfo = new FinancialInfo(null, null, null);

        String userIn = null;
        System.out.println("What is your student ID?");
        userIn = s.nextLine();
        student.setStudentId(userIn);

        System.out.println("What is your full name (first space last)?");
        userIn = s.nextLine();
        student.setName(userIn);

        System.out.println("What type of card will you be entering (credit or debit)?");
        userIn = s.nextLine();
        financialInfo.setCardType(userIn);

        System.out.println("What is your card number? (enter all 16 digits no space)");
        userIn = s.nextLine();
        financialInfo.setCardNumber(userIn);
        if (financialInfo.isCardNumberValid()) {
            System.out.println("Valid card number entered.");
        } else {
            System.out.println("Invalid card number entered.");
            financialInfo.setCardNumber(null);
            return;
        }

        System.out.println("What is your billing address? (Street, City, State, ZIP code)");
        userIn = s.nextLine();
        financialInfo.setBillingAddress(userIn);

        AccountReceivableOffice aro = new AccountReceivableOffice();
        boolean result = aro.addStudentFinancialInfo(student, financialInfo);

        if (result) {
            System.out.println("Financial information added successfully.");
        } else {
            System.out.println("Failed to add financial information.");
        }
    }

    public static HashMap<String, student> exampleStudents() {
        HashMap<String, student> map = new HashMap<String, student>();
        FinancialInfo fiOne = new FinancialInfo("credit","1234567890123456","1234 Street");
        student one = new student("12345", "Student One", fiOne, 100.0);
        map.put("one", one);
        FinancialInfo fiTwo = new FinancialInfo("credit","2345678901234561","2341 Street");
        student two = new student("23451","Student Two", fiTwo, 200.0);
        map.put("two", two);
        FinancialInfo fiThree = new FinancialInfo("credit","3456789012345612","3412 Street");
        student three = new student("34512", "Student Three", fiThree, 300.0);
        map.put("three", three);
        FinancialInfo fiFour = new FinancialInfo("credit","4567890123456123","4123 Street");
        student four = new student("45123", "Student Four", fiFour, 400.0);
        map.put("four", four);
        FinancialInfo fiFive = new FinancialInfo("credit","5678901234561234","5123 Street");
        student five = new student("51234", "Student Five", fiFive, 500.0);
        map.put("five", five);
        return map;
    } 

    public static void addPayment() {
        HashMap<String, student> exampleStudents = exampleStudents();
        student curStudent = null;
        boolean foundStudent = false;
        Payment payment = new Payment();
        String userIn = null;
        System.out.println("What is your student ID?");
        userIn = s.nextLine();
        for(String key: exampleStudents.keySet()) {
            if (userIn.equals(exampleStudents.get(key).getStudentId())) {
                curStudent = exampleStudents.get(key);
                foundStudent = true;
                System.out.println("You have an account balance of: " + exampleStudents.get(key).getAccountBalance());
                System.out.println("Would you like to use your saved payment information? (Y or N) " + exampleStudents.get(key).getFinancialInfo());
                userIn = s.nextLine();
                if (userIn.equals("Y")) {
                    System.out.println("How much would you like to pay today? (Enter in 000.00 format)");
                    userIn = s.nextLine();
                    double amountToPay = Double.parseDouble(userIn);
                    int randomNum = (int)(Math.random() * 99999) + 1;
                    String paymentID = String.valueOf(randomNum);
                    payment.setAmount(amountToPay);
                    payment.setPaymentId(paymentID);
                    payment.setPaymentType(exampleStudents.get(key).getFinancialInfo().getCardType());
                    System.out.println("Confirm the following information (Y or N):");
                    System.out.println("Amount To Pay: " + payment.getAmount());
                    System.out.println("Card Type: " + payment.getPaymentType() + " " + "Card Number: " + exampleStudents.get(key).getFinancialInfo().getCardNumber());
                    userIn = s.nextLine();
                    if (userIn.equals("Y")) {
                        payment.confirmPayment();
                        if(payment.isConfirmed()) {
                            System.out.println("Your Payment Has Been Confirmed!");
                            System.out.println();
                        }
                    } else {
                        System.out.println("Transaction cancelled.");
                        System.out.println();
                        break;
                    }
                } else {
                    System.out.println("Transaction cancelled.");
                    System.out.println();
                    break;
                }
            } 
        }

        if(!foundStudent) {
            System.out.println("You are not registered in the system.");
        }

        AccountReceivableOffice aro = new AccountReceivableOffice();
        boolean result = aro.addStudentPayment(curStudent, payment);

        if (result) {
            System.out.println();
        } else {
            System.out.println("Failed to Process Payment.");
        }
        
    }

    public static void PaymentService() {
        System.out.println("Would you like to do?");
        System.out.println("1. Add Payment");
        System.out.println("2. Add Financial Information");

        String selection = null;
        if (s.hasNextLine())
          selection = s.nextLine();
        
        if (selection.equals("1.")) {
            clearScreen();
            addPayment();
        } else if (selection.equals("2.")) {
            clearScreen();
            addFinancialInfo();
        }
        

    }

    public static void main(String[] args) {
        // Select a department. This is in place instead of any type of authentication.
        //  This allows us to take the user to the correct 'screen'.
        Departments selectedDepartment = null;
        exampleStudent = new student();
        s = new Scanner(System.in);

        while(selectedDepartment != Departments.EXIT) {
            selectedDepartment = getDepartment();

            switch(selectedDepartment) {
                case ADMISSIONS:
                    
                    break;
                case HUMAN_RESOURCES:

                    break;
                case REGISTRATION:
                    CourseRegistration();
                    break;
                case ACCOUNT_RECEIVABLE:
                    PaymentService();
                    break;
                case EXIT:
                    break;
                default:
                    System.out.println("ERROR: THIS DEPARTMENT HAS NOT BEEN IMPLEMENTED");
            }
        }
    }
}
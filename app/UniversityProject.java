
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import models.academics.CourseController;
import models.academics.RegistrationController;
import models.academics.administrativeDepartments.admissions.controllers.ApplicationController;
import models.academics.administrativeDepartments.humanResources.controllers.OfferController;
import models.finances.controllers.FinancialInfoController;
import models.finances.controllers.PaymentController;
import models.finances.offices.AccountReceivableOffice;
import models.finances.paymentServices.FinancialInfo;
import models.finances.paymentServices.Payment;
import models.general.items.Course;
import models.general.people.student;
import src.DatabaseSupport;

public class UniversityProject {
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

    public static HashMap<String, Course> initializeCourses() {
        HashMap<String, Course> map = new HashMap<String, Course>();
        Course one = new Course("one", 3);
        map.put("one", one);
        Course two = new Course("two", 3);
        map.put("two", two);
        Course three = new Course("three", 3);
        map.put("three", three);
        Course four = new Course("four", 3, Set.of("one", "two"));
        map.put("four", four);
        Course five = new Course("five", 3, Set.of("two", "three"));
        map.put("five", five);
        return map;
    }

    public static void AddStudent() {
        ApplicationController appController = new ApplicationController();

        System.out.println("Please enter student id:");
        String sid = s.nextLine();

        System.out.println("Please enter student name:");
        String name = s.nextLine();

        System.out.println("Please enter student address:");
        String address = s.nextLine();

        System.out.println("Please enter student SSN:");
        String ssn = s.nextLine();

        boolean result = appController.addStudent(sid, name, address, ssn);

        clearScreen();

        if(result) {
            System.out.println("Student successfully added!");
        } else {
            System.err.println("Failed to add student. Error: Student with that Id already exists");
        }

        s.nextLine();
    }

    public static void AdmissionsTasks() {

        System.out.println("What would you like to do?");
        System.out.println("1. Add Student");

        String selection = s.nextLine();

        switch (selection.trim()) {
            case "1":
            case "1.":
            case "1. Add Student":
            case "Add Student":
                clearScreen();
                AddStudent();
                break;
        
            default:
                break;
        }
    }

    public static void AddProfessor() {
        OfferController offerController = new OfferController();

        System.out.println("Please enter professor id:");
        String sid = s.nextLine();

        System.out.println("Please enter professor name:");
        String name = s.nextLine();

        System.out.println("Please enter professor address:");
        String address = s.nextLine();

        System.out.println("Please enter professor Area of Study:");
        String aos = s.nextLine();

        boolean result = offerController.addProfessor(sid, name, address, aos);

        clearScreen();

        if(result) {
            System.out.println("Student successfully added!");
        } else {
            System.err.println("Failed to add student. Error: Student with that Id already exists");
        }

        s.nextLine();
    }

    public static void HRTasks() {
        
        System.out.println("What would you like to do?");
        System.out.println("1. Add Professor");

        String selection = s.nextLine();

        switch (selection.trim()) {
            case "1":
            case "1.":
            case "1. Add Professor":
            case "Add Professor":
                clearScreen();
                AddProfessor();
                break;
        
            default:
                break;
        }
    }

    public static void CourseRegistration() {
        RegistrationController rc = new RegistrationController();
        CourseController cc = new CourseController();
        
        String sid = null;
        String selection = null;
        Course selected;
        HashMap<String, Course> offeredCourses = null;

        System.out.println("Type Student ID");

        if (s.hasNextLine()) {
            sid = s.nextLine();
            offeredCourses = cc.getAllCourses();
        }

        System.out.println("Add or remove a course?");
        System.out.println("1. Add");
        System.out.println("2. Remove");

        if (s.hasNextLine())
          selection = s.nextLine();
        
        switch(selection) {
            case "1":
                System.out.println("What class would you like to register for?");
                for(String key : offeredCourses.keySet()) {
                    System.out.println(key);
                }
                if (s.hasNextLine())
                    selection = s.nextLine();
                selected = offeredCourses.get(selection);
                System.out.println(selected.getCID());
                clearScreen();
                if (selected != null && rc.addCourse(sid, selected.getCID(), selected.getCreditHours())){
                    System.out.println("Operation succeeded, " + selected.getCID() + " has been added to the schedule.");
                }
                else {
                    System.out.println("Operation failed, " + selected.getCID() + " has not been added to the schedule.");
                    System.out.println("Three potential causes: already taking course, don't have prereqs, or already have too many credit hours.");        
                }
                s.nextLine();
                break;
            case "2":
                System.out.println("What class would you like to remove?");
                for (Course course : DatabaseSupport.getCoursesForStudent(sid).values()) {
                    System.out.println(course.getCID());
                }
                if (s.hasNextLine())
                    selection = s.nextLine();
                selected = offeredCourses.get(selection);
                clearScreen();
                if (selected != null && (new RegistrationController()).removeCourse(sid, selected))
                    System.out.println("Operation succeeded, " + selected.getCID() + " has been removed from the schedule.");
                else {
                    System.out.println("Operation failed, a course has not been removed from the schedule.");
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
        student student = new student(null, null, null, null, null, 0.0);
        FinancialInfo financialInfo = new FinancialInfo(null, null, null);
        FinancialInfoController fiController = new FinancialInfoController();

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
        if (fiController.isCardNumberValid(financialInfo)) {
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
        student one = new student("12345", "Student One", "111 1st St.", "111-11-1111", fiOne, 100.0);
        map.put("one", one);
        FinancialInfo fiTwo = new FinancialInfo("credit","2345678901234561","2341 Street");
        student two = new student("23451","Student Two", "111 1st St.", "111-11-1111", fiTwo, 200.0);
        map.put("two", two);
        FinancialInfo fiThree = new FinancialInfo("credit","3456789012345612","3412 Street");
        student three = new student("34512", "Student Three", "111 1st St.", "111-11-1111", fiThree, 300.0);
        map.put("three", three);
        FinancialInfo fiFour = new FinancialInfo("credit","4567890123456123","4123 Street");
        student four = new student("45123", "Student Four", "111 1st St.", "111-11-1111", fiFour, 400.0);
        map.put("four", four);
        FinancialInfo fiFive = new FinancialInfo("credit","5678901234561234","5123 Street");
        student five = new student("51234", "Student Five", "111 1st St.", "111-11-1111", fiFive, 500.0);
        map.put("five", five);
        return map;
    } 

    public static void addPayment() {
        HashMap<String, student> exampleStudents = exampleStudents();
        student curStudent = null;
        boolean foundStudent = false;
        Payment payment = new Payment();
        PaymentController pc = new PaymentController();
        String userIn = null;
        System.out.println("What is your student ID?");
        userIn = s.nextLine();
        for(String key: exampleStudents.keySet()) {
            if (userIn.equals(exampleStudents.get(key).getStudentId())) {
                curStudent = exampleStudents.get(key);
                foundStudent = true;
                System.out.println("You have an account balance of: " + exampleStudents.get(key).getAccountBalance());
                System.out.println("Would you like to use your saved payment information? (Y or N)");
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
                        pc.confirmPayment(payment);
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
        s = new Scanner(System.in);

        while(selectedDepartment != Departments.EXIT) {
            selectedDepartment = getDepartment();

            switch(selectedDepartment) {
                case ADMISSIONS:
                    AdmissionsTasks();
                    break;
                case HUMAN_RESOURCES:
                    HRTasks();
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
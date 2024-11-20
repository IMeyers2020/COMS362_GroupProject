
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import models.academics.CourseController;
import models.academics.ProfessorController;
import models.academics.RegistrationController;
import models.academics.StudentController;
import models.academics.administrativeDepartments.admissions.controllers.ApplicationController;
import models.academics.administrativeDepartments.humanResources.controllers.OfferController;
import models.dorms.DormController;
import models.finances.controllers.FinancialInfoController;
import models.finances.controllers.PaymentController;
import models.finances.offices.AccountReceivableOffice;
import models.finances.paymentServices.FinancialInfo;
import models.finances.paymentServices.Payment;
import models.general.items.Course;
import models.general.people.professor;
import models.general.people.student;
import models.general.people.studentLookup;
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

    public static void PrintStudents(ArrayList<studentLookup> students) {
        for (studentLookup student : students) {
            System.out.println(student.value.getName() + ": " + student.value.getStudentId() + (student.value.getDormId() != null ? ": " + student.value.getDormId()  : ""));
        }
    }

    public static void PrintProfessors(HashMap<String, professor> profs) {
        for (professor prof : profs.values()) {
            System.out.println(prof.getName() + ": " + prof.getPID());
        }
    }

    public static void AddStudent(ApplicationController ac) {
        System.out.println("Please enter student id:");
        String sid = s.nextLine();

        System.out.println("Please enter student name:");
        String name = s.nextLine();

        System.out.println("Please enter student address:");
        String address = s.nextLine();

        System.out.println("Please enter student SSN:");
        String ssn = s.nextLine();

        boolean result = ac.addStudent(sid, name, address, ssn);

        clearScreen();

        if(result) {
            System.out.println("Student successfully added!");
        } else {
            System.err.println("Failed to add student. Error: Student with that Id already exists");
        }

        s.nextLine();
    }

    public static void GetStudents(StudentController sc) {
        ArrayList<studentLookup> result = sc.getAllStudents();

        clearScreen();

        if(result.size() > 0) {
            PrintStudents(result);
        } else {
            System.err.println("No students in existing database");
        }

        s.nextLine();
        clearScreen();
    }

    public static void AddStudentToDorm(DormController dc) {
        System.out.println("Please enter student id:");
        String sid = s.nextLine();

        System.out.println("Please enter dorm id:");
        String did = s.nextLine();

        boolean result = dc.addDorm(did, sid);

        clearScreen();

        if(result) {
            System.out.println("Student successfully added to Dorm!");
        } else {
            System.err.println("Failed to add student to Dorm.");
        }

        s.nextLine();
    }

    public static void CreateNewDorm(DormController dc) {
        System.out.println("Please enter dorm id:");
        String did = s.nextLine();

        boolean result = dc.newDorm(did);

        clearScreen();

        if(result) {
            System.out.println("Dorm was successfully created!");
        } else {
            System.err.println("Failed to list new dorm");
        }

        s.nextLine();
    }

    public static void RemoveDorm(DormController dc) {
        System.out.println("Please enter dorm id:");
        String did = s.nextLine();

        boolean result = dc.removeDorm(did);

        if(result) {
            System.out.println("Dorm was successfully removed!");
        } else {
            System.err.println("Failed to remove dorm");
        }

        s.nextLine();
    }

    public static void GetProfessors(ProfessorController pc) {
        HashMap<String, professor> result = pc.getAllProfessors();

        clearScreen();

        if(result.size() > 0) {
            PrintProfessors(result);
        } else {
            System.err.println("No professors in existing database");
        }

        s.nextLine();
        clearScreen();
    }

    public static void AdmissionsTasks(StudentController sc, ApplicationController ac, DormController dc) {

        System.out.println("What would you like to do?");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Add Student To Dorm");
        System.out.println("4. List a New Dorm");
        System.out.println("5. Unlist a Dorm");

        String selection = s.nextLine();

        switch (selection.trim()) {
            case "1":
            case "1.":
            case "1. Add Student":
            case "Add Student":
                clearScreen();
                AddStudent(ac);
                break;

            case "2":
            case "2.":
            case "2. View All Students":
            case "View All Students":
                clearScreen();
                GetStudents(sc);
                break;
                
            case "3":
            case "3.":
            case "3. Add Student To Dorm":
            case "Add Student To Dorm":
                clearScreen();
                AddStudentToDorm(dc);
                break;
            case "4":
            case "4.":
            case "4. List a New Dorm":
            case "List a New Dorm":
                clearScreen();
                CreateNewDorm(dc);
                break;
            case "5":
            case "5.":
            case "5. Unlist a Dorm":
            case "Unlist a Dorm":
                clearScreen();
                RemoveDorm(dc);
                break;
        
            default:
                break;
        }
    }

    public static void AddProfessor(OfferController oc) {
        System.out.println("Please enter professor id:");
        String sid = s.nextLine();

        System.out.println("Please enter professor name:");
        String name = s.nextLine();

        System.out.println("Please enter professor address:");
        String address = s.nextLine();

        System.out.println("Please enter professor Area of Study:");
        String aos = s.nextLine();

        boolean result = oc.addProfessor(sid, name, address, aos);

        clearScreen();

        if(result) {
            System.out.println("Professor successfully added!");
        } else {
            System.err.println("Failed to add professer. Error: Professor with that Id already exists");
        }

        s.nextLine();
    }

    public static void HRTasks(OfferController oc, ProfessorController pc) {
        
        System.out.println("What would you like to do?");
        System.out.println("1. Add Professor");
        System.out.println("2. View All Professors");

        String selection = s.nextLine();

        switch (selection.trim()) {
            case "1":
            case "1.":
            case "1. Add Professor":
            case "Add Professor":
                clearScreen();
                AddProfessor(oc);
                break;

            case "2":
            case "2.":
            case "2. View All Professors":
            case "View All Professors":
                clearScreen();
                GetProfessors(pc);
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
                if (selected != null && rc.addCourse(selected, sid, selected.getCreditHours())){
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

    public static void addFinancialInfo(StudentController sc) {
        FinancialInfoController fc = new FinancialInfoController();

        String userIn = null;
        System.out.println("Enter the student's ID:");
        userIn = s.nextLine();

        studentLookup currStud = sc.getStudent(userIn);

        System.out.println("What type of card will you be entering (credit or debit)?");
        String cardType = s.nextLine();

        System.out.println("What is the card number? (enter all 16 digits no space)");
        String cardNum = s.nextLine();

        if (fc.isCardNumberValid(cardNum)) {
            System.out.println("Valid card number entered.");
        } else {
            System.out.println("Invalid card number entered.");
            return;
        }

        System.out.println("What is the billing address? (Street, City, State, ZIP code)");
        String billingAddress = s.nextLine();

        FinancialInfo financialInfo = new FinancialInfo(cardType, cardNum, billingAddress);

        AccountReceivableOffice aro = new AccountReceivableOffice();
        boolean result = aro.addStudentFinancialInfo(currStud, financialInfo);

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
        System.out.println("Enter the student's ID: ");
        userIn = s.nextLine();
        for(String key: exampleStudents.keySet()) {
            if (userIn.equals(exampleStudents.get(key).getStudentId())) {
                curStudent = exampleStudents.get(key);
                foundStudent = true;
                System.out.println("Account balance of: " + exampleStudents.get(key).getAccountBalance());
                System.out.println("Use saved payment information? (Y or N)");
                userIn = s.nextLine();
                if (userIn.equals("Y")) {
                    System.out.println("Enter payment amount:  (Enter in 000.00 format)");
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

    public static void PaymentService(StudentController sc) {
        System.out.println("Would you like to do?");
        System.out.println("1. Add Payment");
        System.out.println("2. Add Financial Information");

        String selection = null;
        if (s.hasNextLine())
          selection = s.nextLine();

        switch(selection) {
            case "1":
            case "1. Add Payment":
            case "1.":
            case "Add Payment":
                clearScreen();
                addPayment();
                break;
            case "2":
            case "2. Add Financial Information":
            case "2.":
            case "Add Financial Information":
                clearScreen();
                addFinancialInfo(sc);
                break;
        }
    }

    public static void main(String[] args) {
        // Select a department. This is in place instead of any type of authentication.
        //  This allows us to take the user to the correct 'screen'.
        Departments selectedDepartment = null;
        DatabaseSupport db = new DatabaseSupport();
        StudentController studentController = new StudentController(db);
        ApplicationController appController = new ApplicationController(db);
        OfferController offerController = new OfferController(db);
        ProfessorController professorController = new ProfessorController(db);
        DormController dormController = new DormController(db, studentController);

        s = new Scanner(System.in);

        while(selectedDepartment != Departments.EXIT) {
            selectedDepartment = getDepartment();

            switch(selectedDepartment) {
                case ADMISSIONS:
                    AdmissionsTasks(studentController, appController, dormController);
                    break;
                case HUMAN_RESOURCES:
                    HRTasks(offerController, professorController);
                    break;
                case REGISTRATION:
                    CourseRegistration();
                    break;
                case ACCOUNT_RECEIVABLE:
                    PaymentService(studentController);
                    break;
                case EXIT:
                    break;
                default:
                    System.out.println("ERROR: THIS DEPARTMENT HAS NOT BEEN IMPLEMENTED");
            }
        }
        // DormController dormController = new DormController(db);
        // DormManager dormManager = new DormManager(db);
        // System.out.println("Adding dorms...");
        // System.out.println("Dorm A added: " + dormManager.addDorm("DormA")); // Expect true
        // System.out.println("Dorm B added: " + dormManager.addDorm("DormB")); // Expect true
        // System.out.println("Dorm A added again: " + dormManager.addDorm("DormA")); // Expect false



        // student student1 = new student("student1", "John Doe", null, null, new FinancialInfo(), 600.0);
        // System.out.println("Adding student to Dorm A...");
        // System.out.println("Student added to Dorm A: " + dormController.addDorm("DormA", student1)); // Expect true

        // // Check student details after being added
        // System.out.println("Student dorm ID: " + student1.getDormId()); // Expect "DormA"
        // System.out.println("Student remaining balance: " + student1.getAccountBalance()); // Expect 100.0 after paying 500

        // // Remove the student from the dorm
        // System.out.println("Removing student from Dorm A...");
        // System.out.println("Student removed from Dorm A: " + dormController.removeDorm("DormA", student1)); // Expect true
        // System.out.println("Student dorm ID after removal: " + student1.getDormId()); // Expect null or empty string

        // // Remove dorm and check if it still exists
        // System.out.println("Removing Dorm A...");
        // System.out.println("Dorm A removed: " + dormManager.removeDorm("DormA")); // Expect true
        // System.out.println("Attempting to add a student to removed Dorm A...");
        // System.out.println("Student added to Dorm A after removal: " + dormController.addDorm("DormA", student1)); // Expect false

        // // Test edge cases
        // System.out.println("Attempting to add student with insufficient funds...");
    }
}
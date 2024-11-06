package app.src;
import java.util.Scanner;

public class UniversityProject {

    enum Departments {
        HUMAN_RESOURCES, ADMISSIONS, REGISTRATION
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    };

    public static Departments getDepartment() {
        Scanner s = new Scanner(System.in);

        System.out.println("What department are you a part of?");
        System.out.println("1. Human Resources");
        System.out.println("2. Admissions");
        System.out.println("3. Registration");

        String selection = s.nextLine();

        s.close();

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
            default:
                clearScreen();
                System.out.println("That is not a valid option.");
                return getDepartment();
        }
    }

    public static void main(String[] args) {
        // Select a department. This is in place instead of any type of authentication.
        //  This allows us to take the user to the correct 'screen'.
        Departments selectedDepartment = getDepartment();

        switch(selectedDepartment) {
            case ADMISSIONS:
                
                break;
            case HUMAN_RESOURCES:

                break;
            case REGISTRATION:

                break;
            default:
                System.out.println("ERROR: THIS DEPARTMENT HAS NOT BEEN IMPLEMENTED");
        }
    }
}
package app.src;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import app.models.general.items.course;
import app.models.general.people.student;

public class UniversityProject {
    public static student exampleStudent;
    public static Scanner s;

    enum Departments {
        HUMAN_RESOURCES, ADMISSIONS, REGISTRATION, EXIT
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
                case EXIT:
                    break;
                default:
                    System.out.println("ERROR: THIS DEPARTMENT HAS NOT BEEN IMPLEMENTED");
            }
        }
    }
}
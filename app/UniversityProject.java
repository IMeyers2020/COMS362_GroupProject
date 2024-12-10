
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import models.academics.administrativeDepartments.admissions.controllers.ApplicationController;
import models.academics.administrativeDepartments.admissions.controllers.StudentApplication;
import models.academics.CourseController;
import models.academics.MajorController;
import models.academics.ProfessorController;
import models.academics.RegistrationController;
import models.academics.StudentController;
import models.academics.administrativeDepartments.humanResources.controllers.OfferController;
import models.dorms.DormController;
import models.finances.paymentServices.Scholarship;
import models.finances.controllers.ScholarshipController;
import models.finances.controllers.FinancialInfoController;
import models.finances.controllers.PaymentController;
import models.finances.offices.AccountReceivableOffice;
import models.finances.paymentServices.FinancialInfo;
import models.finances.paymentServices.Payment;
import models.general.items.Course;
import models.general.items.Major;
import models.general.items.courseLookup;
import models.general.items.courseSectionLookup;
import models.general.items.scheduleLookup;
import models.general.items.selectedCourse;
import models.general.items.selectedCoursesLookup;
import models.general.people.courseSection;
import models.general.people.genericPerson;
import models.general.people.professorLookup;
import models.general.people.student;
import models.general.people.studentLookup;
import src.DatabaseSupport;
import src.constants.DAYS;
import src.constants.TIMES;

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

    public static void PrintStudents(ArrayList<studentLookup> students) {
        for (studentLookup student : students) {
            System.out.println(student.value.getName() + ": " + student.value.getStudentId() + (student.value.getDormId() != null ? ": " + student.value.getDormId()  : ""));
        }
    }

    public static void PrintProfessors(ArrayList<professorLookup> profs) {
        for (professorLookup prof : profs) {
            System.out.println(prof.value.getName() + ": " + prof.value.getPID());
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
        ArrayList<professorLookup> result = pc.getAllProfessors();

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

    public static ArrayList<genericPerson> AddReferences() {
        ArrayList<genericPerson> retVal = new ArrayList<>();

        boolean con = false;
        int count = 0;

        while(con == false && count < 3) {
            System.out.println("Please enter reference name:");
            String refName = s.nextLine();
    
            System.out.println("Please enter reference Phone Number:");
            String refNum = s.nextLine();

            System.out.println("Would you like to enter another reference? (Y/N)");
            String refCont = s.nextLine();
            refCont = refCont.trim().toLowerCase();

            retVal.add(new genericPerson(refName, refNum));
            con = !(refCont.equals("y") || refCont.equals("yes"));
            count++;
        }

        return retVal;
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

        ArrayList<genericPerson> refs = AddReferences();

        boolean result = oc.addProfessor(sid, name, address, aos, refs);

        clearScreen();

        if(result) {
            System.out.println("Professor successfully added!");
        } else {
            System.err.println("Failed to add professer. Error: Professor with that Id already exists");
        }

        s.nextLine();
    }

    public static Set<String> GetPreRequisites(CourseController cc) {
        ArrayList<courseLookup> validCourses = cc.getAllValidCourses();
        ArrayList<String> nonSetRequisites = new ArrayList<>();
        Set<String> retSet = Set.of();
        boolean cont = true;

        if(validCourses.size() == 0) {
            System.out.println("There are currently no courses available to be used as a pre-requisite.");
            System.out.println("Ensure that you have added the course and assigned a professor to it before trying to use it as a pre-requisite");

            return retSet;
        }

        while(cont) {
            System.out.println("Select a course to be a pre-requisite:");
            int idx = 1;
            for (courseLookup crs : validCourses) {
                System.out.println(idx + ": " + crs.key);
                idx++;
            }
            int selectedIdx = Integer.parseInt(s.nextLine()) - 1;
    
            courseLookup selectedCrs = validCourses.get(selectedIdx);
            nonSetRequisites.add(selectedCrs.key);

            System.out.println("Would you like to add another pre-requisite? (Y/N)");
            String addPreReq = s.nextLine();
            if(!addPreReq.toLowerCase().equals("y")) {
                cont = false;
            }
        }

        retSet = new HashSet<String>(nonSetRequisites);
        return retSet;
    }

    public static ArrayList<String> GetSectionDays(ArrayList<DAYS> days) {
        ArrayList<String> crsDays = new ArrayList<>();
        boolean cont = true;

        while(cont) {
            System.out.println("Please select a day for this class to take place");
            int idx = 1;
            for(DAYS d : days) {
                System.out.println(idx + ": " + d.label);
                idx++;
            }
            int selectedIdx = Integer.parseInt(s.nextLine()) - 1;
    
            crsDays.add(days.get(selectedIdx).label);
            System.out.println("Would you like to add another class day? (Y/N)");
            String addDay = s.nextLine();
            if(!addDay.toLowerCase().equals("y")) {
                clearScreen();
                cont = false;
            } else {
                days.remove(selectedIdx);
            }
        }

        return crsDays;
    }

    public static String GetSectionTime() {
        System.out.println("Please select a time for this class to take place");

        int idx = 1;
        for(TIMES t : TIMES.values()) {
            System.out.println(idx + ": " + t.label);
            idx++;
        }
        int selectedIdx = Integer.parseInt(s.nextLine());

        if(selectedIdx > TIMES.values().length) {
            System.out.println("INVALID TIME! Please enter the number next to the time you would like to select");
            return GetSectionTime();
        } else {
            return TIMES.values()[selectedIdx - 1].label;
        }
    }

    public static ArrayList<courseSection> GetSections(CourseController cc) {
        ArrayList<courseSection> crsSections = new ArrayList<>();
        boolean cont = true;

        while(cont) {
            ArrayList<DAYS> allDays = new ArrayList<DAYS>(Arrays.asList(DAYS.values()));
            ArrayList<String> courseDays = GetSectionDays(allDays);
            String courseTime = GetSectionTime();

            System.out.println("Please enter a sectionId for this class");

            courseSection newSection = new courseSection(stringifySection(courseDays, courseTime), courseDays, courseTime);
            crsSections.add(newSection);

            System.out.println("Would you like to add another section? (Y/N)");
            String addSect = s.nextLine();
            if(!addSect.toLowerCase().equals("y")) {
                clearScreen();
                cont = false;
            }
        }

        return crsSections;
    }

    public static void CreateCourse(CourseController cc) {
        System.out.println("Please enter Course Id:");
        String cid = s.nextLine();

        System.out.println("Please enter Credit Hours:");
        int creditHrs = Integer.parseInt(s.nextLine());

        System.out.println("Would you like to add pre-requisites? (Y/N)");
        String addPreReq = s.nextLine();

        Set<String> preReqs = Set.of();
        if(addPreReq.toLowerCase().equals("y")) {
            preReqs = GetPreRequisites(cc);
        }

        System.out.println("Would you like to configure course sections? (Y/N)");
        String addSections = s.nextLine();

        ArrayList<courseSection> crsSections = new ArrayList<>();
        if(addSections.toLowerCase().equals("y")) {
            crsSections = GetSections(cc);
        }

        Course newCourse = new Course(cid, creditHrs, preReqs);
        cc.AddSectionsToCourse(newCourse, crsSections);

        boolean result = cc.addCourse(newCourse);

        clearScreen();

        if(result) {
            System.out.println("Course successfully added!");
        } else {
            System.err.println("Failed to add Course.");
        }

        s.nextLine();
    }

    private static void printValidatedCourses(CourseController cc, ProfessorController pc) {
        ArrayList<courseLookup> validCourses = cc.getAllValidCourses();

        for(courseLookup crs : validCourses) {

            professorLookup prof = pc.getProfessor(crs.value.GetProfessorId());
            System.out.println(crs.key + " - " + prof.value.getName());
            if(crs.value.getCourseSectionIds().size() > 0) {
                for(String sec : crs.value.getCourseSectionIds()) {
                    System.out.println("\t" + sec);
                }
                System.out.println();
            }
        }
        s.nextLine();
    }

    private static String stringifySection(ArrayList<String> days, String time) {
        String retString = "";

        for (String d : days) {
            retString = retString + d;
        }
        retString = retString + " - " + time;

        return retString;
    }

    private static void printAllCourses(CourseController cc) {
        ArrayList<courseLookup> allCourses = cc.getAllCourses();

        for(courseLookup crs : allCourses) {
            System.out.println(crs.key);
            if(crs.value.getCourseSectionIds().size() > 0) {
                for(String sec : crs.value.getCourseSectionIds()) {
                    System.out.println("\t" + sec);
                }
                System.out.println();
            }
        }
        s.nextLine();
    }

    public static void GetCourses(CourseController cc, ProfessorController pc) {
        System.out.println("Which Course Types would you like to view?");
        System.out.println("1. Validated Courses Only");
        System.out.println("2. All Courses");

        String selection = s.nextLine();
        switch(selection) {
            case "1":
            case "1.":
            case "1. Validated Courses Only":
            case "Validated Courses Only":
                clearScreen();
                printValidatedCourses(cc, pc);
                break;

            case "2":
            case "2.":
            case "2. All Courses":
            case "All Courses":
                clearScreen();
                printAllCourses(cc);
                break;

            default:
                clearScreen();
                GetCourses(cc, pc);
        }
    }

    public static void UpdateCourseWithProfessor(ProfessorController pc, CourseController cc) {
        System.out.println("Please enter the ID of the Professor to assign a class to: ");
        String profId = s.nextLine();

        ArrayList<courseLookup> allCourses = cc.getAllCourses();

        System.out.println("Please Select a Course to Assign this Professor to: ");
        int idx = 1;
        for(courseLookup crs : allCourses) {
            System.out.println(idx + ": " + crs.key);
            idx++;
        }
        int selectedIdx = Integer.parseInt(s.nextLine());

        courseLookup selectedCourse = allCourses.get(selectedIdx - 1);

        cc.addProfessorToCourse(profId, selectedCourse);
    }

    /**
     * Prompts the user to enter the student ID of the student that is under threat of explusion.
     * Prompts the user to answer questions pertaining to the explusion process.
     * Prompts the user to write a finalized reason for the explusion of student.
     * Uses StudentController to add the student to the expelledDB and remove the student from studentDB
     * @param sc The StudentController instance to interact with the student account.
     */
    public static void ExpelStudent(StudentController sc) {
        System.out.println("Type student id of student:");
        String sid = s.nextLine();
        System.out.println("Has the student met with the Student Conduct Administrator? (Y/N)");
        String response = s.nextLine();
        if (response.equals("N")) {
            System.out.println("Allow the student to meet with the administrator to go over the student's conduct.");
            return;
        }
        System.out.println("Has an independent professional assessment been conducted? Not required. (Y/N)");
        response = s.nextLine();
        System.out.println("Has the University Initiated Separation Hearing been conducted? (Y/N)");
        response = s.nextLine();
        if (response.equals("N")) {
            System.out.println("A hearing is required to allow the student a chance for rebuttal.");
            return;
        }
        System.out.println("Write finalized reason for explusion and press enter.");
        String reason = s.nextLine();
        System.out.println("Confirm explusion of student " + sid + ". (Y/N)");
        response = s.nextLine();
        if (!response.equals("Y")) {
            return;
        }
        if (sc.expelStudent(sid, reason))
            System.out.println("Operation successful.");
        else
            System.out.println("Operation failed.");
    }

    /**
     * Prompts the user to enter the student ID of the student that is having expelled status removed.
     * Prompts the user to answer questions pertaining to the re-initiation process.
     * Uses StudentController to add the student to the studentDB and remove the student from expelledDB
     * @param sc The StudentController instance to interact with the student account.
     */
    public static void UnexpelStudent(StudentController sc) {
        System.out.println("Type student id of student:");
        String sid = s.nextLine();
        System.out.println("Has the student met with the Separation Consultation Committee? (Y/N)");
        String response = s.nextLine();
        if (response.equals("N")) {
            System.out.println("Inform the student that a meeting is necessary.");
            return;
        }
        System.out.println("Has the student provided documentation from licensed treating professional? (Y/N)");
        response = s.nextLine();
        if (response.equals("N")) {
            System.out.println("Some form of documentation is required.");
            return;
        }
        System.out.println("Confirm removal of explusion of student " + sid + ". (Y/N)");
        response = s.nextLine();
        if (!response.equals("Y")) {
            return;
        }
        if (sc.unexpelStudent(sid))
            System.out.println("Operation successful.");
        else
            System.out.println("Operation failed.");
    }

    public static void HRTasks(OfferController oc, ProfessorController pc, CourseController cc, StudentController sc) {
        System.out.println("What would you like to do?");
        System.out.println("1. Add Professor");
        System.out.println("2. View All Professors");
        System.out.println("3. Create Courses");
        System.out.println("4. View All Courses");
        System.out.println("5. Assign Professor to Course");
        System.out.println("6. Expel Student");
        System.out.println("7. Reverse Explusion of Student");

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
            case "3":
            case "3.":
            case "3. Create Course":
            case "Create Course":
                clearScreen();
                CreateCourse(cc);
                break;

            case "4":
            case "4.":
            case "4. View All Courses":
            case "View All Courses":
                clearScreen();
                GetCourses(cc, pc);
                break;
            
            case "5":
            case "5.":
            case "5. Assign Professor to Course":
            case "Assign Professor to Course":
                clearScreen();
                UpdateCourseWithProfessor(pc, cc);
                break;
        
            case "6":
            case "6.":
            case "6. Expel Student":
            case "Expel Student":
                clearScreen();
                ExpelStudent(sc);
                break;
            
            case "7":
            case "7.":
            case "7. Reverse Explusion of Student":
            case "Reverse Explusion of Student":
                clearScreen();
                UnexpelStudent(sc);
                break;
            default:
                break;
        }
    }

    /**
     * Handles all RegistrationOffice functionality.
     * @param rc    Handles registration database requests
     * @param cc    Handles course database requests
     * @param mc    Handles major database requests
     * @param sc    Handles student database requests
     */
    public static void CourseRegistration(RegistrationController rc, CourseController cc, MajorController mc, StudentController sc) {
        
        String sid = null;
        String selection = null;
        String address = null;
        Course selectedCourse;
        Major selectedMajor;
        ArrayList<courseLookup> offeredCourses = null;
        HashMap<String, Major> offeredMajors = null;

        System.out.println("Type Student ID");

        if (s.hasNextLine()) {
            sid = s.nextLine();
            offeredCourses = cc.getAllValidCourses();
            offeredMajors = mc.getAllMajors();
        }

        System.out.println("View major(s) and registered course(s), Add/Remove a course, or Add/Remove a major?");
        System.out.println("1. View");
        System.out.println("2. Add Course");
        System.out.println("3. Update Course Section");
        System.out.println("4. Remove Course");
        System.out.println("5. Add Major");
        System.out.println("6. Remove Major");
        System.out.println("7. Graduate Student");

        if (s.hasNextLine())
          selection = s.nextLine();
        
        switch(selection) {
            //View majors and courses
            case "1":
                System.out.println("Student " + sid + "'s major(s):");
                int index = 0;
                ArrayList<String> majors = rc.viewRegisteredMajors(sid);
                int end = majors.size() - 1;
                for (String major : majors) {
                    if(major.trim().length() == 0) {
                        continue;
                    } else {
                        System.out.print(major);
                        if(index != end) {
                            System.out.println(",");
                        } else {
                            System.out.println("");
                        }
                    }
                    index++;
                }
                System.out.println("Student " + sid + "'s course(s):");
                index = 0;
                ArrayList<selectedCourse> courses = rc.viewRegisteredCourses(sid);
                end = majors.size() - 1;
                for (selectedCourse course : courses) {
                    if(course.getCourseId().trim().length() == 0) {
                        continue;
                    } else {
                        System.out.print(course.getCourseId() + ": " + course.getCourseSection());
                        if(index != end) {
                            System.out.println(",");
                        } else {
                            System.out.println("");
                        }
                    }
                    index++;
                }
                
                cc.OutputCoursesForStudent(sid);
                s.nextLine();
                break;
            //Add course
            case "2":
                if(offeredCourses == null || offeredCourses.size() == 0) {
                    System.out.println("No classes are currently available. Please create a class, and ensure it has a professor, and at least one section!");
                    s.nextLine();
                    break;
                }
                System.out.println("What class would you like to register for?");
                int crsIdx = 1;
                for(courseLookup key : offeredCourses) {
                    System.out.println(crsIdx + ": " + key.key);
                    crsIdx++;
                }
                if (s.hasNextLine())
                    selection = s.nextLine();
                selectedCourse = offeredCourses.get(Integer.parseInt(selection) - 1).value;
                clearScreen();
                if (selectedCourse != null && rc.addCourse(sid, selectedCourse)){
                    System.out.println("Operation succeeded, " + selectedCourse.getCID() + " has been added to the schedule.");
                }
                else {
                    System.out.println("Operation failed, " + selectedCourse.getCID() + " has not been added to the schedule.");
                    System.out.println("Three potential causes: already taking course, don't have prereqs, or already have too many credit hours.");        
                }
                s.nextLine();
                break;
            //Update course section
            case "3":
                System.out.println("What class would you like to update?");
                crsIdx = 1;
                for (selectedCourse course : rc.viewRegisteredCourses(sid)) {
                    System.out.println(crsIdx + ": " + course.getCourseId());
                    crsIdx++;
                }
                if (s.hasNextLine())
                    selection = s.nextLine();
                selectedCourse = offeredCourses.get(Integer.parseInt(selection) - 1).value;
                clearScreen();
                if (selectedCourse != null) {
                    ArrayList<courseSection> sections = cc.GetCourseSectionFromIds(selectedCourse.getCourseSectionIds());
                    int secIndex = 1;
                    System.out.println("Select a section to hold this class");
                    for(courseSection cs : sections) {
                        System.out.println(secIndex + ": " + cs.getSectionId());
                        secIndex++;
                    }
                    int selectedIndex = Integer.parseInt(s.nextLine()) - 1;
                    courseSection selectedSection = sections.get(selectedIndex);
                    rc.updateCourseSection(selectedCourse, selectedSection.sectionId);
                    System.out.println("Operation succeeded, " + selectedCourse.getCID() + " has been updated.");
                }
                else {
                    System.out.println("Operation failed, course has not been updated.");
                    System.out.println("This course isn't in the schedule.");
                }
                break;
            //Remove course
            case "4":
                System.out.println("What class would you like to remove?");
                crsIdx = 1;
                for (selectedCourse course : rc.viewRegisteredCourses(sid)) {
                    System.out.println(crsIdx + ": " + course.getCourseId());
                    crsIdx++;
                }
                if (s.hasNextLine())
                    selection = s.nextLine();
                selectedCourse = offeredCourses.get(Integer.parseInt(selection) - 1).value;
                clearScreen();
                if (selectedCourse != null && rc.removeCourse(sid, selectedCourse))
                    System.out.println("Operation succeeded, " + selectedCourse.getCID() + " has been removed from the schedule.");
                else {
                    System.out.println("Operation failed, course has not been removed from the schedule.");
                    System.out.println("This course isn't in the schedule.");
                }
                break;
            //Add major
            case "5":
                System.out.println("What major would you like to register for?");
                for(String key : offeredMajors.keySet()) {
                    System.out.println(key);
                }
                if (s.hasNextLine())
                    selection = s.nextLine();
                selectedMajor = offeredMajors.get(selection);
                clearScreen();
                int tmp = rc.addMajor(sid, selectedMajor);
                if (selectedMajor != null && tmp == 0){
                    System.out.println("Operation succeeded, " + selectedMajor.getMajorName() + ", " + selectedMajor.getDegreeType() + " has been added to the student's records.");
                }
                else if (selectedMajor != null) {
                    System.out.println("Operation failed, " + selectedMajor.getMajorName() + ", " + selectedMajor.getDegreeType() + " has not been added to the student's records.");
                    if (tmp == 1)
                        System.out.println("Student has already requested for graduation, no majors can be added at this time.");
                    else
                        System.out.println("Maximum of two majors and no duplicates allowed.");
                }
                s.nextLine();
                break;
            //Remove major
            case "6":
                System.out.println("What major would you like to remove?");
                for (String major : rc.viewRegisteredMajors(sid)) {
                    System.out.println(major);
                }
                if (s.hasNextLine())
                    selection = s.nextLine();
                selectedMajor = offeredMajors.get(selection);
                clearScreen();
                if (selectedMajor != null && rc.removeMajor(sid, selectedMajor))
                    System.out.println("Operation succeeded, " + selectedMajor.getMajorID() + " has been removed from the student's records.");
                else {
                    System.out.println("Operation failed, major has not been removed from the student's records.");
                    System.out.println("University requires students to have at least one major.");
                }
                break;
            //Graduate student
            case "7":
                clearScreen();
                if (!rc.setGraduate(sid)) {
                    System.out.println("Operation failed, student has not met requirements set by selected major(s).");
                    break;
                }
                System.out.println("Creating diploma file...");
                rc.createDiploma(sid);
                System.out.println("Would the student like for the diploma to be mailed? (Y/N)");
                if (s.hasNextLine())
                    selection = s.nextLine();
                if (selection.equals("Y")){
                    System.out.println("Would the student like to update their mailing address? (Y/N)");
                    if (s.hasNextLine())
                        selection = s.nextLine();
                    if (selection.equals("Y")){
                        System.out.println("Type student's desired mailing address:");
                        if (s.hasNextLine())
                            address = s.nextLine();
                        student tmpStudent = sc.getStudent(sid).value;
                        tmpStudent.setAddress(address);
                        if (sc.updateStudent(tmpStudent)) {
                            System.out.println("Mailing address successfully updated.");
                        }
                        else
                            System.out.println("Mailing address unsuccessfully updated, please try again later.");
                    }
                    System.out.println("Tell student to expect to receive diploma within a month of graduation ceremony.");
                }
                else {
                    System.out.println("Tell student to pick up diploma from Registrar Office.");
                }
                break;
            default:
                clearScreen();
                System.out.println("That is not a valid option.");
                return;
        }
    }

    /**
     * Prompts the user to enter the student ID of the student that wants to add financial information to their account.
     * Prompts the user to enter their financial information (such as credit/debit card details and billing address) 
     * and collects financial details from the user.
     * Uses AccountsReceivableOffice to store the collected FinancialInfo data into the database and print a receipt of the transaction. 
     * @param sc The StudentController instance to interact with the student account.
    */
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

        if (fc.isCardValid(cardNum, cardType)) {
            System.out.println("Valid information entered.");
        } else {
            System.out.println("Invalid information entered.");
            return;
        }

        System.out.println("What is the billing address? (Street, City, State, ZIP code)");
        String billingAddress = s.nextLine();

        try {
            FinancialInfo financialInfo = new FinancialInfo(cardType, cardNum, billingAddress, currStud.value.getStudentId());
            AccountReceivableOffice aro = new AccountReceivableOffice();
            boolean result = aro.addStudentFinancialInfo(currStud.value, financialInfo);
    
            if (result) {
                System.out.println("Financial information added successfully."); 
                aro.printFinancialInfoReceipt(currStud.value);
            } else {
                System.out.println("Failed to add financial information.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    /**
     * Prompts the user to enter the student ID of the student that wants to edit the financial information in their account.
     * Prompts the user to edit their saved financial information (such as credit/debit card details and billing address.
     * Collects the updated financial information, and
     * edits the existing financial information (credit/debit card details and billing address) that the user selects.
     * Stores the changes into the database using AccountsReceivableOffice. 
     * @param sc The StudentController instance to interact with the student account.
    */
    public static void editFinancialInfo(StudentController sc) {
        String userIn = null;
        String cardType = "";
        String cardNumber = "";
        String billingAddress = "";
        System.out.println("Enter the student's ID:");
        userIn = s.nextLine();

        studentLookup curStudent = sc.getStudent(userIn);
        FinancialInfo curFinancialInfo = curStudent.value.getFinancialInfo();
        if(curFinancialInfo == null) {
            System.out.println("This student has no financial information on account.");
            return;
        }

        System.out.println("Edit student's saved card? (Y or N)");
        System.out.println("-----------------------------------");
        System.out.println("Saved Card Type: " + curFinancialInfo.getCardType());
        System.out.println("Saved Card Number: " + curFinancialInfo.getCardNumber());
        System.out.println("-----------------------------------");

        userIn = s.nextLine();
        if (userIn.equalsIgnoreCase("Y")) {
            FinancialInfoController fiC = new FinancialInfoController();
            System.out.println("Enter new card type:");
            cardType = s.nextLine();

            System.out.println("Enter new card number:");
            cardNumber = s.nextLine();
            if(fiC.isCardValid(cardNumber, cardType)) {
                System.out.println("Valid information entered.");
            } else {
                return;
            }
        }  else if (userIn.equalsIgnoreCase("N")) {
            cardType = curFinancialInfo.getCardType();
            cardNumber = curFinancialInfo.getCardNumber();
            System.out.println("Saved card information not edited.");
        }

        System.out.println("Edit student's saved billing address? (Y or N)");
        System.out.println("-----------------------------------");
        System.out.println("Saved Billing Address: " + curFinancialInfo.getBillingAddress());
        System.out.println("-----------------------------------");

        userIn = s.nextLine();
        if (userIn.equalsIgnoreCase("Y")) {
            System.out.println("Enter new billing address:");
            billingAddress = s.nextLine();
            System.out.println("New billing address entered.");
        } else if (userIn.equalsIgnoreCase("N")) {
            billingAddress = curFinancialInfo.getBillingAddress();
            System.out.println("Billing address not edited.");
        }

        try {
            FinancialInfo newFI = new FinancialInfo(cardType, cardNumber, billingAddress, curStudent.value.getStudentId());
            AccountReceivableOffice aro = new AccountReceivableOffice();
            boolean result = aro.editStudentFinancialInfo(curStudent.value, newFI);
            if (result) {
                System.out.println("Financial information edited successfully."); 
            } else {
                System.out.println("Failed to edit financial information.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }

    }

    /**
     * Prompts the user to enter the student ID of the student that wants to delete the financial information stored on their account.
     * Prompts the user to delete the financial information (credit/debit card details and billing address) of the student.
     * Removes all stored financial data from the student's account and from the database using AccountReceivableOffice.
     * @param sc The StudentController instance to interact with the student account.
    */
    public static void deleteFinancialInfo(StudentController sc) {
        String userIn = null;

        System.out.println("Enter the student's ID:");
        userIn = s.nextLine();

        studentLookup curStudent = sc.getStudent(userIn);
        FinancialInfo curFinancialInfo = curStudent.value.getFinancialInfo();
        if(curFinancialInfo == null) {
            System.out.println("This student has no financial information on account.");
            return;
        }

        System.out.println("Delete student's saved financial information? (Y or N)");
        System.out.println("Saved Financial Information: ");
        System.out.println("------------------------------");
        System.out.println("Card Type: " + curFinancialInfo.getCardType());
        System.out.println("Card Number: " + curFinancialInfo.getCardNumber());
        System.out.println("Billing Address: " + curFinancialInfo.getBillingAddress());
        System.out.println("------------------------------");
        
        userIn = s.nextLine();

        if (userIn.equalsIgnoreCase("Y")) {
            try {
                AccountReceivableOffice aro = new AccountReceivableOffice();
                boolean result = aro.deleteStudentFinancialInfo(curStudent.value, curFinancialInfo);
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
        } else {
            System.out.println("Transaction cancelled.");
            return;
        }

    }

    /**
     * Prompts the user to enter the student ID of the student that wants to add a payment onto their account.
     * Prompts the user to enter a payment amount for the payment they want to add onto their account.
     *      (If applicable) Displays scholarships and account balance after scholarships are applied
     * Processes a payment made by the student based on scholarship status and payment amount and updates their account.
     * The payment details are collected and stored in a database, and a receipt for the transaction is printed using AccountsReceivableOffice.
     * @param sc The StudentController instance to manage the student's account.
    */
    public static void addPayment(StudentController sc) {
        Payment payment = new Payment();
        PaymentController pc = new PaymentController();
        boolean foundStudent = false;

        String userIn = null;
        System.out.println("Enter the student's ID: ");
        userIn = s.nextLine();
        studentLookup curStudent = sc.getStudent(userIn);
        FinancialInfo curFinancialInfo = curStudent.value.getFinancialInfo();

        if (curStudent != null) {
            foundStudent = true;
        }

        if (curStudent.value.getFinancialInfo() == null) {
            System.out.println("This student has no saved financial info on account.");
            return;
        }

        ArrayList<Scholarship> scholarships = curStudent.value.getScholarships();
        StringBuilder scholarshipBuilder = new StringBuilder();
        double scholarshipAmount = 0;
        if (!scholarships.contains("") && !scholarships.isEmpty()) {
            for(Scholarship scholarship: scholarships) {
                if (scholarship.checkIsApplied() == false) {
                    scholarship.setApplied();
                    scholarshipAmount+=(scholarship.getScholarshipAmount());
                    scholarshipBuilder.append(scholarship.getScholarshipName());
                    scholarshipBuilder.append("  ");
                } else {
                    scholarshipBuilder.append(scholarship.getScholarshipName());
                    scholarshipBuilder.append("(applied)  ");
                }
            }
        }
        
        System.out.println("Account balance of: " + curStudent.value.getAccountBalance()); 
        System.out.println();
        System.out.println("Scholarships: ");
        System.out.println("-----------------------------------");
        if (scholarshipBuilder.isEmpty()) {
            System.out.println("No scholarships on this student's account.");
        } else {
            System.out.println(scholarshipBuilder.toString());
        }
        System.out.println("-----------------------------------");
        System.out.println("Account balance after scholarships: " + (curStudent.value.getAccountBalance() - + scholarshipAmount));
        System.out.println();

        System.out.println("Use saved payment information? (Y or N)");
        System.out.println("-----------------------------------");
        System.out.println("Saved Card Type: " + curFinancialInfo.getCardType());
        System.out.println("Saved Card Number: " + curFinancialInfo.getCardNumber());
        System.out.println("-----------------------------------");
        userIn = s.nextLine();
        if (userIn.equals("Y")) {
            System.out.println("Enter payment amount:  (Enter in 000.00 format)");
            userIn = s.nextLine();
            double amountToPay = Double.parseDouble(userIn);
            int randomNum = (int)(Math.random() * 99999) + 1;
            String paymentID = String.valueOf(randomNum);
            payment.setAmount(amountToPay);
            payment.setPaymentId(paymentID);
            payment.setPaymentType(curStudent.value.getFinancialInfo().getCardType());
            System.out.println();
            System.out.println("Confirm the following information (Y or N):");
            System.out.println("-----------------------------------");
            System.out.println("Amount To Pay: " + payment.getAmount());
            System.out.println("Card Type: " + payment.getPaymentType() + " | " + "Card Number: " + curStudent.value.getFinancialInfo().getCardNumber());
            System.out.println("-----------------------------------");
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
            }
        } else {
            System.out.println("Transaction cancelled.");
            System.out.println();
        }

        if(!foundStudent) {
            System.out.println("You are not registered in the system.");
        }

        try {
            AccountReceivableOffice aro = new AccountReceivableOffice();
            boolean result = aro.addStudentPayment(curStudent.value, payment, scholarshipAmount);

            if (result) {
                System.out.println("Payent processed successfully. New balance: $" + curStudent.value.getAccountBalance());
                aro.printPaymentReceipt(payment, curStudent.value);
            } else {
                System.out.println("Failed to Process Payment.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    /** 
     * Prompts the user to enter the student ID of the student that wants to add a scholarship onto their account.
     * Prompts the us er to enter the name of the scholarship that student wants to add to their account.
     *      (Displays) The available scholarships to add to the students account.
     * Adds a scholarship to the student's account and uses AccountReceivableOffice to update the database, 
     * checking whether or not the entered scholarship exists and the student qualifies for the scholarship.
     * @param sc The StudentController instance to manage the student's account.
    */
    public static void addStudentScholarship(StudentController sc) {
        DatabaseSupport db = new DatabaseSupport();
        ScholarshipController ssc = new ScholarshipController();
        Scholarship curScholarship = new Scholarship();

        String userIn = null;
        System.out.println("Enter the student's ID: ");
        userIn = s.nextLine();
        studentLookup curStudent = sc.getStudent(userIn);

        System.out.println("What is the name of the scholarship the student would like to add?");

        StringBuilder availableScholarships = new StringBuilder();
        ArrayList<Scholarship> existingScholarships = db.getScholarships();
        for (Scholarship scholarship : existingScholarships) {
            availableScholarships.append(scholarship.getScholarshipName());
            availableScholarships.append("    ");
        }
        System.out.println("Available Scholarships: " + availableScholarships.toString());
        userIn = s.nextLine();
        for (Scholarship scholarship : existingScholarships) {
            if (scholarship.getScholarshipName().equalsIgnoreCase(userIn)) {
                curScholarship.setScholarshipAmount(scholarship.getScholarshipAmount()); 
                curScholarship.setScholarshipId(scholarship.getScholarshipId());
                curScholarship.setScholarshipName(scholarship.getScholarshipName());
                break;
            } 
        }

        boolean validateScholarship = ssc.validateScholarship(userIn, curStudent.value);
        if (validateScholarship) {
            System.out.println("Successfully validated scholarship status.");

            AccountReceivableOffice aro = new AccountReceivableOffice();
            boolean result = aro.addStudentScholarship(curStudent.value, curScholarship);
            if (result) {
                System.out.println("Successfully added scholarship.");
            } else {
                System.out.println("Failed to add scholarship.");
            }
        } else {
            System.out.println("Failed to add scholarship.");
        }
    }

    /**
     * Prompts the user to enter the student ID of the student that needs a scholarship deleted from their account.
     * Prompts the user to enter the name of the scholarship that needs to be deleted from the students account.
     *      (Displays) The current scholarships on  the students account.
     * Prompts the user to delete the entered scholarship from the students account.
     * Removes the entered scholarship from the students account and updates the database using AccountReceivableOffice.
     * @param sc The StudentController instance to interact with the student account.
    */
    public static void deleteStudentScholarship(StudentController sc) {
        Scholarship curScholarship = new Scholarship();

        String userIn = null;
        System.out.println("Enter the student's ID: ");
        userIn = s.nextLine();
        studentLookup curStudent = sc.getStudent(userIn);

        if (curStudent.value.getScholarships().contains("") || curStudent.value.getScholarships().isEmpty()) {
            System.out.println("This student has no scholarships on account.");
            return;
        }

        System.out.println("What is the name of the scholarship you would like to delete?");

        StringBuilder availableScholarships = new StringBuilder();
        ArrayList<Scholarship> studentScholarships = curStudent.value.getScholarships();
        for (Scholarship scholarship : studentScholarships) {
            availableScholarships.append(scholarship.getScholarshipName());
            availableScholarships.append("    ");
        }
        System.out.println("Available Scholarships: " + availableScholarships.toString());

        userIn = s.nextLine();
        boolean validatedScholarshipStatus = false;
        while (!validatedScholarshipStatus) {
            for (Scholarship scholarship : studentScholarships) {
                if (scholarship.getScholarshipName().equalsIgnoreCase(userIn)) {
                    curScholarship = scholarship;
                    validatedScholarshipStatus = true;
                    System.out.println("Validated scholarship status, please continue.");
                    break;
                } else {
                    System.out.println("Entered name is not one of the available scholarships, please try again.");
                    userIn = s.nextLine();
                }
            }
        }

        AccountReceivableOffice aro = new AccountReceivableOffice();
        boolean result = aro.deleteStudentScholarship(curStudent.value, curScholarship);
        if (result) {
            System.out.println("Successfully deleted scholarship.");
        } else {
            System.out.println("Failed to delete scholarship.");
        }
    }

    /**
     * Provides an interface for the user to interact with the payment, financial information,
     * and scholarship management features. The user can select an option to add payment, 
     * add financial information, add a scholarship, or edit financial information.
     * @param sc The StudentController instance to interact with the student account.
    */
    public static void PaymentService(StudentController sc) {
        System.out.println("Would you like to do?");
        System.out.println("1. Add Payment");
        System.out.println("2. Add Financial Information");
        System.out.println("3. Add Scholarship");
        System.out.println("4. Edit Financial Information");
        System.out.println("5. Delete Financial Information");
        System.out.println("6. Delete Scholarship");

        String selection = null;
        if (s.hasNextLine())
          selection = s.nextLine();

        switch(selection) {
            case "1":
            case "1. Add Payment":
            case "1.":
            case "Add Payment":
                clearScreen();
                addPayment(sc);
                break;
            case "2":
            case "2. Add Financial Information":
            case "2.":
            case "Add Financial Information":
                clearScreen();
                addFinancialInfo(sc);
                break;
            case "3":
            case "3. Add Scholarship":
            case "3.":
            case "Add Scholarship":
                clearScreen();
                addStudentScholarship(sc);
                break;
            case "4":
            case "4. Edit Financial Information":
            case "4.":
            case "Edit Financial Information":
                clearScreen();
                editFinancialInfo(sc);
                break;
            case "5":
            case "5. Delete Financial Information":
            case "5.":
            case "Delete Financial Information":
                clearScreen();
                deleteFinancialInfo(sc);
                break;
            case "6":
            case "6. Delete Scholarship":
            case "6.":
            case "Delete Scholarship":
                clearScreen();
                deleteStudentScholarship(sc);
                break;
        }
    }

    public static void main(String[] args) {
        scheduleLookup forceBuild = new scheduleLookup();
        selectedCoursesLookup forceBuild3 = new selectedCoursesLookup();
        courseSectionLookup forceBuild4 = new courseSectionLookup();
        // Select a department. This is in place instead of any type of authentication.
        //  This allows us to take the user to the correct 'screen'.
        Departments selectedDepartment = null;
        DatabaseSupport db = new DatabaseSupport();
        StudentController studentController = new StudentController(db);
        ApplicationController appController = new ApplicationController(db);
        OfferController offerController = new OfferController(db);
        ProfessorController professorController = new ProfessorController(db);
        DormController dormController = new DormController(db, studentController);
        RegistrationController rc = new RegistrationController(db);
        CourseController cc = new CourseController(db);
        MajorController mc = new MajorController(db);
        StudentApplication forceBuild2 = new StudentApplication(db);


        s = new Scanner(System.in);

        while(selectedDepartment != Departments.EXIT) {
            selectedDepartment = getDepartment();

            switch(selectedDepartment) {
                case ADMISSIONS:
                    AdmissionsTasks(studentController, appController, dormController);
                    break;
                case HUMAN_RESOURCES:
                    HRTasks(offerController, professorController, cc, studentController);
                    break;
                case REGISTRATION:
                    CourseRegistration(rc, cc, mc, studentController);
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
    }
}
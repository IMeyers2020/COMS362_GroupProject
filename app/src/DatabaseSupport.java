package src;

import java.util.HashMap;

import models.finances.paymentServices.FinancialInfo;
import models.finances.paymentServices.Payment;
import models.general.items.Course;
import models.general.people.professor;
import models.general.people.student;


public class DatabaseSupport {
    public static HashMap<String, student> getStudents() {
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

    public boolean putStudent(student s) {
        HashMap<String, student> allStudents = getStudents();

        // True if doesn't exist yet, false otherwise
        // Will change this to actually save to a DB
        return allStudents.get(s.getStudentId()) != null;
    }

    public static HashMap<String, professor> getProfessors() {
        HashMap<String, professor> map = new HashMap<String, professor>();
        professor one = new professor("12345", "Prof One", "111 1st St.", "111-11-1111");
        map.put("one", one);
        professor two = new professor("23451","Prof Two", "111 1st St.", "111-11-1111");
        map.put("two", two);
        professor three = new professor("34512", "Prof Three", "111 1st St.", "111-11-1111");
        map.put("three", three);
        professor four = new professor("45123", "Prof Four", "111 1st St.", "111-11-1111");
        map.put("four", four);
        professor five = new professor("51234", "Prof Five", "111 1st St.", "111-11-1111");
        map.put("five", five);
        return map;
    }

    public boolean putProfessor(professor s) {
        HashMap<String, professor> allStudents = getProfessors();

        // True if doesn't exist yet, false otherwise
        // Will change this to actually save to a DB
        return allStudents.get(s.getPID()) != null;
    }

    public static HashMap<String, student> getFinancialInfo() {
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

    public boolean putFinancialInfo(FinancialInfo fi) {
        HashMap<String, student> allFI = getFinancialInfo();

        // True if doesn't exist yet, false otherwise
        // Will change this to actually save to a DB
        return allFI.get(fi.getCardNumber()) != null;
    }

    public static HashMap<String, Payment> getPayments() {
        HashMap<String, Payment> map = new HashMap<String, Payment>();
        Payment one = new Payment("12345", 100.0, "credit", false);
        map.put("one", one);
        Payment two = new Payment("23451",200.0,"credit",false);
        map.put("two", two);
        Payment three = new Payment("34512", 300.0, "credit", false);
        map.put("three", three);
        Payment four = new Payment("45123", 400.0, "credit", false);
        map.put("four", four);
        Payment five = new Payment("51234", 500.0, "credit",false);
        map.put("five", five);
        return map;
    } 

    public boolean putPayment(Payment p) {
        HashMap<String, Payment> allP = getPayments();

        return allP.get(p.getPaymentId()) != null;
    }

    public static HashMap<String, Course> getCoursesForStudent(String sid) {
        HashMap<String, Course> map = new HashMap<String, Course>();
        Course one = new Course("12345", 4);
        map.put("one", one);
        Course two = new Course("12345", 4);
        map.put("two", two);
        Course three = new Course("12345", 4);
        map.put("three", three);
        Course four = new Course("12345", 4);
        map.put("four", four);
        Course five = new Course("12345", 4);
        map.put("five", five);
        return map;
    }

    public static HashMap<String, Course> getAllCourses() {
        HashMap<String, Course> map = new HashMap<String, Course>();
        Course one = new Course("12345", 4);
        map.put("one", one);
        Course two = new Course("12345", 4);
        map.put("two", two);
        Course three = new Course("12345", 4);
        map.put("three", three);
        Course four = new Course("12345", 4);
        map.put("four", four);
        Course five = new Course("12345", 4);
        map.put("five", five);
        return map;
    }

    public boolean putCourse(String sid, Course c) {
        HashMap<String, Course> allC = getCoursesForStudent(sid);

        return allC.get(c.getCID()) != null;
    }

    public boolean removeCourse(String sid, Course c) {
        HashMap<String, Course> allC = getCoursesForStudent(sid);

        return allC.get(c.getCID()) != null;
    }
}

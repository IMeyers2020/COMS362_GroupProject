package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import models.dorms.DormInfo;
import models.finances.paymentServices.FinancialInfo;
import models.finances.paymentServices.Payment;
import models.general.items.Course;
import models.general.people.professor;
import models.general.people.student;
import src.jsonParser.JsonUtil;


public class DatabaseSupport {
    public static student student = new student("1", "Test Student", null, null, null, 0);
    public static student student2 = new student("2", "Test Student2", null, null, null, 0);


    public HashMap<String, student> students = new HashMap<>();
    public HashMap<String, professor> professors = new HashMap<>();
    public static HashMap<String, DormInfo> dorms = new HashMap<>();

    public DatabaseSupport() {
        this.addStudent(student.getStudentId(),student);
        this.addStudent(student2.getStudentId(),student2);
    }

    public boolean addDorm(String dormId) {
        if (!dorms.containsKey(dormId)) {
            dorms.put(dormId, new DormInfo(dormId, this));
            return true;
        }
        return false;
    }

    // Remove a dorm from the database
    public boolean removeDorm(String dormId) {
        if (dorms.containsKey(dormId)) {
            dorms.remove(dormId);
            return true;
        }
        return false;
    }

    public boolean addStudent(String studentId, student stud) {
        student resultingKey = this.students.put(studentId, stud);

        return resultingKey == null;
    }

    public boolean updateStudent(String studentId, student stud) {
        student resultingKey = this.students.put(studentId, stud);

        return resultingKey != null;
    }

    public DormInfo getDorm(String dormId) {
        return dorms.get(dormId);
    }

    public student getStudentForDorm(String studentId) {
        return students.get(studentId);
    }

    public int getDormssize() {
        return dorms.size();
    }

    public HashMap<String, student> getStudents() {
        return this.students;
    }

    public HashMap<String, professor> getProfessors() {
        return this.professors;
    }

    public boolean addProfessor(professor prof) {
        professor resultingKey = this.professors.put(prof.getPID(), prof);

        return resultingKey == null;
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

    public boolean putFinancialInfo(FinancialInfo fi) throws IOException{
        String filePath = "app/models/finances/data/FinancialInfo.json";
        String jsonContent = JsonUtil.serialize(fi);

        File file = new File(filePath);
        boolean fileExists = file.exists();
        boolean isArrayClosed = false;
        StringBuilder fileContent = new StringBuilder();
    
        // Check if the file exists and has content
        if (fileExists && file.length() > 0) {
            // Read the file content to check if the array is closed
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent.append(line);
                }
                // Check if the file ends with "]" indicating the array is closed
                if (fileContent.toString().endsWith("]")) {
                    isArrayClosed = true;
                }
            }
        }
    
        // Open the file for writing (not appending yet)
        try (FileWriter fileWriter = new FileWriter(filePath, false);  // Overwrite the file first
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
    
            // If the array is closed or the file is empty, start a new array
            if (fileExists && isArrayClosed) {
                // Remove the closing bracket (we're going to append data)
                fileContent.setLength(fileContent.length() - 1); // Remove the ']'
                bufferedWriter.write(fileContent.toString()); // Write the content without the ']'
                bufferedWriter.write(","); // Add a comma before appending new data
            } else if (!fileExists || file.length() == 0) {
                // If the file doesn't exist or is empty, start a new array
                bufferedWriter.write("[");
            } else {
                // If the file contains an open array, just append new data with a comma
                bufferedWriter.write(",");
            }
    
            // Append the new object
            bufferedWriter.write(jsonContent);
    
            // Close the array by appending the closing bracket
            bufferedWriter.write("]");
    
            return true;
        }
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

    public static HashMap<String, Course> getAllCourses() {
        HashMap<String, Course> map = new HashMap<String, Course>();
        Course one = new Course("100", 4);
        map.put("100", one);
        Course two = new Course("200", 4);
        map.put("200", two);
        Course three = new Course("300", 4);
        map.put("300", three);
        Course four = new Course("400", 4, Set.of("100", "200"));
        map.put("400", four);
        Course five = new Course("500", 4, Set.of("200", "300"));
        map.put("500", five);
        return map;
    }

    public boolean putCourse(String sid, Course c) {
        // will eventually return student with sid, for now just using preset student
        // HashMap<String, Course> students = getStudents();
        // student student = students.get(sid);
        return true;
    }

    public boolean removeCourse(String sid, Course c) {
        // will eventually return student with sid, for now just using preset student
        // HashMap<String, Course> students = getStudents();
        // student student = students.get(sid);
        return true;
    }

    public student getStudent(String sid) {
        // will eventually return student with sid, for now just using preset student
        // HashMap<String, Course> students = getStudents();
        // student student = students.get(sid);
        return student;
    }

    public static HashMap<String, Course> getRegisteredCoursesForStudent(String sid) {
        // will eventually return student with sid, for now just using preset student
        // HashMap<String, Course> students = getStudents();
        // student student = students.get(sid);
        return student.getCurrentCourses();
    }
}

package src;

import java.io.BufferedWriter;
import java.io.File;
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
    public static student student = new student("1", "Test Student", null, null, null, 5000.0);
    public static student student2 = new student("2", "Test Student2", null, null, null, 13000.0);


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

    public boolean putFinancialInfo(FinancialInfo fi) throws IOException{
        String filePath = "app/models/finances/data/FinancialInfo.txt";
        String jsonContent = JsonUtil.serialize(fi);

        File file = new File(filePath);
        boolean fileExists = file.exists();

        boolean isFirstObject = !fileExists || file.length() == 0;
    
        try (FileWriter fileWriter = new FileWriter(filePath, true);  
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
    
            if (isFirstObject) {
                // Append the new object
                bufferedWriter.write(jsonContent);
            } else {
                bufferedWriter.write(",");
                bufferedWriter.write(jsonContent);
            }
    
            return true;
        }
    }

    public boolean putPayment(Payment p) throws IOException{
        String filePath = "app/models/finances/data/Payments.json";
        String jsonContent = JsonUtil.serialize(p);

        File file = new File(filePath);
        boolean fileExists = file.exists();

        boolean isFirstObject = !fileExists || file.length() == 0;
    
        try (FileWriter fileWriter = new FileWriter(filePath, true);  
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
    
            if (isFirstObject) {
                // Append the new object
                bufferedWriter.write(jsonContent);
            } else {
                bufferedWriter.write(",");
                bufferedWriter.write(jsonContent);
            }
    
            return true;
        }
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

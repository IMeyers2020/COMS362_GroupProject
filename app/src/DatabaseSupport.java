package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import models.dorms.DormInfo;
import models.finances.paymentServices.FinancialInfo;
import models.finances.paymentServices.Payment;
import models.general.items.Course;
import models.general.items.courseLookup;
import models.general.items.dormLookup;
import models.general.people.professor;
import models.general.people.professorLookup;
import models.general.people.student;
import models.general.people.studentLookup;
import src.jsonParser.JsonUtil;


public class DatabaseSupport {
    public DatabaseSupport() {
    }
    // STUDENT FUNCTIONS
    public ArrayList<studentLookup> getStudents() {
        DB_Student lookups = new DB_Student();

        try(BufferedReader br = new BufferedReader(new FileReader("./StudentDB.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
        
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();

            if(everything.trim().equals("{}")) {
                return new ArrayList<studentLookup>();
            } else {
                lookups = JsonUtil.deserialize(everything, lookups.getClass());
            }
        } catch (Exception e) {
            return new ArrayList<studentLookup>();
        }
        return new ArrayList<studentLookup>(lookups.students);
    }

    public studentLookup getStudent(String sid) {
        ArrayList<studentLookup> students = getStudents();

        for(studentLookup s : students) {
            if(s.value.getStudentId() == sid) {
                return s;
            }
        }
        return null;
    }

    public boolean addStudent(String studentId, student stud) {
        studentLookup sl = new studentLookup(studentId, stud);

        ArrayList<studentLookup> arrayListed = getStudents();
        arrayListed.add(sl);

        try {
            DB_Student dbStud = new DB_Student();
            dbStud.setStudents(arrayListed);
            String lookupsString = JsonUtil.serialize(dbStud);
            System.out.println(lookupsString);

            Files.writeString(Paths.get("./StudentDB.txt"), lookupsString);
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }

        return true;
    }

    public boolean removeStudent(String studentId) {
        studentLookup[] lookups = {};

        ArrayList<studentLookup> arrayListed = getStudents();
        arrayListed.removeIf(s -> s.key == studentId);
        lookups = arrayListed.toArray(lookups);

        try {
            String lookupsString = JsonUtil.serialize(lookups);
            Files.writeString(Paths.get("./StudentDB.txt"), lookupsString);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean updateStudent(String studentId, student stud) {
        studentLookup sl = new studentLookup(studentId, stud);
        studentLookup[] lookups = {};

        ArrayList<studentLookup> arrayListed = getStudents();
        arrayListed.removeIf(s -> s.key == studentId);
        arrayListed.add(sl);
        lookups = arrayListed.toArray(lookups);

        try {
            String lookupsString = JsonUtil.serialize(lookups);
            Files.writeString(Paths.get("./StudentDB.txt"), lookupsString);
        } catch (Exception e) {
            return false;
        }

        return true;
    }


    // DORM FUNCTIONS
    public ArrayList<dormLookup> getDorms() {
        dormLookup[] lookups = {};

        try(BufferedReader br = new BufferedReader(new FileReader("./DormDB.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
        
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();

            lookups = JsonUtil.deserialize(everything, lookups.getClass());
        } catch (Exception e) {
            return new ArrayList<dormLookup>();
        }
        return new ArrayList<dormLookup>(Arrays.asList(lookups));
    }

    public dormLookup getDorm(String did) {
        ArrayList<dormLookup> dorms = getDorms();

        for(dormLookup d : dorms) {
            if(d.value.getDormId() == did) {
                return d;
            }
        }
        return null;
    }

    public boolean addDorm(String dormId, DormInfo dorm) {
        dormLookup dl = new dormLookup(dormId, dorm);
        dormLookup[] lookups = {};

        ArrayList<dormLookup> arrayListed = getDorms();
        arrayListed.add(dl);
        lookups = arrayListed.toArray(lookups);

        try {
            String lookupsString = JsonUtil.serialize(lookups);
            Files.writeString(Paths.get("./DormDB.txt"), lookupsString);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean removeDorm(String dormId) {
        dormLookup[] lookups = {};

        ArrayList<dormLookup> arrayListed = getDorms();
        arrayListed.removeIf(s -> s.key == dormId);
        lookups = arrayListed.toArray(lookups);

        ArrayList<studentLookup> studs = getStudents();
        studs.removeIf(s -> !(s.value.getDormId() == dormId));

        for(studentLookup s : studs) {
            s.value.setDormId(null);
            updateStudent(s.value.getStudentId(), s.value);
        }

        try {
            String lookupsString = JsonUtil.serialize(lookups);
            Files.writeString(Paths.get("./DormDB.txt"), lookupsString);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean updateDorm(String dormId, DormInfo dorm) {
        dormLookup dl = new dormLookup(dormId, dorm);
        dormLookup[] lookups = {};

        ArrayList<dormLookup> arrayListed = getDorms();
        arrayListed.removeIf(s -> s.key == dormId);
        arrayListed.add(dl);
        lookups = arrayListed.toArray(lookups);

        try {
            String lookupsString = JsonUtil.serialize(lookups);
            Files.writeString(Paths.get("./DormDB.txt"), lookupsString);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public studentLookup getStudentForDorm(String dormId) {
        ArrayList<studentLookup> studs = getStudents();

        for(studentLookup s : studs) {
            if(s.value.getDormId() == dormId) {
                return s;
            }
        }

        return null;
    }

    public int getDormssize() {
        ArrayList<dormLookup> d = getDorms();
        return d.size();
    }

    // PROFESSOR FUNCTIONS
    public ArrayList<professorLookup> getProfessors() {
        professorLookup[] lookups = {};

        try(BufferedReader br = new BufferedReader(new FileReader("./ProfessorDB.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
        
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();

            lookups = JsonUtil.deserialize(everything, lookups.getClass());
        } catch (Exception e) {
            return new ArrayList<professorLookup>();
        }
        return new ArrayList<professorLookup>(Arrays.asList(lookups));
    }

    public professorLookup getProfessor(String pid) {
        ArrayList<professorLookup> profs = getProfessors();

        for(professorLookup p : profs) {
            if(p.value.getPID() == pid) {
                return p;
            }
        }
        return null;
    }

    public boolean addProfessor(professor prof) {
        professorLookup pl = new professorLookup(prof.getPID(), prof);
        studentLookup[] lookups = {};

        ArrayList<professorLookup> arrayListed = getProfessors();
        arrayListed.add(pl);
        lookups = arrayListed.toArray(lookups);

        try {
            String lookupsString = JsonUtil.serialize(lookups);
            Files.writeString(Paths.get("./ProfessorDB.txt"), lookupsString);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean removeProfessor(String profId) {
        professorLookup[] lookups = {};

        ArrayList<professorLookup> arrayListed = getProfessors();
        arrayListed.removeIf(s -> s.key == profId);
        lookups = arrayListed.toArray(lookups);

        try {
            String lookupsString = JsonUtil.serialize(lookups);
            Files.writeString(Paths.get("./ProfessorDB.txt"), lookupsString);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean updateProfessor(String profId, professor stud) {
        professorLookup pl = new professorLookup(profId, stud);
        professorLookup[] lookups = {};

        ArrayList<professorLookup> arrayListed = getProfessors();
        arrayListed.removeIf(s -> s.key == profId);
        arrayListed.add(pl);
        lookups = arrayListed.toArray(lookups);

        try {
            String lookupsString = JsonUtil.serialize(lookups);
            Files.writeString(Paths.get("./ProfessorDB.txt"), lookupsString);
        } catch (Exception e) {
            return false;
        }

        return true;
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
        String filePath = "app/models/finances/data/Payments.txt";
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


    public ArrayList<courseLookup> getCoursesForStudent(String sid) {
        studentLookup student = getStudent(sid);

        return student.value.getCurrentCourses();
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

    public ArrayList<courseLookup> getRegisteredCoursesForStudent(String sid) {
        studentLookup s = this.getStudent(sid);
        return s.value.getCurrentCourses();
    }
}

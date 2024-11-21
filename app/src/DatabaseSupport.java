package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import models.dorms.DormInfo;
import models.finances.paymentServices.FinancialInfo;
import models.finances.paymentServices.Payment;
import models.general.items.Course;
import models.general.items.Major;
import models.general.items.courseLookup;
import models.general.items.dormLookup;
import models.general.items.majorLookup;
import models.general.people.professor;
import models.general.people.professorLookup;
import models.general.people.student;
import models.general.people.studentLookup;
import models.finances.paymentServices.ScholarshipLookup;
import src.jsonParser.JsonUtil;


public class DatabaseSupport {
    public DatabaseSupport() {
    }
    // STUDENT FUNCTIONS
    public ArrayList<studentLookup> getStudents() {
        DB_Student lookups = new DB_Student();
    
        try (BufferedReader br = new BufferedReader(new FileReader("./StudentDB.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
    
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
    
            if (everything.trim().equals("{}")) {
                return new ArrayList<studentLookup>();
            } else {
                DB_Student dbStudent = (DB_Student) JsonUtil.deserialize(everything, DB_Student.class);
    
                if (dbStudent != null && dbStudent.students != null) {
                    lookups.setStudents(dbStudent.students);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<studentLookup>();
        }
        return lookups.students != null ? new ArrayList<>(lookups.students) : new ArrayList<>();
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
        DB_Professor lookups = new DB_Professor();
    
        try (BufferedReader br = new BufferedReader(new FileReader("./ProfessorDB.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
    
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();

    
            if (everything.trim().equals("{}")) {
                return new ArrayList<professorLookup>();
            } else {
                DB_Professor dbProfessor = (DB_Professor) JsonUtil.deserialize(everything, DB_Professor.class);
                
                if (dbProfessor != null && dbProfessor.professors != null) {
                    lookups.setProfessors(dbProfessor.professors);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<professorLookup>();
        }
        return lookups.professors != null ? new ArrayList<>(lookups.professors) : new ArrayList<>();
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

    public boolean addProfessor(String profId, professor prof) {
        professorLookup sl = new professorLookup(profId, prof);

        ArrayList<professorLookup> arrayListed = getProfessors();
        arrayListed.add(sl);

        try {
            DB_Professor dbProf = new DB_Professor();
            dbProf.setProfessors(arrayListed);
            String lookupsString = JsonUtil.serialize(dbProf);

            Files.writeString(Paths.get("./ProfessorDB.txt"), lookupsString);
        } catch (Exception e) {
            System.err.println(e);
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

    public boolean updateFinancialInfo(FinancialInfo fi) throws IOException {
        String filePath = "app/models/finances/data/FinancialInfo.txt";
        File file = new File(filePath);

        List<FinancialInfo> financialInfoList = new ArrayList<>();
        
        // Check if the file exists and read its contents
        if (file.exists() && file.length() > 0) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                
                // Deserialize existing data into a list of FinancialInfo
                String jsonContent = sb.toString();
                financialInfoList = Arrays.asList(JsonUtil.deserialize(jsonContent, FinancialInfo[].class));
            }
        }

        // Find and replace the matching FinancialInfo
        boolean isUpdated = false;
        for (int i = 0; i < financialInfoList.size(); i++) {
            if (financialInfoList.get(i).getStudent().equals(fi.getStudent())) { 
                financialInfoList.set(i, fi);
                isUpdated = true;
                break;
            }
        }

        // If not found
        if (!isUpdated) {
            System.out.println("Previous financial information not found.");
        }

        // Serialize the updated list back to the file
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            String updatedJsonContent = JsonUtil.serialize(financialInfoList);
            bufferedWriter.write(updatedJsonContent);
        }

        return isUpdated;
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

    public ArrayList<ScholarshipLookup> getScholarships() {
        ScholarshipLookup[] lookups = {};

        try(BufferedReader br = new BufferedReader(new FileReader("./Scholarships.txt"))) {
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
            return new ArrayList<ScholarshipLookup>();
        }
        return new ArrayList<>(Arrays.asList(lookups));
    }


    public ScholarshipLookup getScholarship(String ssid) {
        ArrayList<ScholarshipLookup> scholarships = getScholarships();

        for(ScholarshipLookup s : scholarships) {
            if(s.value.getScholarshipId() == ssid) {
                return s;
            }
        }
        return null;
    }


    public ArrayList<courseLookup> getCoursesForStudent(String sid) {
        studentLookup student = getStudent(sid);

        return student.value.getCurrentCourses();
    }

    public static HashMap<String, Course> getAllCourses() {
        HashMap<String, Course> map = new HashMap<String, Course>() {{
            put("COMS100", new Course("COMS100", 3));
            put("COMS200", new Course("COMS200", 3));
            put("SE200", new Course("SE200", 3));
            put("COMS300", new Course("COMS300", 3));
            put("COMS400", new Course("COMS400", 4, Set.of("COMS100")));
            put("SE400", new Course("SE400", 4, Set.of("SE200")));
            put("COMS500", new Course("COMS500", 4, Set.of("COMS200")));
            put("FIN100", new Course("FIN100", 3));
            put("FIN200", new Course("FIN200", 3));
            put("FIN300", new Course("FIN300", 4, Set.of("FIN100")));
            put("FIN400", new Course("FIN400", 4, Set.of("FIN200")));
        }};
        return map;
    }

    public static HashMap<String, Major> getAllMajors() {
        HashMap<String, Major> map = new HashMap<String, Major>() {{
            put("SE", new Major("SE", "Software Engineering", "B.S.", 125, Set.of("COMS100", "COMS200", "COMS300", "COMS400", "COMS500")));
            put("COMS(BS)", new Major("COMS(BS)", "Computer Science", "B.S.", 120, Set.of("COMS100", "COMS200", "COMS300", "COMS400", "COMS500")));
            put("COMS(BA)", new Major("COMS(BA)", "Computer Science", "B.A.", 120, Set.of("COMS100", "COMS200", "COMS300", "COMS400")));
            put("FIN", new Major("FIN", "Finance", "B.S.", 122, Set.of("COMS100", "COMS200", "COMS300", "COMS400")));
        }};
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

    public ArrayList<majorLookup> getRegisteredMajorsForStudent(String sid) {
        studentLookup s = this.getStudent(sid);
        return s.value.getMajors();
    }
}

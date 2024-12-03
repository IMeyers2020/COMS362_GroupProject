package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import models.dorms.DormInfo;
import models.finances.paymentServices.FinancialInfo;
import models.finances.paymentServices.Payment;
import models.general.items.Course;
import models.general.items.Major;
import models.general.items.dormLookup;
import models.general.items.schedule;
import models.general.items.scheduleLookup;
import models.general.people.professor;
import models.general.people.professorLookup;
import models.general.people.student;
import models.general.people.studentLookup;
import models.finances.paymentServices.Scholarship;
import models.finances.paymentServices.ScholarshipLookup;
import src.constants.DAYS;
import src.constants.TIMES;
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
            if(s.value.getStudentId().equals(sid)) {
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

        ArrayList<studentLookup> arrayListed = getStudents();
        arrayListed.removeIf(s -> s.key.equals(studentId));
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

    public boolean PrintScheduleForStudent(student stud) {
        // Note: This currently assumes all classes get 1 hr time slots. Change this in the future to match possibility of different timeslots like other standard universities
        try {
            final int stringWidth = 10; // 2-3 spaces on both sides ideally (3 if single digit time like 8:00AM, 2 if double like 11:00AM)
            String timeLabelString = "  ";
            for(TIMES timeString : TIMES.values()) {
                timeLabelString = timeLabelString + "|";
                timeLabelString = timeLabelString + " ".repeat(Math.floorDiv((stringWidth - timeString.label.length()), 2));
                timeLabelString = timeLabelString + timeString.label;
                timeLabelString = timeLabelString + " ".repeat(Math.ceilDiv((stringWidth - timeString.label.length()), 2));
            }

            ArrayList<String> courseIds = getCoursesForStudent(stud.getStudentId());
            HashMap<String, Course> allCourses = getAllCourses();

            String daysString = "";
            for(DAYS dayString : DAYS.values()) {
                String dayClassString = dayString.label + " |";
                for(TIMES timeString : TIMES.values()) {
                    boolean courseFound = false;
                    for(String courseId : courseIds) {
                        Course currentCourse = allCourses.get(courseId);

                        if(currentCourse == null || currentCourse.getTimeOfClass() == null || currentCourse.getDaysOfClass() == null || currentCourse.getDaysOfClass().size() == 0) {
                            continue;
                        } else {
                            if(allCourses.get(courseId).getTimeOfClass().equals(timeString) && allCourses.get(courseId).getDaysOfClass().contains(dayString)) {
                                courseFound = true;
                                String idToShow = courseId.substring(0, 5); // Show at most 6 characters. I.E FIN200. Shouldn't have more than that
                                if(dayClassString.charAt(dayClassString.length() - 1) != '|') {
                                    dayClassString = dayClassString.substring(0, dayClassString.length() - 1) + "|";
                                }
                                dayClassString = dayClassString + " ".repeat(Math.floorDiv((stringWidth - idToShow.length()), 2));
                                dayClassString = dayClassString + idToShow;
                                dayClassString = dayClassString + " ".repeat(Math.ceilDiv((stringWidth - idToShow.length()), 2));
                                dayClassString = dayClassString + "|";
                                break;
                            }
                        }
                    }
                    if(!courseFound) {
                        dayClassString = dayClassString + "-".repeat(stringWidth + 1); // +1 to account for the '|' that we added to the labels
                    }
                }
                daysString = daysString + dayClassString + "\n";
            }

            String returnString = "   Schedule for " + stud.getName() + "\n\n"
            + timeLabelString + "\n"
            + daysString;

            String schedulePath = "./" + stud.getName().trim() + "-Schedule.txt";

            Files.writeString(Paths.get(schedulePath), returnString);
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }

        return true;
    }


    // SCHEDULE FUNCTIONS
    public ArrayList<scheduleLookup> getSchedules() {
        DB_Schedule lookups = new DB_Schedule();
    
        try (BufferedReader br = new BufferedReader(new FileReader("./ScheduleDB.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
    
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
    
            if (everything.trim().equals("{}")) {
                return new ArrayList<scheduleLookup>();
            } else {
                DB_Schedule dbSchedule = (DB_Schedule) JsonUtil.deserialize(everything, DB_Schedule.class);
    
                if (dbSchedule != null && dbSchedule.schedules != null) {
                    lookups.setSchedules(dbSchedule.schedules);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<scheduleLookup>();
        }
        return lookups.schedules != null ? new ArrayList<>(lookups.schedules) : new ArrayList<>();
    }   

    public scheduleLookup getSchedule(String sid) {
        ArrayList<scheduleLookup> scheds = getSchedules();

        for(scheduleLookup s : scheds) {
            if(s.value.getScheduleId().equals(sid)) {
                return s;
            }
        }
        return null;
    }

    public boolean addSchedule(String schedId, schedule sched) {
        scheduleLookup sl = new scheduleLookup(schedId, sched);

        ArrayList<scheduleLookup> arrayListed = getSchedules();
        arrayListed.add(sl);

        try {
            DB_Schedule dbSched = new DB_Schedule();
            dbSched.setSchedules(arrayListed);
            String lookupsString = JsonUtil.serialize(dbSched);

            Files.writeString(Paths.get("./ScheduleDB.txt"), lookupsString);
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }

        return true;
    }

    public boolean removeSchedule(String schedId) {
        scheduleLookup[] lookups = {};

        ArrayList<scheduleLookup> arrayListed = getSchedules();
        arrayListed.removeIf(s -> s.key == schedId);
        lookups = arrayListed.toArray(lookups);

        try {
            String lookupsString = JsonUtil.serialize(lookups);
            Files.writeString(Paths.get("./ScheduleDB.txt"), lookupsString);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean updateSchedule(String schedId, schedule sched) {
        scheduleLookup sl = new scheduleLookup(schedId, sched);

        ArrayList<scheduleLookup> arrayListed = getSchedules();
        arrayListed.removeIf(s -> s.key.equals(schedId));
        arrayListed.add(sl);

        try {
            DB_Schedule dbSched = new DB_Schedule();
            dbSched.setSchedules(arrayListed);
            String lookupsString = JsonUtil.serialize(dbSched);

            Files.writeString(Paths.get("./ScheduleDB.txt"), lookupsString);
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }

        return true;
    }

    // COURSE FUNCTIONS
    public ArrayList<scheduleLookup> getAllCourses() {
        DB_Course lookups = new DB_Course();
    
        try (BufferedReader br = new BufferedReader(new FileReader("./ScheduleDB.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
    
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
    
            if (everything.trim().equals("{}")) {
                return new ArrayList<scheduleLookup>();
            } else {
                DB_Schedule dbSchedule = (DB_Schedule) JsonUtil.deserialize(everything, DB_Schedule.class);
    
                if (dbSchedule != null && dbSchedule.schedules != null) {
                    lookups.setSchedules(dbSchedule.schedules);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<scheduleLookup>();
        }
        return lookups.schedules != null ? new ArrayList<>(lookups.schedules) : new ArrayList<>();
    }   

    public scheduleLookup getSchedule(String sid) {
        ArrayList<scheduleLookup> scheds = getSchedules();

        for(scheduleLookup s : scheds) {
            if(s.value.getScheduleId().equals(sid)) {
                return s;
            }
        }
        return null;
    }

    public boolean addSchedule(String schedId, schedule sched) {
        scheduleLookup sl = new scheduleLookup(schedId, sched);

        ArrayList<scheduleLookup> arrayListed = getSchedules();
        arrayListed.add(sl);

        try {
            DB_Schedule dbSched = new DB_Schedule();
            dbSched.setSchedules(arrayListed);
            String lookupsString = JsonUtil.serialize(dbSched);

            Files.writeString(Paths.get("./ScheduleDB.txt"), lookupsString);
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }

        return true;
    }

    public boolean removeSchedule(String schedId) {
        scheduleLookup[] lookups = {};

        ArrayList<scheduleLookup> arrayListed = getSchedules();
        arrayListed.removeIf(s -> s.key == schedId);
        lookups = arrayListed.toArray(lookups);

        try {
            String lookupsString = JsonUtil.serialize(lookups);
            Files.writeString(Paths.get("./ScheduleDB.txt"), lookupsString);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean updateSchedule(String schedId, schedule sched) {
        scheduleLookup sl = new scheduleLookup(schedId, sched);

        ArrayList<scheduleLookup> arrayListed = getSchedules();
        arrayListed.removeIf(s -> s.key.equals(schedId));
        arrayListed.add(sl);

        try {
            DB_Schedule dbSched = new DB_Schedule();
            dbSched.setSchedules(arrayListed);
            String lookupsString = JsonUtil.serialize(dbSched);

            Files.writeString(Paths.get("./ScheduleDB.txt"), lookupsString);
        } catch (Exception e) {
            System.err.println(e);
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

    public boolean PrintProfessorOffer(professor prof) {
        try {
            String lookupsString = "Dear " + prof.getName() + ", \n\n" + "We are excited to offer you this fulltime offer as a "
            + prof.getAOS() + " Professor at this University!\n\n" +
            "Your compensation for this role will be $60,000/year.\n\n" +
            "We look forward to working with you,\n" + "- The University";

            String offerPath = "./" + prof.getName().trim() + "-OfferLetter.txt";

            Files.writeString(Paths.get(offerPath), lookupsString);
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
        String filePath = "models/finances/data/FinancialInfo.txt";
        File file = new File(filePath);
        List<FinancialInfo> financialInfoList = new ArrayList<>();

        // Check if the file exists and has content
        if (file.exists() && file.length() > 0) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                // Deserialize existing JSON array into a list
                String jsonContent = sb.toString();
                financialInfoList = JsonUtil.deserializeArray(jsonContent, FinancialInfo.class);
            }
        }

        // Add the new FinancialInfo object to the list
        financialInfoList.add(fi);

        // Serialize the updated list back to the file
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            StringBuilder sb = new StringBuilder();
            sb.append("["); // Start of JSON array

            for (int i = 0; i < financialInfoList.size(); i++) {
                sb.append(JsonUtil.serialize(financialInfoList.get(i)));
                if (i < financialInfoList.size() - 1) {
                    sb.append(","); // Add comma between objects
                }
            }

            sb.append("]"); // End of JSON array
            bufferedWriter.write(sb.toString());
        }

        return true;
    }

    public boolean updateFinancialInfo(FinancialInfo fi) throws IOException {
        String filePath = "models/finances/data/FinancialInfo.txt";
        File file = new File(filePath);
        int lineCount = 0;

        List<FinancialInfo> financialInfoList = new ArrayList<>();
        
        // Check if the file exists and read its contents
        if (file.exists() && file.length() > 0) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                    lineCount++;
                }
                
                // Deserialize existing data into a list of FinancialInfo
                if (lineCount < 2) {
                    String jsonContent = sb.toString();
                    FinancialInfo dJsonContent = JsonUtil.deserialize(jsonContent, FinancialInfo.class);
                    financialInfoList.add(dJsonContent);
                }

                String jsonArray = sb.toString();
                financialInfoList = JsonUtil.deserializeArray(jsonArray, FinancialInfo.class);
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

        if (lineCount < 2) {
            // Serialize back to the file
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
                String updatedJsonContent = JsonUtil.serialize(financialInfoList.get(0));
                bufferedWriter.write(updatedJsonContent);
            }
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (int i = 0; i < financialInfoList.size(); i++) {
                sb.append(JsonUtil.serialize(financialInfoList.get(i)));
                if (i < financialInfoList.size() - 1) {
                    sb.append(",");
                }
            }
            sb.append("]");
            String updatedJsonContent = sb.toString();
            bufferedWriter.write(updatedJsonContent);
        }

        return isUpdated;
    }

    public boolean putPayment(Payment p) throws IOException{
        String filePath = "models/finances/data/Payments.txt";
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

    public ArrayList<Scholarship> getScholarships() {
        List<Scholarship> lookups = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader("models/finances/data/Scholarships.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
        
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            String everything = sb.toString();

            lookups = JsonUtil.deserializeArray(everything, Scholarship.class);
        } catch (Exception e) {
            return new ArrayList<Scholarship>();
        }
        return new ArrayList<Scholarship>(lookups);
    }


    public Scholarship getScholarship(String ssid) {
        ArrayList<Scholarship> scholarships = getScholarships();

        for(Scholarship s : scholarships) {
            if(s.getScholarshipId() == ssid) {
                return s;
            }
        }
        return null;
    }


    public ArrayList<String> getCoursesForStudent(String sid) {
        Scanner scann = new Scanner(System.in);
        studentLookup student = getStudent(sid);
        if(student == null || student.value.getScheduleId() == null) {
            System.err.println("Failed to find courses, unable to get Schedule from given StudentId");
            scann.nextLine();
            return null;
        }

        scheduleLookup sched = getSchedule(student.value.getScheduleId());
        if(sched == null || sched.value.getScheduleId() == null) {
            System.err.println("Failed to find courses, unable to find a Schedule matching the ScheduleId on that student");
            scann.nextLine();
            return null;
        }

        return sched.value.getCourses();
    }

    public static HashMap<String, Course> getAllCourses() {
        ArrayList<DAYS> MWF = new ArrayList<DAYS>();
        MWF.add(DAYS.Monday);
        MWF.add(DAYS.Wednesday);
        MWF.add(DAYS.Friday);
        ArrayList<DAYS> TR = new ArrayList<DAYS>();
        TR.add(DAYS.Tuesday);
        TR.add(DAYS.Thursday);
        HashMap<String, Course> map = new HashMap<String, Course>() {{
            put("COMS100", new Course("COMS100", 3, TIMES.EightAM, MWF));
            put("COMS200", new Course("COMS200", 3, TIMES.NineAM, MWF));
            put("SE200", new Course("SE200", 3, TIMES.TenAM, MWF));
            put("COMS300", new Course("COMS300", 3, TIMES.ElevenAM, MWF));
            put("COMS400", new Course("COMS400", 4, Set.of("COMS100"), TIMES.EightAM, TR));
            put("SE400", new Course("SE400", 4, Set.of("SE200"), TIMES.NineAM, TR));
            put("COMS500", new Course("COMS500", 4, Set.of("COMS200"), TIMES.TenAM, TR));
            put("FIN100", new Course("FIN100", 3, TIMES.ElevenAM, TR));
            put("FIN200", new Course("FIN200", 3, TIMES.TwelvePM, MWF));
            put("FIN300", new Course("FIN300", 4, Set.of("FIN100"), TIMES.TwelvePM, TR));
            put("FIN400", new Course("FIN400", 4, Set.of("FIN200"), TIMES.OnePM, MWF));
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

    public ArrayList<String> getRegisteredCoursesForStudent(String sid) {
        return this.getCoursesForStudent(sid);
    }

    public ArrayList<String> getRegisteredMajorsForStudent(String sid) {
        studentLookup s = this.getStudent(sid);
        return s.value.getMajors();
    }
}

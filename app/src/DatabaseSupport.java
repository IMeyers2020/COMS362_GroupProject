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
import java.util.Scanner;
import java.util.Set;

import models.Diningplan.Dplan;
import models.dorms.DormInfo;
import models.finances.paymentServices.FinancialInfo;
import models.finances.paymentServices.Payment;
import models.general.items.Course;
import models.general.items.Major;
import models.general.items.courseLookup;
import models.general.items.courseSectionLookup;
import models.general.items.diningplanlookups;
import models.general.items.dormLookup;
import models.general.items.schedule;
import models.general.items.scheduleLookup;
import models.general.items.selectedCourse;
import models.general.items.selectedCoursesLookup;
import models.general.people.courseSection;
import models.general.people.professor;
import models.general.people.professorLookup;
import models.general.people.student;
import models.general.people.studentLookup;
import models.finances.paymentServices.Scholarship;
import src.constants.DAYS;
import src.constants.TIMES;
import src.jsonParser.JsonUtil;

public class DatabaseSupport {
    public DatabaseSupport() {
    }

    // DINING PLAN FUNCTIONS

    public boolean PrintMealPlan(Dplan d) {
        try {
            String lookupsString = "Dear " + d.getStudent().getName() + ", \n\n"
                    + "You have chose the "
                    + d.getType() + " meal plan. You will be charged " + d.getcost() + " for the " + d.getTerm() + " Please Sign\n\n" +
                    "--------------------------------------\n\n" +
                    "--------------------------------------,\n" + "- The University";

            String offerPath = "./" + d.getStudent().getName().trim() + "-Mealplan.txt";

            Files.writeString(Paths.get(offerPath), lookupsString);
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }

        return true;
    }

    public ArrayList<diningplanlookups> getDiningPlans() {
        DB_DININGPLAN lookups = new DB_DININGPLAN();

        try (BufferedReader br = new BufferedReader(new FileReader("./DiningPlanDB.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();

            if (everything.trim().equals("{}")) {
                return new ArrayList<diningplanlookups>();
            } else {
                DB_DININGPLAN dbDiningPlan = (DB_DININGPLAN) JsonUtil.deserialize(everything, DB_DININGPLAN.class);

                if (dbDiningPlan != null && dbDiningPlan.diningPlans != null) {
                    lookups.setDiningPlans(dbDiningPlan.diningPlans);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<diningplanlookups>();
        }
        return lookups.diningPlans != null ? new ArrayList<>(lookups.diningPlans) : new ArrayList<>();
    }

    public diningplanlookups getDiningPlan(String dplanId) {
        ArrayList<diningplanlookups> diningPlans = getDiningPlans();

        for (diningplanlookups dp : diningPlans) {
            if (dp.getKey().equals(dplanId)) {
                return dp;
            }
        }
        return null;
    }

    public boolean addDiningPlan(String dplanId, Dplan dplan) {
        diningplanlookups dpLookup = new diningplanlookups(dplanId, dplan);

        ArrayList<diningplanlookups> arrayListed = getDiningPlans();
        arrayListed.add(dpLookup);

        try {
            DB_DININGPLAN dbDiningPlan = new DB_DININGPLAN();
            dbDiningPlan.setDiningPlans(arrayListed);
            String lookupsString = JsonUtil.serialize(dbDiningPlan);

            Files.writeString(Paths.get("./DiningPlanDB.txt"), lookupsString);
            PrintMealPlan(dplan);
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }

        return true;
    }

    public boolean removeDiningPlan(String dplanId) {
        diningplanlookups[] lookups = {};

        ArrayList<diningplanlookups> arrayListed = getDiningPlans();
        arrayListed.removeIf(dp -> dp.getKey().equals(dplanId));
        lookups = arrayListed.toArray(lookups);

        try {
            String lookupsString = JsonUtil.serialize(lookups);
            Files.writeString(Paths.get("./DiningPlanDB.txt"), lookupsString);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    // STUDENT FUNCTIONS
    /**
     * Get all student records in the database. Uses the JSON util method to deserialize the contents of a local StudentDB.txt file
     * @return A list of studentLookup objects, which contain a key and a student value. Allows for a structure similar to a HashMap (not as optimal) that can easily be stored in the DB
     */
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

    /**
     * Returns the student in the database that has a matching studentId.
     * Gets all students using out getStudents method, then filters out options without a matching Id
     * @param sid The Id of the student to get.
     * @return A keyValue pair of studentId and student
     */
    public studentLookup getStudent(String sid) {
        ArrayList<studentLookup> students = getStudents();

        for (studentLookup s : students) {
            if (s.value.getStudentId().equals(sid)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Adds a student into the database.
     * Creates a key value pair of the studentId and the student, gets all current students, adds the created key/value pair to the existing list, then updates in the database.
     *  (Database is updated by writing the serialized list to a local StudentDB.txt file) using JSON Utils.
     * @param studentId
     * @param stud
     * @return
     */
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

    /**
     * Removes a student with a matching student Id.
     * Gets all students using the getStudents() method
     * @param studentId Id of the student to be removed
     * @return true if removal was successful, false otherwise
     */
    public boolean removeStudent(String studentId) {
        studentLookup[] lookups = {};

        ArrayList<studentLookup> arrayListed = getStudents();
        arrayListed.removeIf(s -> s.key.equals(studentId));
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
        // Note: This currently assumes all classes get 1 hr time slots. Change this in
        // the future to match possibility of different timeslots like other standard
        // universities
        try {
            final int stringWidth = 10; // 2-3 spaces on both sides ideally (3 if single digit time like 8:00AM, 2 if
                                        // double like 11:00AM)
            String timeLabelString = "  ";
            for (TIMES timeString : TIMES.values()) {
                timeLabelString = timeLabelString + "|";
                timeLabelString = timeLabelString
                        + " ".repeat(Math.floorDiv((stringWidth - timeString.label.length()), 2));
                timeLabelString = timeLabelString + timeString.label;
                timeLabelString = timeLabelString
                        + " ".repeat(Math.ceilDiv((stringWidth - timeString.label.length()), 2));
            }

            ArrayList<selectedCourse> courseIds = getCoursesForStudent(stud.getStudentId());

            String daysString = "";
            for (DAYS dayString : DAYS.values()) {
                String dayClassString = dayString.label + " |";
                for (TIMES timeString : TIMES.values()) {
                    boolean courseFound = false;
                    for (selectedCourse courseId : courseIds) {
                        ArrayList<courseLookup> coursesClone = getCourses();

                        coursesClone.removeIf(c -> !(c.value.getCID().equals(courseId.getCourseId())));
                        if (coursesClone.size() > 0) {
                            Course currentCourse = coursesClone.get(0).value;

                            selectedCoursesLookup sc = getSelectedCourse(stud.getStudentId(), currentCourse.getCID());

                            if (currentCourse == null || sc == null) {
                                continue;
                            } else {
                                String selectedCourseSection = sc.value.getCourseSection();
                                courseSectionLookup foundSection = getCourseSection(selectedCourseSection);
                                if (foundSection.value.courseTime.equals(timeString.label)
                                        && foundSection.value.courseDays.contains(dayString.label)) {
                                    courseFound = true;
                                    String idToShow = courseId.getCourseId().substring(0, 5); // Show at most 6
                                                                                              // characters. I.E FIN200.
                                                                                              // Shouldn't have more
                                                                                              // than that
                                    if (dayClassString.charAt(dayClassString.length() - 1) != '|') {
                                        dayClassString = dayClassString.substring(0, dayClassString.length() - 1) + "|";
                                    }
                                    dayClassString = dayClassString
                                            + " ".repeat(Math.floorDiv((stringWidth - idToShow.length()), 2));
                                    dayClassString = dayClassString + idToShow;
                                    dayClassString = dayClassString
                                            + " ".repeat(Math.ceilDiv((stringWidth - idToShow.length()), 2));
                                    dayClassString = dayClassString + "|";
                                    break;
                                }
                            }
                        }
                    }
                    if (!courseFound) {
                        dayClassString = dayClassString + "-".repeat(stringWidth + 1); // +1 to account for the '|' that
                                                                                       // we added to the labels
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
    /**
     * Get all schedules records in the database. Uses the JSON util method to deserialize the contents of a local ScheduleDB.txt file
     * @return A list of scheduleLookup objects, which contain a key and a student value. Allows for a structure similar to a HashMap (not as optimal) that can easily be stored in the DB
     */
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

        for (scheduleLookup s : scheds) {
            if (s.value.getScheduleId().equals(sid)) {
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
        arrayListed.removeIf(s -> s.key.equals(schedId));
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
    /**
     * Get all dorm records in the database. Uses the JSON util method to deserialize the contents of a local DormDB.txt file
     * @return A list of dormLookup objects, which contain a key and a student value. Allows for a structure similar to a HashMap (not as optimal) that can easily be stored in the DB
     */
    public ArrayList<dormLookup> getDorms() {
        dormLookup[] lookups = {};

        try (BufferedReader br = new BufferedReader(new FileReader("./DormDB.txt"))) {
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

        for (dormLookup d : dorms) {
            if (d.value.getDormId().equals(did)) {
                return d;
            }
        }
        return null;
    }

    public boolean addDorm(String dormId, DormInfo dorm) {
        dormLookup dl = new dormLookup(dormId, dorm);

        ArrayList<dormLookup> arrayListed = getDorms();
        arrayListed.add(dl);
        String lookupsString = JsonUtil.serialize(dl);

        try {
            DB_DORMS db_DORMS = new DB_DORMS();
            db_DORMS.setEvents(arrayListed);
            Files.writeString(Paths.get("./DormDB.txt"), lookupsString);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean removeDorm(String dormId) {
        dormLookup[] lookups = {};

        ArrayList<dormLookup> arrayListed = getDorms();
        arrayListed.removeIf(s -> s.key.equals(dormId));
        lookups = arrayListed.toArray(lookups);

        ArrayList<studentLookup> studs = getStudents();
        studs.removeIf(s -> !(s.value.getDormId().equals(dormId)));

        for (studentLookup s : studs) {
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
        arrayListed.removeIf(s -> s.key.equals(dormId));
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

        for (studentLookup s : studs) {
            if (s.value.getDormId().equals(dormId)) {
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
    /**
     * Get all student records in the database. Uses the JSON util method to deserialize the contents of a local ProfessorDB.txt file
     * @return A list of professorLookup objects, which contain a key and a student value. Allows for a structure similar to a HashMap (not as optimal) that can easily be stored in the DB
     */
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

        for (professorLookup p : profs) {
            if (p.value.getPID().equals(pid)) {
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
            String lookupsString = "Dear " + prof.getName() + ", \n\n"
                    + "We are excited to offer you this fulltime offer as a "
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
        arrayListed.removeIf(s -> s.key.equals(profId));
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
        arrayListed.removeIf(s -> s.key.equals(profId));
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

    public professorLookup getProfessorForCourse(Course c) {
        if (c.GetProfessorId() == null) {
            return null;
        } else {
            return getProfessor(c.GetProfessorId());
        }
    }

    /**
     * Adds a new financial information record to the FinancialInfo.txt file.
     * If the file exists and contains content, it deserializes the existing data
     * into a list,
     * adds the new FinancialInfo object, and serializes the updated list back into
     * the file.
     * 
     * @param fi The FinancialInfo object to be added.
     * @return true if the operation is successful, false otherwise.
     * @throws IOException If an I/O error occurs while reading or writing the file.
     */
    public boolean addFinancialInfo(FinancialInfo fi) throws IOException {
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

    /**
     * Updates an existing financial information record in the FinancialInfo.txt
     * file.
     * The method searches for a matching FinancialInfo object by student ID and
     * replaces it with the new data.
     * If the financial information is found and updated, the updated list is
     * serialized back to the file.
     * 
     * @param fi The FinancialInfo object to update.
     * @return true if the financial information is updated successfully, false if
     *         not found.
     * @throws IOException If an I/O error occurs while reading or writing the file.
     */
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

        // Check for special case (only 1 stored financial info object)
        if (lineCount < 2) {
            // Serialize back to the file
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
                String updatedJsonContent = JsonUtil.serialize(financialInfoList.get(0));
                bufferedWriter.write(updatedJsonContent);
            }
        }

        // Serialize the updated list back to the file
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

    /**
     * Deletes an existing financial information record from the FinancialInfo.txt
     * file.
     * The method searches for the specified FinancialInfo object by student ID and
     * removes it.
     * If the financial information is found and deleted, the updated list is
     * serialized back to the file.
     * 
     * @param fi The FinancialInfo object to be deleted.
     * @return true if the financial information is deleted successfully, false if
     *         not found.
     * @throws IOException If an I/O error occurs while reading or writing the file.
     */
    public boolean deleteFinancialInfo(FinancialInfo fi) throws IOException {
        String filePath = "models/finances/data/FinancialInfo.txt";
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

                // Deserialize existing JSON array into a list
                String jsonContent = sb.toString();
                financialInfoList = JsonUtil.deserializeArray(jsonContent, FinancialInfo.class);
            }
        }

        // Find and remove the matching FinancialInfo
        boolean isDeleted = financialInfoList.removeIf(info -> info.getStudent().equals(fi.getStudent()));

        // If not found
        if (!isDeleted) {
            System.out.println("Financial information not found.");
            return false;
        }

        // Serialize the updated list back to the file
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            if (financialInfoList.isEmpty()) {
                // If the list is empty, write nothing to the file (empty file)
                bufferedWriter.write("");
            } else if (financialInfoList.size() == 1) {
                // Special case: only one item left, serialize it directly
                String updatedJsonContent = JsonUtil.serialize(financialInfoList.get(0));
                bufferedWriter.write(updatedJsonContent);
            } else {
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
        }
        return true;
    }

    /**
     * Adds a new payment record to the Payments.txt file.
     * If the file exists, the new payment is appended to the file; otherwise, it
     * writes the first payment record to the file.
     * 
     * @param p The Payment object to be added.
     * @return true if the payment is successfully added, false otherwise.
     * @throws IOException If an I/O error occurs while reading or writing the file.
     */
    public boolean addPayment(Payment p) throws IOException {
        String filePath = "models/finances/data/Payments.txt";
        String jsonContent = JsonUtil.serialize(p);

        File file = new File(filePath);
        boolean fileExists = file.exists();

        boolean isFirstObject = !fileExists || file.length() == 0;

        try (FileWriter fileWriter = new FileWriter(filePath, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            if (isFirstObject) {
                bufferedWriter.write(jsonContent);
            } else {
                bufferedWriter.write(",");
                bufferedWriter.write(jsonContent);
            }

            return true;
        }
    }

    /**
     * Retrieves all available scholarship records from the Scholarships.txt file.
     * It reads the file, deserializes the content into a list of Scholarship
     * objects, and returns the list.
     * 
     * @return A list of Scholarship objects. If no scholarships are found, an empty
     *         list is returned.
     */
    public ArrayList<Scholarship> getScholarships() {
        List<Scholarship> lookups = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("models/finances/data/Scholarships.txt"))) {
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

    /**
     * Retrieves a specific scholarship record by its scholarship ID.
     * It searches through the list of scholarships and returns the matching
     * scholarship.
     * 
     * @param ssid The scholarship ID to search for.
     * @return The Scholarship object if found, null if not found.
     */
    public Scholarship getScholarship(String ssid) {
        ArrayList<Scholarship> scholarships = getScholarships();

        for (Scholarship s : scholarships) {
            if (s.getScholarshipId().equals(ssid)) {
                return s;
            }
        }
        return null;
    }

    // AVAILABLE COURSES FUNCTIONS
    public ArrayList<courseLookup> getCourses() {
        DB_Courses lookups = new DB_Courses();

        File f = new File("./CoursesDB.txt");
        if (!f.exists() || f.isDirectory()) {
            try {
                f.createNewFile();
                Files.writeString(Paths.get("./CoursesDB.txt"), "{}");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader("./CoursesDB.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();

            if (everything.trim().equals("{}")) {
                return new ArrayList<courseLookup>();
            } else {
                DB_Courses dbCourse = (DB_Courses) JsonUtil.deserialize(everything, DB_Courses.class);

                if (dbCourse != null && dbCourse.courses != null) {
                    lookups.setCourses(dbCourse.courses);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<courseLookup>();
        }
        return lookups.courses != null ? new ArrayList<>(lookups.courses) : new ArrayList<>();
    }

    public courseLookup getAvailableCourse(String cid) {
        ArrayList<courseLookup> courses = getCourses();

        for (courseLookup s : courses) {
            if (s.value.getCID().equals(cid)) {
                return s;
            }
        }
        return null;
    }

    public boolean addCourse(Course course) {
        courseLookup cl = new courseLookup(course.getCID(), course);

        ArrayList<courseLookup> arrayListed = getCourses();
        arrayListed.add(cl);

        try {
            DB_Courses dbCourses = new DB_Courses();
            dbCourses.setCourses(arrayListed);
            String lookupsString = JsonUtil.serialize(dbCourses);

            Files.writeString(Paths.get("./CoursesDB.txt"), lookupsString);
        } catch (Exception e) {
            System.err.println(e);
            Scanner s = new Scanner(System.in);
            s.nextLine();
            s.close();
            return false;
        }

        return true;
    }

    public boolean removeCourse(String courseId) {
        courseLookup[] lookups = {};

        ArrayList<courseLookup> arrayListed = getCourses();
        arrayListed.removeIf(s -> s.key.equals(courseId));
        lookups = arrayListed.toArray(lookups);

        try {
            String lookupsString = JsonUtil.serialize(lookups);
            Files.writeString(Paths.get("./CoursesDB.txt"), lookupsString);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean updateCourses(String courseId, Course course) {
        courseLookup sl = new courseLookup(courseId, course);

        ArrayList<courseLookup> arrayListed = getCourses();
        arrayListed.removeIf(s -> s.key.equals(courseId));
        arrayListed.add(sl);

        try {
            DB_Courses dbCourses = new DB_Courses();
            dbCourses.setCourses(arrayListed);
            String lookupsString = JsonUtil.serialize(dbCourses);

            Files.writeString(Paths.get("./CoursesDB.txt"), lookupsString);
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }

        return true;
    }

    public ArrayList<courseLookup> GetAllValidCourses() {
        ArrayList<courseLookup> arrayListed = getCourses();
        arrayListed.removeIf(s -> !s.value.IsValid());

        return arrayListed;
    }

    public ArrayList<selectedCourse> getCoursesForStudent(String sid) {
        Scanner scann = new Scanner(System.in);
        studentLookup student = getStudent(sid);
        if (student == null || student.value.getScheduleId() == null) {
            System.err.println("Failed to find courses, unable to get Schedule from given StudentId");
            scann.nextLine();
            return null;
        }

        scheduleLookup sched = getSchedule(student.value.getScheduleId());
        if (sched == null || sched.value.getScheduleId() == null) {
            System.err.println(
                    "Failed to find courses, unable to find a Schedule matching the ScheduleId on that student");
            scann.nextLine();
            return null;
        }

        ArrayList<String> courseIds = sched.value.getCourses();
        ArrayList<selectedCourse> retList = new ArrayList<>();

        for (String courseId : courseIds) {
            selectedCoursesLookup crsLookup = getSelectedCourse(student.getKey(), courseId);
            if (crsLookup != null) {
                selectedCourse foundCourse = crsLookup.value;
                retList.add(foundCourse);
            }
        }

        return retList;
    }

    // COURSE SECTIONS FUNCTIONS
    public ArrayList<courseSectionLookup> getCourseSections() {
        DB_CourseSection lookups = new DB_CourseSection();

        try (BufferedReader br = new BufferedReader(new FileReader("./CourseSectionDB.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();

            if (everything.trim().equals("{}")) {
                return new ArrayList<courseSectionLookup>();
            } else {
                DB_CourseSection dbCourseSection = (DB_CourseSection) JsonUtil.deserialize(everything,
                        DB_CourseSection.class);

                if (dbCourseSection != null && dbCourseSection.sections != null) {
                    lookups.setSections(dbCourseSection.sections);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<courseSectionLookup>();
        }
        return lookups.sections != null ? new ArrayList<>(lookups.sections) : new ArrayList<>();
    }

    public courseSectionLookup getCourseSection(String sid) {
        ArrayList<courseSectionLookup> courseSections = getCourseSections();

        for (courseSectionLookup s : courseSections) {
            if (s.value.getSectionId().equals(sid)) {
                return s;
            }
        }
        return null;
    }

    public boolean addCourseSection(String courseSectionId, courseSection sec) {
        courseSectionLookup sl = new courseSectionLookup(courseSectionId, sec);

        ArrayList<courseSectionLookup> arrayListed = getCourseSections();
        arrayListed.add(sl);

        try {
            DB_CourseSection dbCourseSection = new DB_CourseSection();
            dbCourseSection.setSections(arrayListed);
            String lookupsString = JsonUtil.serialize(dbCourseSection);

            Files.writeString(Paths.get("./CourseSectionDB.txt"), lookupsString);
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }

        return true;
    }

    public boolean removeCourseSection(String courseSectionId) {
        courseSectionLookup[] lookups = {};

        ArrayList<courseSectionLookup> arrayListed = getCourseSections();
        arrayListed.removeIf(s -> s.key.equals(courseSectionId));
        lookups = arrayListed.toArray(lookups);

        try {
            String lookupsString = JsonUtil.serialize(lookups);
            Files.writeString(Paths.get("./CourseSectionDB.txt"), lookupsString);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean updateCourseSection(String courseSectionId, courseSection sec) {
        courseSectionLookup sl = new courseSectionLookup(courseSectionId, sec);

        ArrayList<courseSectionLookup> arrayListed = getCourseSections();
        arrayListed.removeIf(s -> s.key.equals(courseSectionId));
        arrayListed.add(sl);

        try {
            DB_CourseSection dbStud = new DB_CourseSection();
            dbStud.setSections(arrayListed);
            String lookupsString = JsonUtil.serialize(dbStud);

            Files.writeString(Paths.get("./CourseSectionDB.txt"), lookupsString);
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }

        return true;
    }

    // STUDENT SELECTED COURSES FUNCTIONS
    public ArrayList<selectedCoursesLookup> getSelectedCourses() {
        DB_SelectedCourse lookups = new DB_SelectedCourse();

        File f = new File("./SelectedCoursesDB.txt");
        if (!f.exists() || f.isDirectory()) {
            try {
                f.createNewFile();
                Files.writeString(Paths.get("./SelectedCoursesDB.txt"), "{}");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader("./SelectedCoursesDB.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();

            if (everything.trim().equals("{}")) {
                return new ArrayList<selectedCoursesLookup>();
            } else {
                DB_SelectedCourse dbSelectedCourse = (DB_SelectedCourse) JsonUtil.deserialize(everything,
                        DB_SelectedCourse.class);

                if (dbSelectedCourse != null && dbSelectedCourse.courses != null) {
                    lookups.setSelectedCourses(dbSelectedCourse.courses);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<selectedCoursesLookup>();
        }
        return lookups.courses != null ? new ArrayList<>(lookups.courses) : new ArrayList<>();
    }

    public selectedCoursesLookup getSelectedCourse(String sid, String cid) {
        ArrayList<selectedCoursesLookup> courses = getSelectedCourses().size() > 0 ? getSelectedCourses()
                : new ArrayList<>();

        for (selectedCoursesLookup s : courses) {
            if (s != null) {
                if (s.value.getStudentId().equals(sid) && s.value.getCourseId().equals(cid)) {
                    return s;
                }
            }
        }
        return null;
    }

    public boolean addSelectedCourse(selectedCourse stud) {
        selectedCoursesLookup sl = new selectedCoursesLookup(stud.getStudentId(), stud);

        ArrayList<selectedCoursesLookup> arrayListed = getSelectedCourses();
        arrayListed.add(sl);

        try {
            DB_SelectedCourse dbSelectedCourse = new DB_SelectedCourse();
            dbSelectedCourse.setSelectedCourses(arrayListed);
            String lookupsString = JsonUtil.serialize(dbSelectedCourse);

            Files.writeString(Paths.get("./SelectedCoursesDB.txt"), lookupsString);
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }

        return true;
    }

    public boolean removeSelectedCourse(String studentId) {
        selectedCoursesLookup[] lookups = {};

        ArrayList<selectedCoursesLookup> arrayListed = getSelectedCourses();
        arrayListed.removeIf(s -> s.key.equals(studentId));
        lookups = arrayListed.toArray(lookups);

        try {
            String lookupsString = JsonUtil.serialize(lookups);
            Files.writeString(Paths.get("./SelectedCoursesDB.txt"), lookupsString);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean updateSelectedCourse(selectedCourse stud) {
        selectedCoursesLookup sl = new selectedCoursesLookup(stud.getStudentId(), stud);

        ArrayList<selectedCoursesLookup> arrayListed = getSelectedCourses();
        arrayListed.removeIf(s -> (s.value.getStudentId().equals(stud.getStudentId())
                && s.value.getCourseId().equals(stud.getCourseId())));
        arrayListed.add(sl);

        try {
            DB_SelectedCourse dbStud = new DB_SelectedCourse();
            dbStud.setSelectedCourses(arrayListed);
            String lookupsString = JsonUtil.serialize(dbStud);

            Files.writeString(Paths.get("./SelectedCoursesDB.txt"), lookupsString);
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }

        return true;
    }

    public static HashMap<String, Major> getAllMajors() {
        HashMap<String, Major> map = new HashMap<String, Major>() {
            {
                put("SE", new Major("SE", "Software Engineering", "B.S.", "Bachelor of Science", 125,
                        Set.of("COMS100", "COMS200", "COMS300", "COMS400", "COMS500")));
                put("COMS(BS)", new Major("COMS(BS)", "Computer Science", "B.S.", "Bachelor of Science", 120,
                        Set.of("COMS100", "COMS200", "COMS300", "COMS400", "COMS500")));
                put("COMS(BA)", new Major("COMS(BA)", "Computer Science", "B.A.", "Bachelor of Arts",120,
                        Set.of("COMS100", "COMS200", "COMS300", "COMS400")));
                put("FIN",
                        new Major("FIN", "Finance", "B.S.", "Bachelor of Science", 122, Set.of("COMS100", "COMS200", "COMS300", "COMS400")));
            }
        };
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
    public ArrayList<studentLookup> getExpelledStudents() {
        DB_Student lookups = new DB_Student();

        try (BufferedReader br = new BufferedReader(new FileReader("./ExpelledStudentDB.txt"))) {
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

    public studentLookup getExpelledStudent(String sid) {
        ArrayList<studentLookup> students = getExpelledStudents();

        for(studentLookup s : students) {
            if(s.value.getStudentId().equals(sid)) {
                return s;
            }
        }
        return null;
    }
    public boolean addExpelledStudent(String studentId, student stud) {
        studentLookup sl = new studentLookup(studentId, stud);

        ArrayList<studentLookup> arrayListed = getStudents();
        arrayListed.add(sl);

        try {
            DB_Student dbStud = new DB_Student();
            dbStud.setStudents(arrayListed);
            String lookupsString = JsonUtil.serialize(dbStud);

            Files.writeString(Paths.get("./ExpelledStudentDB.txt"), lookupsString);
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }

        return true;
    }

    public boolean removeExpelledStudent(String studentId) {
        studentLookup[] lookups = {};

        ArrayList<studentLookup> arrayListed = getStudents();
        arrayListed.removeIf(s -> s.key == studentId);
        lookups = arrayListed.toArray(lookups);

        try {
            String lookupsString = JsonUtil.serialize(lookups);
            Files.writeString(Paths.get("./ExpelledStudentDB.txt"), lookupsString);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public ArrayList<selectedCourse> getRegisteredCoursesForStudent(String sid) {
        return this.getCoursesForStudent(sid);
    }

    public ArrayList<String> getRegisteredMajorsForStudent(String sid) {
        studentLookup s = this.getStudent(sid);
        return s.value.getMajors();
    }
}

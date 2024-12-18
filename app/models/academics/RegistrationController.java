package models.academics;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import models.general.items.Course;
import models.general.items.Major;
import models.general.items.scheduleLookup;
import models.general.items.selectedCourse;
import models.general.people.courseSection;
import models.general.people.student;
import models.general.people.studentLookup;
import src.DatabaseSupport;

public class RegistrationController {

    DatabaseSupport db;

    public RegistrationController(DatabaseSupport _db) {
        this.db = _db;
    }

    /**
     * Adds c to schedule of student with studentID = sid
     * @param sid       Student ID of student to be updated
     * @param c         Course to be added
     * @return          true if student and schedule exist and update is successful
     */
    public boolean addCourse(String sid, Course c) {
        studentLookup stud = this.db.getStudent(sid);
        if(stud == null || stud.value.getScheduleId() == null) {
            System.err.println("Failed to add course, unable to get Schedule from given StudentId");
            return false;
        }

        scheduleLookup sched = this.db.getSchedule(stud.value.getScheduleId());
        if(sched == null || sched.value.getScheduleId() == null) {
            System.err.println("Failed to add course, unable to find a Schedule matching the ScheduleId on that student");
            return false;
        }
        selectedCourse sc = new selectedCourse();
        sched.value.addCourse(c, sc);
        this.db.addSelectedCourse(sc);
        this.db.updateSchedule(sched.key, sched.value);
        this.db.updateStudent(stud.key, stud.value);
        return true;
    }

    /**
     * Removes c from schedule of student with studentID = sid
     * @param sid   Student ID of student to be updated
     * @param c     Course to be removed
     * @return      true if student and schedule exist and update is successful
     */
    public boolean removeCourse(String sid, Course c) {
        studentLookup stud = this.db.getStudent(sid);
        if(stud == null || stud.value.getScheduleId() == null) {
            System.err.println("Failed to remove course, unable to get Schedule from given StudentId");
            return false;
        }

        scheduleLookup sched = this.db.getSchedule(stud.value.getScheduleId());
        if(sched == null || sched.value.getScheduleId() == null) {
            System.err.println("Failed to remove course, unable to find a Schedule matching the ScheduleId on that student");
            return false;
        }
        sched.value.removeCourse(c);
        this.db.updateSchedule(sched.key, sched.value);
        this.db.updateStudent(stud.key, stud.value);
        return true;
    }

    public ArrayList<selectedCourse> viewRegisteredCourses(String sid) {
        return this.db.getCoursesForStudent(sid);
    }

    // adds major m to student with studentId == sid
    // returns 0 if successful
    // returns 1 if student has already set up graduation
    // returns 2 otherwise

    /**
     * Adds the major provided to the student with studentID = sid
     * @param sid   Student ID of student to be updated
     * @param m     Major to be added to student account
     * @return      0 if successful, 1 if student has already requested graduation, 2 otherwise
     */
    public int addMajor(String sid, Major m) {
        studentLookup stud = this.db.getStudent(sid);
        if (!stud.value.addMajor(m))
            return 2;
        if (stud.value.getGraduated())
            return 1;
        this.db.updateStudent(sid, stud.value);
        m.createRequiredCourses();
        return 0;
    }

    /**
     * Removes the major provided from the student with studentID = sid
     * @param sid   Student ID of student to be updated
     * @param m     Major to be removed from student account
     * @return      true if successful, false otherwise
     */
    public boolean removeMajor(String sid, Major m) {
        studentLookup stud = this.db.getStudent(sid);
        if (!stud.value.removeMajor(m))
            return false;
        this.db.updateStudent(sid, stud.value);
        return true;
    }

    public boolean updateCourseSection(Course c, String sectionId) {
        Course courseCopy = c;
        courseCopy.setCourseSections(sectionId);
        return this.db.updateCourses(c.getCID(), courseCopy);
    }

    /**
     * Returns list of majors that student with provided ID is registered under
     * @param sid   Student ID of student
     * @return      List of strings of majors
     */
    public ArrayList<String> viewRegisteredMajors(String sid) {
        studentLookup stud = this.db.getStudent(sid);
        return stud.value.getMajors();
    }

    /**
     * Sets the student as a graduate (marked for graduation from undergrad program) if the student
     * meets all of the requirements of the majors they have registered for.
     * @param sid   Student ID of student
     * @return      true if successful, false if student's credit/course requirements are not met
     */
    public boolean setGraduate(String sid) {
        studentLookup stud = this.db.getStudent(sid);
        student s = stud.value;
        HashMap<String, Major> majors = DatabaseSupport.getAllMajors();

        //for each major the student has, check if requirements are met
        for (String m : s.getMajors()){
            if (m.isEmpty() && s.getMajors().size() == 1){
                System.out.println("No major, so no requirements.");
                return false;
            }

            if (!m.isEmpty()) {
                Major major = majors.get(m);
                //if student doesn't have all required courses completed, return false
                if (!s.getCompletedCourseIDs().containsAll(major.getRequiredCourses())) {
                    return false;
                }
                //if student has less than required credits, return false
                if (s.getTotalCredits() < major.getTotalRequiredCredits()) {
                    return false;
                }
            }
        }

        s.setGraduated(true);
        this.db.updateStudent(sid, s);
        return true;
    }

    /**
     * Creates diploma file for graduating student. Saves file to app/
     * @param sid   Student ID of student
     * @see         ./(studentname)Diploma.txt file created by method
     */
    public void createDiploma(String sid) {
        studentLookup stud = this.db.getStudent(sid);
        student s = stud.value;
        HashMap<String, Major> majors = DatabaseSupport.getAllMajors();
        String degree = "";

        // gets all degrees student is graduating with
        for (String m : s.getMajors()){
            if (!m.isEmpty()) {
                Major major = majors.get(m);
                if (degree.isEmpty())
                    degree += major.getDegree();
                else {
                    // no duplication
                    if (!degree.contains(major.getDegree()))
                        degree += ", " + major.getDegree();
                }
            }
        }

        // diploma file content
        String toFile = "Example University\n" +
                        "hereby confers upon\n" +
                        "" + s.getName() + "\n" +
                        "the degree\n" +
                        "" + degree + "\n";

        try {
            Files.writeString(Paths.get("./" + s.getName().replace(" ", "") + "Diploma.txt"), toFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

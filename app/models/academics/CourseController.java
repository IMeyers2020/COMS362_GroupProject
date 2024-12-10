package models.academics;

import java.util.ArrayList;

import models.general.items.Course;
import models.general.items.courseLookup;
import models.general.items.courseSectionLookup;
import models.general.items.selectedCourse;
import models.general.people.courseSection;
import models.general.people.studentLookup;
import src.DatabaseSupport;

public class CourseController {
    DatabaseSupport db;

    public CourseController(DatabaseSupport _db) {
        this.db = _db;
    }
    
    public ArrayList<courseLookup> getAllCourses() {
        return this.db.getCourses();
    }

    public courseLookup getCourse(String cid) {
        return this.db.getAvailableCourse(cid);
    }

    public ArrayList<courseLookup> getAllValidCourses() {
        return this.db.GetAllValidCourses();
    }

    /**
     * Updates course by setting professor ID
     * @param pId       Professor ID
     * @param c         Contains course to be updated
     * @return          true if successful
     */
    public boolean addProfessorToCourse(String pId, courseLookup c) {
        courseLookup crsClone = c;
        crsClone.value.SetProfessorId(pId);
        return this.db.updateCourses(c.key, c.value);
    }

    /**
     * Adds course to database
     * @param c     Course to be added
     * @return      true if successful
     */
    public boolean addCourse(Course c) {
        return this.db.addCourse(c);
    }

    /**
     * Creates schedule file for student
     * @param sid   Student ID
     * @see         Name-Schedule.txt file created
     */
    public void OutputCoursesForStudent(String sid) {
        studentLookup stud = this.db.getStudent(sid);
        this.db.PrintScheduleForStudent(stud.value);
    }

    /**
     * Saves a selection made by a student into the database.
     * This selection is of a course and section.
     * @param sc    Selected course
     */
    public void AddSelectedCoursesForCourse(selectedCourse sc) {
        this.db.addSelectedCourse(sc);
    }

    /**
     * Adds sections to the provided course
     * @param c     Course for sections to be added to
     * @param secs  Sections to add to course
     */
    public void AddSectionsToCourse(Course c, ArrayList<courseSection> secs) {
        ArrayList<String> stringified = new ArrayList<>();

        for(courseSection sec : secs) {
            stringified.add(sec.getSectionId());
            this.db.addCourseSection(sec.getSectionId(), sec);
        }
        c.setCourseSections(stringified);
    }

    /**
     * Gets list of course sections from their ids
     * @param ids   list of course section ids
     * @return      list of course sections
     */
    public ArrayList<courseSection> GetCourseSectionFromIds(ArrayList<String> ids) {
        ArrayList<courseSection> cs = new ArrayList<>();
        for(String id : ids) {
            courseSectionLookup csl = this.db.getCourseSection(id);
            if(csl != null && csl.value != null) {
                cs.add(csl.value);
            }
        }
        return cs;
    }
}

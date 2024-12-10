package models.general.items;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import models.general.people.courseSection;
import src.constants.DAYS;
import src.constants.TIMES;

public class Course {
    private String cid;
    private int creditHours;
    private Set<String> prereqs;
    private ArrayList<courseSection> courseSections;
    private String professorId;

    public Course() {
    }

    public Course(String cid, int creditHours, Set<String> prereqs){
        this.cid = cid;
        this.creditHours = creditHours;
        this.prereqs = new HashSet<>();
        this.prereqs.addAll(prereqs);
        this.courseSections = new ArrayList<>();
    }

    public Course(String cid, int creditHours){
        this.cid = cid;
        this.creditHours = creditHours;
        this.prereqs = new HashSet<>();
        this.courseSections = new ArrayList<>();
    }

    public Set<String> getPrereqs() {
        return prereqs;
    }
    public int getCreditHours() {
        return creditHours;
    }
    public String getCID() {
        return cid;
    }

    public ArrayList<courseSection> getCourseSections() {
        return courseSections;
    }
    public void setCourseSections(ArrayList<courseSection> sections) {
        this.courseSections = sections;
    }
    public void setCourseSections(courseSection section) {
        ArrayList<courseSection> sections = new ArrayList<>();
        sections.add(section);
        this.courseSections = sections;
    }

    public String GetProfessorId() {
        return this.professorId;
    }
    public void SetProfessorId(String profId) {
        this.professorId = profId;
    }

    public void AddPrerequisite(String classId) {
        if(this.prereqs == null) {
            this.prereqs = Set.of("classId");
        }
        this.prereqs.add(classId);
    }

    public void AddSection(TIMES timeOfClass, ArrayList<DAYS> daysOfClass) {
        courseSection classSection = new courseSection(stringifySection(daysOfClass, timeOfClass), daysOfClass, timeOfClass);

        if(courseSections == null) {
            this.courseSections = new ArrayList<>();
        }
        this.courseSections.add(classSection);
    }

    private String stringifySection(ArrayList<DAYS> days, TIMES time) {
        String retString = "";

        for (DAYS d : days) {
            retString = retString + d.label;
        }
        retString = retString + time.label;

        return retString;
    }

    /**
     * This function determines if this is a valid Course that is ready to be shown for student registration. For this to be the case,
     *   there must be a professor able to teach the course, and there needs to be at least one defined section.
     * @return TRUE if the course is valid (per the requirements given above), FALSE otherwise
     */
    public boolean IsValid() {
        if(professorId == null || courseSections == null) {
            return false;
        }
        if(courseSections.size() < 1) {
            return false;
        }
        return true;
    }
}

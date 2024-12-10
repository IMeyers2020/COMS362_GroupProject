package models.general.items;

import java.util.ArrayList;
import java.util.Set;

import models.general.people.courseSection;
import src.constants.DAYS;
import src.constants.TIMES;

public class Course {
    private String cid;
    private int creditHours;
    private ArrayList<String> prereqs;
    private ArrayList<String> courseSectionIds;
    private String professorId;

    public Course() {
    }

    public Course(String cid, int creditHours, Set<String> prereqs){
        this.cid = cid;
        this.creditHours = creditHours;
        this.prereqs = new ArrayList<>();
        this.prereqs.addAll(prereqs);
        this.courseSectionIds = new ArrayList<>();
    }

    public Course(String cid, int creditHours){
        this.cid = cid;
        this.creditHours = creditHours;
        this.prereqs = new ArrayList<>();
        this.courseSectionIds = new ArrayList<>();
    }

    public ArrayList<String> getPrereqs() {
        return prereqs;
    }
    public int getCreditHours() {
        return creditHours;
    }
    public String getCID() {
        return cid;
    }

    public ArrayList<String> getCourseSectionIds() {
        return courseSectionIds == null ? new ArrayList<>() : courseSectionIds;
    }
    public void setCourseSections(ArrayList<String> sections) {
        this.courseSectionIds = sections;
    }
    public void setCourseSections(String section) {
        ArrayList<String> sections = new ArrayList<>();
        sections.add(section);
        this.courseSectionIds = sections;
    }

    public String GetProfessorId() {
        return this.professorId;
    }
    public void SetProfessorId(String profId) {
        this.professorId = profId;
    }

    public void AddPrerequisite(String classId) {
        if(this.prereqs == null) {
            this.prereqs = new ArrayList<>(Set.of("classId"));
        }
        this.prereqs.add(classId);
    }

    public void AddSection(TIMES timeOfClass, ArrayList<DAYS> daysOfClass) {
        courseSection classSection = new courseSection(stringifySection(daysOfClass, timeOfClass), daysOfClass, timeOfClass);

        if(courseSectionIds == null) {
            this.courseSectionIds = new ArrayList<>();
        }
        this.courseSectionIds.add(stringifySection(daysOfClass, timeOfClass));
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
        if(professorId == null || courseSectionIds == null) {
            return false;
        }
        if(courseSectionIds.size() < 1) {
            return false;
        }
        return true;
    }
}

package models.general.items;

import java.util.ArrayList;
import java.util.Scanner;

import models.general.people.courseSection;
import src.constants.DAYS;
import src.constants.TIMES;

public class schedule {
    private final int maxCreditHours = 18;
    private int creditHours;
    private ArrayList<selectedCourse> courses;
    private String scheduleId;

    public schedule() {
        creditHours = 0;
        courses = new ArrayList<selectedCourse>();
    }

    public schedule(String schedId) {
        creditHours = 0;
        courses = new ArrayList<selectedCourse>();
        this.scheduleId = schedId;
    }

    public ArrayList<selectedCourse> getCourses() {
        return courses;
    }

    public boolean addCourse(Course c) {
        Scanner s = new Scanner(System.in);
        if(c.getCreditHours() + creditHours > maxCreditHours) {
            return false;
        }
        if(c.getCourseSections() == null || c.getCourseSections().size() == 0) {
            return false;
        }

        ArrayList<selectedCourse> courseClone = this.getCourses();
        
        for (selectedCourse cor : courseClone) {
            if (cor.getCourseId() == c.getCID())
                return false;
        }

        int count = 1;
        for(courseSection sec : c.getCourseSections()) {
            System.out.println(count + ": " + stringifySection(sec.getDays(), sec.getTime()));
            count++;
        }

        int index = 0;
        try {
            index = Integer.parseInt(s.nextLine()) - 1;
        } catch (Exception e) {
            return false;
        }

        courseSection selectedSection = c.getCourseSections().get(index);

        selectedCourse newSelectedCourse = new selectedCourse(c.getCID(), selectedSection.sectionId);
        courseClone.add(newSelectedCourse);
        
        this.setCourses(courseClone);
        this.setCreditHours(this.creditHours + c.getCreditHours());
        return true;
    }

    private String stringifySection(ArrayList<String> days, String time) {
        String retString = "";

        for (String d : days) {
            retString = retString + d;
        }
        retString = retString + " - " + time;

        return retString;
    }

    public void setCreditHours(int creds) {
        this.creditHours = creds;
    }

    public boolean removeCourse(Course c) {
        ArrayList<selectedCourse> cls = this.getCourses();
        cls.removeIf(cor -> cor.getCourseId() == c.getCID());
        this.setCourses(cls);
        return true;
    }

    public void setCourses(ArrayList<selectedCourse> cl) {
        this.courses = cl;
    }

    public String getScheduleId() {
        return this.scheduleId;
    }
    public void setScheduleId(String sId) {
        this.scheduleId = sId;
    }
}

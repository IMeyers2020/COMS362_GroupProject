package models.general.items;

import java.util.ArrayList;
import java.util.Scanner;

public class schedule {
    private final int maxCreditHours = 18;
    private int creditHours;
    private ArrayList<String> courseIds;
    private String scheduleId;
    private String studentId;

    public schedule() {
        creditHours = 0;
        courseIds = new ArrayList<String>();
    }

    public schedule(String schedId, String studId) {
        creditHours = 0;
        courseIds = new ArrayList<String>();
        this.scheduleId = schedId;
        this.studentId = studId;
    }

    public ArrayList<String> getCourses() {
        return courseIds;
    }

    public boolean addCourse(Course c, selectedCourse newSelectedCourse) {
        Scanner s = new Scanner(System.in);
        if(c.getCreditHours() + creditHours > maxCreditHours) {
            return false;
        }
        if(c.getCourseSectionIds() == null || c.getCourseSectionIds().size() == 0) {
            return false;
        }

        ArrayList<String> courseClone = this.getCourses() == null ? new ArrayList<>() : this.getCourses();

        System.out.println("Enter the section of " + c.getCID() + " you would like to register for");
        int count = 1;
        for(String sec : c.getCourseSectionIds()) {
            System.out.println(count + ": " + sec);
            count++;
        }

        int index = 0;
        try {
            index = Integer.parseInt(s.nextLine()) - 1;
        } catch (Exception e) {
            return false;
        }

        String selectedSection = c.getCourseSectionIds().get(index);

        newSelectedCourse.setCourseId(c.getCID());
        newSelectedCourse.setCourseSection(selectedSection);
        newSelectedCourse.setStudentId(studentId);
        if(newSelectedCourse.getCourseId() != null && newSelectedCourse.getCourseId().length() > 0) {
            courseClone.add(newSelectedCourse.getCourseId());
        }
        
        this.setCourses(courseClone);
        this.setCreditHours(this.creditHours + c.getCreditHours());
        return true;
    }

    public void setCreditHours(int creds) {
        this.creditHours = creds;
    }

    public boolean removeCourse(Course c) {
        ArrayList<String> cls = this.getCourses();
        cls.removeIf(cor -> cor.equals(c.getCID()));
        this.setCourses(cls);
        return true;
    }

    public void setCourses(ArrayList<String> cl) {
        this.courseIds = cl;
    }

    public String getScheduleId() {
        return this.scheduleId;
    }
    public void setScheduleId(String sId) {
        this.scheduleId = sId;
    }

    public String getStudentId() {
        return this.studentId;
    }
    public void setStudentId(String sId) {
        this.studentId = sId;
    }
}

package models.general.items;

import java.util.ArrayList;

public class schedule {
    private final int maxCreditHours = 18;
    private int creditHours;
    private ArrayList<courseLookup> courses;

    public schedule() {
        creditHours = 0;
        courses = new ArrayList<courseLookup>();
    }

    public ArrayList<courseLookup> getCourses() {
        return courses;
    }

    public boolean addCourse(Course c) {
        if(c.getCreditHours() + creditHours > maxCreditHours) {
            return false;
        }

        courseLookup courseToAdd = new courseLookup(c.getCID(), c);
        ArrayList<courseLookup> courseClone = getCourses();

        courseClone.add(courseToAdd);

        this.setCourses(courseClone);
        this.setCreditHours(this.creditHours + c.getCreditHours());
        return true;
    }

    public void setCreditHours(int creds) {
        this.creditHours = creds;
    }

    public boolean removeCourse(Course c) {
        ArrayList<courseLookup> cls = this.getCourses();
        cls.removeIf(cor -> cor.value.getCID() == c.getCID());
        setCourses(cls);
        return true;
    }

    public void setCourses(ArrayList<courseLookup> cl) {
        this.courses = cl;
    }
}

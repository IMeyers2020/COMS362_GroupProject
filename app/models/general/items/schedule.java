package models.general.items;

import java.util.ArrayList;

public class schedule {
    private final int maxCreditHours = 18;
    private int creditHours;
    private ArrayList<String> courses;

    public schedule() {
        creditHours = 0;
        courses = new ArrayList<String>();
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public boolean addCourse(Course c) {
        if(c.getCreditHours() + creditHours > maxCreditHours) {
            return false;
        }

        ArrayList<String> courseClone = this.getCourses();
        
        for (String cor : courseClone) {
            if (cor == c.getCID())
                return false;
        }

        courseClone.add(c.getCID());
        
        this.setCourses(courseClone);
        this.setCreditHours(this.creditHours + c.getCreditHours());
        return true;
    }

    public void setCreditHours(int creds) {
        this.creditHours = creds;
    }

    public boolean removeCourse(Course c) {
        ArrayList<String> cls = this.getCourses();
        cls.removeIf(cor -> cor == c.getCID());
        this.setCourses(cls);
        return true;
    }

    public void setCourses(ArrayList<String> cl) {
        this.courses = cl;
    }
}

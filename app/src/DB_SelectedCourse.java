package src;

import java.util.ArrayList;

import models.general.items.selectedCoursesLookup;

public class DB_SelectedCourse {
    public ArrayList<selectedCoursesLookup> courses;

    public DB_SelectedCourse() {
    }

    public void setSelectedCourses(ArrayList<selectedCoursesLookup> _courses) {
        this.courses = _courses;
    }
}

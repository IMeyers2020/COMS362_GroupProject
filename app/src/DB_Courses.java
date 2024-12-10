package src;

import java.util.ArrayList;

import models.general.items.courseLookup;

public class DB_Courses {
    public ArrayList<courseLookup> courses;

    public DB_Courses() {
    }

    public void setCourses(ArrayList<courseLookup> _courses) {
        this.courses = _courses;
    }
}

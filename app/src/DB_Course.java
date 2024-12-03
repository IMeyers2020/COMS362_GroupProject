package src;

import java.util.ArrayList;

import models.general.items.courseLookup;

public class DB_Course {
    public ArrayList<courseLookup> courses;

    public DB_Course() {
    }

    public void setCourses(ArrayList<courseLookup> _courses) {
        this.courses = _courses;
    }
}

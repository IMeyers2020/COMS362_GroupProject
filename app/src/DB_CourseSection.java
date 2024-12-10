package src;

import java.util.ArrayList;

import models.general.items.courseSectionLookup;

public class DB_CourseSection {
    public ArrayList<courseSectionLookup> sections;

    public DB_CourseSection() {
    }

    public void setSections(ArrayList<courseSectionLookup> _sections) {
        this.sections = _sections;
    }
}

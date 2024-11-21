package src;

import java.util.ArrayList;
import models.general.people.studentLookup;

public class DB_Student {
    public ArrayList<studentLookup> students;

    public DB_Student() {
    }

    public void setStudents(ArrayList<studentLookup> _students) {
        this.students = _students;
    }
}

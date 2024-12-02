package models.academics;

import java.util.ArrayList;

import models.general.items.scheduleLookup;
import models.general.people.student;
import models.general.people.studentLookup;
import src.DatabaseSupport;

public class StudentController {
    DatabaseSupport db;

    public StudentController(DatabaseSupport _db) {
        this.db = _db;
    }

    public boolean updateStudent(student stud) {
        return this.db.updateStudent(stud.getStudentId(), stud);
    }

    public studentLookup getStudent(String sid) {
        ArrayList<studentLookup> filtered = this.db.getStudents();
        filtered.removeIf(s -> !(s.key.equals(sid)));

        if(filtered.size() == 0) {
            return null;
        }

        return filtered.get(0);
    };

    public ArrayList<studentLookup> getAllStudents() {
        return this.db.getStudents();
    }

    public scheduleLookup getScheduleForStudent(student stud) {
        scheduleLookup foundSched;

        foundSched = db.getSchedule(stud.getScheduleId());

        return foundSched;
    }
}

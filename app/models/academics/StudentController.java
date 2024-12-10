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

    // move student from studentdb to expelleddb
    public boolean expelStudent(String sid, String reason) {
        studentLookup s = this.db.getStudent(sid);
        s.value.setExplusionNote(reason);
        // if student is added to expelled db and removed from student db return true
        return this.db.addExpelledStudent(sid, s.value) && this.db.removeStudent(sid);
    }

    // move student from expelleddb to studentdb
    public boolean unexpelStudent(String sid) {
        studentLookup s = this.db.getExpelledStudent(sid);
        s.value.setExplusionNote(null);
        // if student is added to student db and removed from expelled db return true
        return this.db.addStudent(sid, s.value) && this.db.removeExpelledStudent(sid);
    }
}

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

    /**
     * Gets studentlookup object from database with provided ID
     * @param sid   student ID
     * @return      studentlookup object, contains student object
     */
    public studentLookup getStudent(String sid) {
        ArrayList<studentLookup> filtered = this.db.getStudents();
        filtered.removeIf(s -> !(s.key.equals(sid)));

        if(filtered.size() == 0) {
            return null;
        }

        return filtered.get(0);
    };

    /**
     * Gets all students currently in database
     * @return  list of studentlookup objects
     */
    public ArrayList<studentLookup> getAllStudents() {
        return this.db.getStudents();
    }

    /**
     * Gets schedule of student
     * @param stud  student
     * @return      schedulelookup object of provided student
     */
    public scheduleLookup getScheduleForStudent(student stud) {
        scheduleLookup foundSched;

        foundSched = db.getSchedule(stud.getScheduleId());

        return foundSched;
    }

    /**
     * Expels student from university, puts them in expelled student database
     * @param sid       student ID
     * @param reason    string, reason for expulsion
     * @return          true if student is successfully expelled
     */
    public boolean expelStudent(String sid, String reason) {
        studentLookup s = this.db.getStudent(sid);
        s.value.setExplusionNote(reason);
        // if student is added to expelled db and removed from student db return true
        return this.db.addExpelledStudent(sid, s.value) && this.db.removeStudent(sid);
    }

    /**
     * Unexpells student from university, returning them to main student database
     * @param sid   student ID
     * @return      true if student is successfully unexpelled
     */
    public boolean unexpelStudent(String sid) {
        studentLookup s = this.db.getExpelledStudent(sid);
        // wipe explusion note, used as way of checking explusion boolean
        s.value.setExplusionNote(null);
        // if student is added to student db and removed from expelled db return true
        return this.db.addStudent(sid, s.value) && this.db.removeExpelledStudent(sid);
    }
}

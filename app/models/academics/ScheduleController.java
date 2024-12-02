package models.academics;

import java.util.ArrayList;

import models.general.items.schedule;
import models.general.people.studentLookup;
import src.DatabaseSupport;

public class ScheduleController {
    DatabaseSupport db;

    public ScheduleController(DatabaseSupport _db) {
        this.db = _db;
    }

    public boolean updateSchedule(schedule stud) {
        return this.db.updateSchedule(stud.getScheduleId(), stud);
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
}

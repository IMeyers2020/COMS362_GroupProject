package src;

import java.util.ArrayList;

import models.general.items.scheduleLookup;

public class DB_Schedule {
    public ArrayList<scheduleLookup> schedules;

    public DB_Schedule() {
    }

    public void setSchedules(ArrayList<scheduleLookup> _scheds) {
        this.schedules = _scheds;
    }
}

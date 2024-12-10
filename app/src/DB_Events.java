package src;

import java.util.ArrayList;

import models.general.items.sportslookup;

public class DB_Events {
    public ArrayList<sportslookup> sports;

    public DB_Events() {
    }

    public void setEvents(ArrayList<sportslookup> events) {
        this.sports = events;
    }
}
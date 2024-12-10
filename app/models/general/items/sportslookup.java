package models.general.items;

import models.athletics.events;

public class sportslookup {
    public String key;
    public events value;

    // Default no-arg constructor
    public sportslookup() {}

    // Parameterized constructor
    public sportslookup(String key, events value) {
        this.key = key;
        this.value = value;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String getKey() {
        return key;
    }

    public void setValue(events value) {
        this.value = value;
    }
    public events getValue() {
        return value;
    }
}
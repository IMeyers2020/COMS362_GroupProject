package models.general.items;

import models.general.people.courseSection;

public class courseSectionLookup {
    public String key;
    public courseSection value;

    public courseSectionLookup() {
    }

    public courseSectionLookup(String key, courseSection val) {
        this.key = key;
        this.value = val;
    }
}

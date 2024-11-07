package app.models.general.items;

import java.util.HashSet;
import java.util.Set;

public class course {
    private int creditHours;
    private Set<course> prereqs;

    public course(int creditHours, Set<course> prereqs){
        this.creditHours = creditHours;
        this.prereqs = new HashSet<>();
        this.prereqs.addAll(prereqs);
    }

    public course(int creditHours){
        this.creditHours = creditHours;
        this.prereqs = new HashSet<>();
    }

    public Set<course> getPrereqs() {
        return prereqs;
    }
    public int getCreditHours() {
        return creditHours;
    }
}

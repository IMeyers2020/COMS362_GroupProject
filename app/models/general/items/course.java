package app.models.general.items;

import java.util.HashSet;
import java.util.Set;

public class course {
    private String name;
    private int creditHours;
    private Set<String> prereqs;

    public course(String name, int creditHours, Set<String> prereqs){
        this.name = name;
        this.creditHours = creditHours;
        this.prereqs = new HashSet<>();
        this.prereqs.addAll(prereqs);
    }

    public course(String name, int creditHours){
        this.name = name;
        this.creditHours = creditHours;
        this.prereqs = new HashSet<>();
    }

    public Set<String> getPrereqs() {
        return prereqs;
    }
    public int getCreditHours() {
        return creditHours;
    }
    public String getName() {
        return name;
    }
}

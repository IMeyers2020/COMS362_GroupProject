package app.models.general.items;

import java.util.List;

public class course {
    private int creditHours;
    private List<course> prereqs;

    public List<course> getPrereqs() {
        return prereqs;
    }
    public int getCreditHours() {
        return creditHours;
    }
}

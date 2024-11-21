package models.general.items;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Set;

public class Major {
    private String id;
    private String name;
    private String degreeType;
    private int totalRequiredCredits;
    private Set<String> requiredCourses;

    public Major(String id, String name, String degreeType, int totalRequiredCredits, Set<String> requiredCourses) {
        this.id = id;
        this.name = name;
        this.degreeType = degreeType;
        this.totalRequiredCredits = totalRequiredCredits;
        this.requiredCourses = requiredCourses;
    }

    public String getMajorID() {
        return this.id;
    }

    public String getMajorName() {
        return this.name;
    }

    public String getDegreeType() {
        return this.degreeType;
    }

    public int getTotalRequiredCredits() {
        return this.totalRequiredCredits;
    }

    public Set<String> getRequiredCourses() {
        return this.requiredCourses;
    }
    
    public void createRequiredCourses() {
        String toFile = "Required Courses for " + this.name + "\n";
        for (String course : getRequiredCourses()) {
            toFile += course + "\n";
        }
        try {
            Files.writeString(Paths.get("./RequiredCourses.txt"), toFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

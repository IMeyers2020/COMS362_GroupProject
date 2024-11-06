package app.models.academics.academicColleges.business;

import java.util.Arrays;
import java.util.List;

import app.models.academics.academicColleges.collegeInfo;
import app.models.academics.academicColleges.genericCollege;

public class businessCollege extends genericCollege implements collegeInfo {
    public final List<String> majors = Arrays.asList(new String[]{"Finance", "bar"});

    @Override
    public String getName() {
        return "College of Business";
    }

    @Override
    public String[] getMajors() {
        return new String[]{"Finance", "bar"};
    }
    
}

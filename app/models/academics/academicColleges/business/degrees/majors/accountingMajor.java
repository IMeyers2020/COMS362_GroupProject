package app.models.academics.academicColleges.business.degrees.majors;

import app.models.academics.academicColleges.genericMajor;

public class accountingMajor implements genericMajor {

    @Override
    public String getName() {
        return "Accounting";
    }

    @Override
    public String getMajorNumber() {
        return "001";
    }

    @Override
    public String getDescription() {
        return "This is the accounting Major";
    }
    
}

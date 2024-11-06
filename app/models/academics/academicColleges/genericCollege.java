package app.models.academics.academicColleges;

public abstract class genericCollege {
    public abstract String[] getMajors();

    public void printMajors() {
        String[] majors = this.getMajors();

        // For each major in the college, print its number and name
        for(int i = 0; i < majors.length; i++) {
            System.out.println(String.format("%d. %s", i + 1, majors[i]));
        }
    };
}

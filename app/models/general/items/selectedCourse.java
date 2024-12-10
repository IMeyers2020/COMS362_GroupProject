package models.general.items;

public class selectedCourse {
    private String courseId;
    private String courseSection;
    private String studentId;

    public selectedCourse(){}

    public selectedCourse(String courseId, String courseSection, String studentId) {
        this.courseId = courseId;
        this.courseSection = courseSection;
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getStudentId() {
        return studentId;
    }
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseSection() {
        return courseSection;
    }
    public void setCourseSection(String courseSection) {
        this.courseSection = courseSection;
    }
}

package models.general.items;

public class selectedCourse {
    private String courseId;
    private String courseSection;

    selectedCourse(){}

    selectedCourse(String courseId, String courseSection) {
        this.courseId = courseId;
        this.courseSection = courseSection;
    }

    public String getCourseId() {
        return courseId;
    }
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseSection() {
        return courseSection;
    }
    public void setCourseSection(String courseSection) {
        this.courseSection = courseSection;
    }
}

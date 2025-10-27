package Business.Academics;

public class CourseOffer {
    private final Course course;
    private final String section;
    private Object faculty; 

    public CourseOffer(Course course, String section) {
        this.course = course;
        this.section = section;
    }

    public Course getCourse() { return course; }
    public String getSection() { return section; }

    public void setFaculty(Object faculty) { this.faculty = faculty; }
    public Object getFaculty() { return faculty; }

    @Override
    public String toString() {
        String num = course != null ? course.getNumber() : "?";
        return num + "-" + section;
    }
}
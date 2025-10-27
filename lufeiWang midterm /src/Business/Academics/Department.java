package Business.Academics;

public class Department {
    private final String code;
    private final String name;
    private final CourseCatalog courseCatalog = new CourseCatalog();

    public Department(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public CourseCatalog getCourseCatalog() { return courseCatalog; }
    public CourseCatalog getCourseDirectory() { return courseCatalog; }

    public String getCode() { return code; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return code + " - " + name;
    }
}
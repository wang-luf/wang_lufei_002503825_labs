package Business.Academics;

import java.util.ArrayList;
import java.util.List;

public class CourseCatalog {
    private final List<Course> courseList = new ArrayList<>();

    public Course newCourse(String number, String name) {
        Course c = new Course(number, name);
        courseList.add(c);
        return c;
    }

    public Course newCourse(String number) {
        return newCourse(number, number);
    }

    public List<Course> getCourseList() { return courseList; }

    @Override
    public String toString() {
        return "CourseCatalog(" + courseList.size() + ")";
    }
}
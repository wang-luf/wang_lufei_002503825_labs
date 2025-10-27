package Business.Academics;

import java.util.ArrayList;
import java.util.List;

public class SemesterDirectory {
    private final List<CourseSchedule> schedules = new ArrayList<>();

    public CourseSchedule newSemester(String name) {
        CourseSchedule cs = new CourseSchedule(name);
        schedules.add(cs);
        return cs;
    }

    public CourseSchedule newTerm(String name) {
        return newSemester(name);
    }

    public CourseSchedule newCourseSchedule(String name) {
        return newSemester(name);
    }

    public List<CourseSchedule> getSchedules() {
        return schedules;
    }

    public List<CourseSchedule> getSemesterList() {
        return schedules;
    }

    public List<CourseSchedule> getCourseScheduleList() {
        return schedules;
    }

    @Override
    public String toString() {
        return "SemesterDirectory(" + schedules.size() + ")";
    }
}
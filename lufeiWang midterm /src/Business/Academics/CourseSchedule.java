package Business.Academics;

import java.util.ArrayList;
import java.util.List;

public class CourseSchedule {
    private final String name;
    private final List<CourseOffer> offerList = new ArrayList<>();

    public CourseSchedule(String name) {
        this.name = name;
    }

    public CourseOffer newCourseOffer(Course course, String section) {
        CourseOffer co = new CourseOffer(course, section);
        offerList.add(co);
        return co;
    }

    public List<CourseOffer> getCourseOfferList() { return offerList; }
    public List<CourseOffer> getCourseOffers() { return offerList; }
    public List<CourseOffer> getOffers() { return offerList; }
    public List<CourseOffer> getOfferList() { return offerList; }

    public String getName() { return name; }

    @Override
    public String toString() {
        return name;
    }
}
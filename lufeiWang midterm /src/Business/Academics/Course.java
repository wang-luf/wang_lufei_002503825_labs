package Business.Academics;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private final String number;
    private final String name;
    private final List<CourseOffer> offers = new ArrayList<>();

    public Course(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public String getNumber() { return number; }
    public String getName() { return name; }

    public CourseOffer newCourseOffer(String section) {
        CourseOffer co = new CourseOffer(this, section);
        offers.add(co);
        return co;
    }

    public List<CourseOffer> getCourseOfferList() { return offers; }
    public List<CourseOffer> getCourseOffers() { return offers; }

    @Override
    public String toString() {
        return number + " " + name;
    }
}
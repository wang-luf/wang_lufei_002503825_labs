package Business.Academics;

import java.util.ArrayList;
import java.util.List;

public class DepartmentCatalog {
    private final List<Department> departments = new ArrayList<>();

    public Department newDepartment(String code, String name) {
        Department d = new Department(code, name);
        departments.add(d);
        return d;
    }

    public Department newDepartment(String name) {
        return newDepartment(name, name);
    }

    public List<Department> getDepartments() {
        return departments;
    }

    @Override
    public String toString() {
        return "DepartmentCatalog(" + departments.size() + ")";
    }
}
package Business;

import Business.Person.PersonDirectory;
import Business.Profiles.EmployeeDirectory;
import Business.Profiles.StudentDirectory;
import Business.UserAccounts.UserAccountDirectory;
import Business.Academics.DepartmentCatalog;
import Business.Academics.SemesterDirectory;

public class Business {

    String name;
    PersonDirectory persondirectory;
    EmployeeDirectory employeedirectory;
    UserAccountDirectory useraccountdirectory;
    StudentDirectory studentdirectory;
    DepartmentCatalog departmentcatalog;
    SemesterDirectory semesterDirectory;

    public Business(String n) {
        name = n;
        persondirectory = new PersonDirectory();
        employeedirectory = new EmployeeDirectory(this);
        useraccountdirectory = new UserAccountDirectory();
        studentdirectory = new StudentDirectory();
        departmentcatalog = new DepartmentCatalog();
        semesterDirectory = new SemesterDirectory();
    }

    public PersonDirectory getPersonDirectory() { return persondirectory; }
    public UserAccountDirectory getUserAccountDirectory() { return useraccountdirectory; }
    public EmployeeDirectory getEmployeeDirectory() { return employeedirectory; }
    public StudentDirectory getStudentDirectory(){ return studentdirectory; }
    public DepartmentCatalog getDepartmentCatalog() { return departmentcatalog; }
    public SemesterDirectory getSemesterDirectory() { return semesterDirectory; }
}
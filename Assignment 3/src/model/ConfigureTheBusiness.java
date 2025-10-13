/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class ConfigureTheBusiness {
    public static LibrarySystem init() {
        LibrarySystem sys = new LibrarySystem();

        Branch b1 = sys.createBranch("Downtown", 101);
        Branch b2 = sys.createBranch("Uptown", 202);

        sys.createUser("admin", "admin", "System Admin", new Role.SystemAdminRole());

        UserAccount m1 = sys.createUser("mgr1", "mgr1", "Alice Manager", new Role.BranchManagerRole());
        m1.setManagedBranch(b1);
        m1.setEmployeeId(10001);
        m1.setExperienceYears(5);

        UserAccount m2 = sys.createUser("mgr2", "mgr2", "Bob Manager", new Role.BranchManagerRole());
        m2.setManagedBranch(b2);
        m2.setEmployeeId(10002);
        m2.setExperienceYears(3);

        for (int i = 1; i <= 5; i++) {
            sys.createUser("c" + i, "c" + i, "Customer" + i, new Role.CustomerRole());
        }

        Author a1 = sys.addAuthorToLibrary(b1, "J. K. Author");
        b1.getLibrary().addBook("Data Structures", 420, "EN", a1);
        b1.getLibrary().addBook("Operating Systems", 530, "EN", a1);

        Author a2 = sys.addAuthorToLibrary(b2, "Martin Fowler");
        b2.getLibrary().addBook("Refactoring", 460, "EN", a2);

        return sys;
    }
}

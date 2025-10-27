package Business;

import Business.Person.PersonDirectory;
import Business.Profiles.EmployeeDirectory;
import Business.Profiles.StudentDirectory;
import Business.UserAccounts.UserAccountDirectory;
import java.util.ArrayList;
import java.util.List;


public final class DataSeeder {

    private DataSeeder() {}

    public static void seed(Business b) {
        // 1) Directories from Business
        PersonDirectory personDir   = b.getPersonDirectory();
        EmployeeDirectory employeeDir = b.getEmployeeDirectory();
        StudentDirectory studentDir   = b.getStudentDirectory();
        UserAccountDirectory uad      = b.getUserAccountDirectory();

        // 2) Create >=30 persons and KEEP references
        List<Object> studentPersons  = new ArrayList<>(); // S1..S10
        List<Object> employeePersons = new ArrayList<>(); // E1..E10
        List<Object> extraPersons    = new ArrayList<>(); // X1..X9
        Object adminPerson;

        for (int i = 1; i <= 10; i++) {
            Object p = safeNewPerson(personDir, "S" + i, "Student_" + i, "s" + i + "@uni.edu");
            studentPersons.add(p);
        }
        for (int i = 1; i <= 10; i++) {
            Object p = safeNewPerson(personDir, "E" + i, "Employee_" + i, "e" + i + "@uni.edu");
            employeePersons.add(p);
        }
        adminPerson = safeNewPerson(personDir, "A1", "Admin_1", "admin@uni.edu");

        for (int i = 1; i <= 9; i++) {
            Object p = safeNewPerson(personDir, "X" + i, "Extra_" + i, "x" + i + "@uni.edu");
            extraPersons.add(p);
        }

        // 3) Create profiles USING the Person objects above (no more lookups)
        List<Object> studentProfiles  = new ArrayList<>();
        for (Object sp : studentPersons) {
            studentProfiles.add(safeNewStudentProfile(studentDir, sp));
        }

        List<Object> employeeProfiles = new ArrayList<>();
        for (Object ep : employeePersons) {
            employeeProfiles.add(safeNewEmployeeProfile(employeeDir, ep));
        }

        Object adminProfile = safeNewEmployeeProfile(employeeDir, adminPerson); // Admin uses EmployeeProfile

        // 4) Create sample user accounts (bind to profiles we already have)
        // Admin
        safeNewUserAccount(uad, "admin@uni.edu", "admin123", adminProfile);

        // Faculty (use first employee)
        if (!employeeProfiles.isEmpty()) {
            safeNewUserAccount(uad, "f_alice@uni.edu", "pass123", employeeProfiles.get(0));
        }

        // Student (use first student)
        if (!studentProfiles.isEmpty()) {
            safeNewUserAccount(uad, "s_bob@uni.edu", "pass123", studentProfiles.get(0));
        }
        
        seedAcademics(b);
    }
    
    // ====== Academic seeding: Department / Course / Semester / CourseOffer ======
    private static void seedAcademics(Business b) {
        Object deptCatalog = coalesce(
                tryGet(b, "getDepartmentCatalog"),
                tryGet(b, "getDepartmentDirectory"),
                tryGet(b, "getDepartments")
        );
        if (deptCatalog == null) {
            System.out.println("[Seeder] No Department catalog/directory getter on Business");
            return;
        }

        Object cs = safeNewDepartment(deptCatalog, "CS", "Computer Science");
        if (cs == null) {
            System.out.println("[Seeder] Failed to create Department");
            return;
        }

        Object courseCatalog = coalesce(
                tryGet(cs, "getCourseCatalog"),
                tryGet(cs, "getCourseDirectory")
        );
        if (courseCatalog == null) {
            System.out.println("[Seeder] No Course catalog/directory on Department");
            return;
        }

        Object cs5001 = safeNewCourse(courseCatalog, "CS5001", "Algorithms");
        Object cs5200 = safeNewCourse(courseCatalog, "CS5200", "Database Systems");
        if (cs5001 == null || cs5200 == null) {
            System.out.println("[Seeder] Failed to create Course(s)");
            return;
        }

        Object semDir = coalesce(
                tryGet(b, "getSemesterDirectory"),
                tryGet(b, "getTermDirectory"),
                tryGet(b, "getMasterCourseSchedule")
        );
        Object fall = safeNewSemester(semDir, "Fall 2025");
        if (fall == null) {
            System.out.println("[Seeder] Failed to create Semester/Term");
            return;
        }

        Object off1 = safeNewCourseOffer(fall, cs5001, "01");
        Object off2 = safeNewCourseOffer(fall, cs5200, "01");
        if (off1 == null || off2 == null) {
            System.out.println("[Seeder] Failed to create CourseOffer(s)");
        }
    }

    // Create Department by common signatures: newDepartment(code,name) or newDepartment(name)
    private static Object safeNewDepartment(Object deptCatalog, String code, String name) {
        try {
            try {
                return deptCatalog.getClass()
                        .getMethod("newDepartment", String.class, String.class)
                        .invoke(deptCatalog, code, name);
            } catch (NoSuchMethodException e1) {
                return deptCatalog.getClass()
                        .getMethod("newDepartment", String.class)
                        .invoke(deptCatalog, name);
            }
        } catch (Exception e) {
            return null;
        }
    }

    // Create Course by common signatures: newCourse(number,name) or newCourse(number)
    private static Object safeNewCourse(Object courseCatalog, String number, String name) {
        try {
            try {
                return courseCatalog.getClass()
                        .getMethod("newCourse", String.class, String.class)
                        .invoke(courseCatalog, number, name);
            } catch (NoSuchMethodException e1) {
                return courseCatalog.getClass()
                        .getMethod("newCourse", String.class)
                        .invoke(courseCatalog, number);
            }
        } catch (Exception e) {
            return null;
        }
    }

    // Create Semester/Term: try newSemester(name) / newTerm(name) / newCourseSchedule(name)
    private static Object safeNewSemester(Object semDir, String name) {
        if (semDir == null) return null;
        String[] tries = {"newSemester", "newTerm", "newCourseSchedule"};
        for (String m : tries) {
            try {
                return semDir.getClass().getMethod(m, String.class).invoke(semDir, name);
            } catch (Exception ignore) {}
        }
        return null;
    }

    // Create CourseOffer: prefer schedule.newCourseOffer(course, section) or course.newCourseOffer(section)
    private static Object safeNewCourseOffer(Object semesterOrSchedule, Object course, String section) {
        if (course == null || semesterOrSchedule == null) return null;
        try {
            try {
                return semesterOrSchedule.getClass()
                        .getMethod("newCourseOffer", course.getClass(), String.class)
                        .invoke(semesterOrSchedule, course, section);
            } catch (NoSuchMethodException e1) {
                return course.getClass()
                        .getMethod("newCourseOffer", String.class)
                        .invoke(course, section);
            }
        } catch (Exception e) {
            return null;
        }
    }

    // ===== small reflection helpers =====
    private static Object tryGet(Object target, String getter) {
        if (target == null) return null;
        try { return target.getClass().getMethod(getter).invoke(target); }
        catch (Exception e) { return null; }
    }
    private static Object coalesce(Object... xs) {
        for (Object x : xs) if (x != null) return x;
        return null;
    }


    // ======================= Reflection helpers =======================

    /** Create a Person using either newPerson(id,name,email) or newPerson(name). */
    private static Object safeNewPerson(Object personDir, String id, String name, String email) {
        try {
            try {
                // Many templates: newPerson(String id, String name, String email)
                return personDir.getClass()
                        .getMethod("newPerson", String.class, String.class, String.class)
                        .invoke(personDir, id, name, email);
            } catch (NoSuchMethodException e1) {
                // Some templates: newPerson(String name)
                return personDir.getClass()
                        .getMethod("newPerson", String.class)
                        .invoke(personDir, name);
            }
        } catch (Exception e) {
            throw new RuntimeException("Adjust safeNewPerson() to your PersonDirectory API", e);
        }
    }

    /** Create a StudentProfile from a Person. No null person allowed. */
    private static Object safeNewStudentProfile(Object studentDir, Object person) {
        if (person == null) {
            throw new RuntimeException("Student person is null; ensure you keep the Person returned by newPerson(...)");
        }
        try {
            try {
                // Common: newStudentProfile(Person p)
                return studentDir.getClass()
                        .getMethod("newStudentProfile", person.getClass())
                        .invoke(studentDir, person);
            } catch (NoSuchMethodException e1) {
                // Some: addStudent(Person p)
                return studentDir.getClass()
                        .getMethod("addStudent", person.getClass())
                        .invoke(studentDir, person);
            }
        } catch (Exception e) {
            throw new RuntimeException("Adjust safeNewStudentProfile() to match your StudentDirectory API", e);
        }
    }

    /** Create an EmployeeProfile from a Person. */
    private static Object safeNewEmployeeProfile(Object employeeDir, Object person) {
        if (person == null) {
            throw new RuntimeException("Employee person is null; ensure you keep the Person returned by newPerson(...)");
        }
        try {
            try {
                // Common: newEmployeeProfile(Person p)
                return employeeDir.getClass()
                        .getMethod("newEmployeeProfile", person.getClass())
                        .invoke(employeeDir, person);
            } catch (NoSuchMethodException e1) {
                // Some: addEmployee(Person p)
                return employeeDir.getClass()
                        .getMethod("addEmployee", person.getClass())
                        .invoke(employeeDir, person);
            }
        } catch (Exception e) {
            throw new RuntimeException("Adjust safeNewEmployeeProfile() to match your EmployeeDirectory API", e);
        }
    }


    /** Create a UserAccount using the exact signature discovered:
     *  newUserAccount(Profile, String username, String password)
     */
    private static Object safeNewUserAccount(Object uad, String username, String password, Object profile) {
        if (profile == null) {
            throw new RuntimeException("Profile is null when creating user account for: " + username);
        }
        try {
            // Try with the concrete Profile type first
            try {
                return uad.getClass()
                        .getMethod("newUserAccount", profile.getClass(), String.class, String.class)
                        .invoke(uad, profile, username, password);
            } catch (NoSuchMethodException e1) {
                // Fallback to the base type Business.Profiles.Profile
                Class<?> profileBase = Class.forName("Business.Profiles.Profile");
                return uad.getClass()
                        .getMethod("newUserAccount", profileBase, String.class, String.class)
                        .invoke(uad, profile, username, password);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to call newUserAccount(Profile, String, String). Adjust if your API differs.", e);
        }
    }

    
    /** Try to load a class by FQCN; returns null if not present. */
    private static Class<?> tryLoadClass(String fqcn) {
        try { return Class.forName(fqcn); }
        catch (ClassNotFoundException e) { return null; }
    }

    /** If the class has valueOf(String) (enum-like), try to get a constant by name. */
    private static Object tryValueOf(Class<?> clazz, String name) {
        try {
            java.lang.reflect.Method m = clazz.getMethod("valueOf", String.class);
            return m.invoke(null, name);
        } catch (Exception e) {
            return null;
        }
    }

    /** Very rough role inference from profile type or username. */
    private static String inferRoleFromProfile(Object profile) {
        if (profile == null) return "User";
        String simple = profile.getClass().getSimpleName().toLowerCase();
        if (simple.contains("student")) return "Student";
        if (simple.contains("admin"))   return "Admin";
        if (simple.contains("faculty") || simple.contains("employee")) return "Faculty";
        return "User";
    }

    /** Print all public methods that look like "create user account" to console for debugging. */
    private static void dumpUserAccountAPIs(Class<?> uadClass) {
        System.out.println("==== DEBUG: Public methods in " + uadClass.getName() + " ====");
        for (java.lang.reflect.Method m : uadClass.getMethods()) {
            if (m.getName().toLowerCase().contains("useraccount")) {
                StringBuilder sb = new StringBuilder();
                sb.append(m.getName()).append("(");
                Class<?>[] pts = m.getParameterTypes();
                for (int i = 0; i < pts.length; i++) {
                    if (i > 0) sb.append(", ");
                    sb.append(pts[i].getName());
                }
                sb.append(")");
                System.out.println(sb.toString());
            }
        }
        System.out.println("==== END DEBUG ====");
    }


}

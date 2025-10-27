/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AccessControl;

/**
 *
 * @author l
 */
public final class Authorization {

    private Authorization() {}

    public static void requireRole(Session session, String... roles) {
        if (session == null || !session.isActive()) {
            throw new SecurityException("Not authenticated.");
        }
        if (roles == null || roles.length == 0) return;

        String my = session.getRoleName();
        if (my == null) {
            throw new SecurityException("Unknown role.");
        }
        for (String r : roles) {
            if (r != null && my.equalsIgnoreCase(r)) {
                return; 
            }
        }
        throw new SecurityException("Access denied. Required role: " + String.join("/", roles));
    }

    public static void requireAdmin(Session session) {
        requireRole(session, "Admin");
    }

    public static void requireFaculty(Session session) {
        requireRole(session, "Faculty");
    }

    public static void requireStudent(Session session) {
        requireRole(session, "Student");
    }

    public static void requireAdminOrFaculty(Session session) {
        requireRole(session, "Admin", "Faculty");
    }

    public static void requireAdminOrStudent(Session session) {
        requireRole(session, "Admin", "Student");
    }

    public static void requireFacultyOrStudent(Session session) {
        requireRole(session, "Faculty", "Student");
    }
}

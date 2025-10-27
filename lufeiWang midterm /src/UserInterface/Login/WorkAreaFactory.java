// UserInterface.Login.WorkAreaFactory.java
package UserInterface.Login;

import AccessControl.Session;
import Business.Business;
import Business.Profiles.EmployeeProfile;
import Business.Profiles.StudentProfile;
import javax.swing.JPanel;
import UserInterface.WorkAreas.AdminRole.AdminRoleWorkAreaJPanel;
import UserInterface.WorkAreas.FacultyRole.FacultyWorkAreaJPanel;
import UserInterface.WorkAreas.StudentRole.StudentWorkAreaJPanel;

public class WorkAreaFactory {

    public static JPanel createForRole(Session session, JPanel mainContainer, Business business) {

        String uname = safeUserName(session);
        if ("admin@uni.edu".equalsIgnoreCase(uname)) {
            return new AdminRoleWorkAreaJPanel(mainContainer, business, session);
        }

        Object p = session.getProfile();
        if (p instanceof EmployeeProfile) {
            return new FacultyWorkAreaJPanel(mainContainer, business, session);
        }
        if (p instanceof StudentProfile) {
            return new StudentWorkAreaJPanel(mainContainer, business, session);
        }

        return new javax.swing.JPanel();
    }

    private static String safeUserName(Session s) {
        try { 
            return String.valueOf(s.getAccount().getUserLoginName());
        } catch (Throwable t) {
            return "";
        }
    }
}
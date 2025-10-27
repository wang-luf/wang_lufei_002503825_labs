/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AccessControl;
import Business.Profiles.Profile;
import Business.UserAccounts.UserAccount;

/**
 *
 * @author l
 */
public class Session {

    private final UserAccount account;
    private boolean active = true;

    public Session(UserAccount account) {
        if (account == null) {
            throw new IllegalArgumentException("UserAccount cannot be null");
        }
        this.account = account;
    }

    public boolean isActive() {
        return active;
    }

    public UserAccount getAccount() {
        return account;
    }

    public String getRoleName() {
        Profile p = getProfile();
        return p == null ? null : p.getRole();
    }

    public Profile getProfile() {
        try {
            return account.getAssociatedPersonProfile();
        } catch (Throwable t) {
            return null;
        }
    }

    public boolean isInRole(String role) {
        if (!active || role == null) return false;
        String my = getRoleName();
        return my != null && my.equalsIgnoreCase(role);
    }

    public boolean isAdmin()   { return isInRole("Admin"); }
    public boolean isFaculty() { return isInRole("Faculty"); }
    public boolean isStudent() { return isInRole("Student"); }

    public void logout() {
        active = false;
    }
}

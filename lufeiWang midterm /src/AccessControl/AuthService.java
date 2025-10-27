/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AccessControl;

import Business.UserAccounts.UserAccount;
import Business.UserAccounts.UserAccountDirectory;

import java.lang.reflect.Method;
import java.util.List;

public class AuthService {

    private final UserAccountDirectory directory;

    public AuthService(UserAccountDirectory directory) {
        if (directory == null) {
            throw new IllegalArgumentException("UserAccountDirectory cannot be null");
        }
        this.directory = directory;
    }

    public Session login(String username, String password) {
        if (isEmpty(username) || isEmpty(password)) return null;

        try {
            Method m = UserAccountDirectory.class.getMethod("authenticateUser", String.class, String.class);
            Object uaObj = m.invoke(directory, username, password);
            if (uaObj instanceof UserAccount) {
                return new Session((UserAccount) uaObj);
            }
        } catch (NoSuchMethodException ignore) {
        } catch (Exception e) {
        }

        try {
            List<UserAccount> list = directory.getUserAccountList();
            if (list != null) {
                for (UserAccount ua : list) {
                    try {
                        Method isValid = UserAccount.class.getMethod("IsValidUser", String.class, String.class);
                        Object ok = isValid.invoke(ua, username, password);
                        if (ok instanceof Boolean && ((Boolean) ok)) {
                            return new Session(ua);
                        }
                    } catch (NoSuchMethodException noSuch) {
                        try {
                            Method getLogin = UserAccount.class.getMethod("getUserLoginName");
                            Method verify = UserAccount.class.getMethod("verify", String.class);
                            Object loginNameObj = getLogin.invoke(ua);
                            Object verified = verify.invoke(ua, password);
                            if (loginNameObj instanceof String && ((String) loginNameObj).equalsIgnoreCase(username)
                                    && verified instanceof Boolean && ((Boolean) verified)) {
                                return new Session(ua);
                            }
                        } catch (Exception ignored) {
                        }
                    } catch (Exception ignored) {
                    }
                }
            }
        } catch (Exception ignored) {
        }

        return null;
    }

    public void logout(Session session) {
        if (session != null) {
            session.logout();
        }
    }

    private static boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}
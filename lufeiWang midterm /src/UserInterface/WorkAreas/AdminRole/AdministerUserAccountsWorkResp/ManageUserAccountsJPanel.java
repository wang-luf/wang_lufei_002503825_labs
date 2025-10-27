package UserInterface.WorkAreas.AdminRole.AdministerUserAccountsWorkResp;

import Business.Business;
import Business.UserAccounts.UserAccount;
import Business.UserAccounts.UserAccountDirectory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.List;

public class ManageUserAccountsJPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    UserAccount selecteduseraccount;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbProfileRole;
    private JComboBox<Object> cmbProfileObject;
    private JTextField txtSearch;
    private JButton btnCreate, btnUpdatePwd, btnToggle, btnDelete, btnSearch, btnReset;

    public ManageUserAccountsJPanel(Business bz, JPanel jp) {
        CardSequencePanel = jp;
        this.business = bz;
        initComponents();
        buildExtraUI();
        refreshProfiles();
        refreshTable();
    }

    public void refreshTable() {
        DefaultTableModel m = (DefaultTableModel) UserAccountTable.getModel();
        m.setRowCount(0);
        UserAccountDirectory uad = business.getUserAccountDirectory();
        for (UserAccount ua : uad.getUserAccountList()) {
            String uname = getUsername(ua);
            String status = getStatusString(ua);
            String updated = getLastUpdatedString(ua);
            m.addRow(new Object[]{uname, status, "", updated, ua});
        }
    }

    private void searchAndFill(String keyword) {
        DefaultTableModel m = (DefaultTableModel) UserAccountTable.getModel();
        m.setRowCount(0);
        keyword = keyword == null ? "" : keyword.trim().toLowerCase();

        for (UserAccount ua : business.getUserAccountDirectory().getUserAccountList()) {
            String uname = getUsername(ua).toLowerCase();
            String pid = stringByGetters(tryGet(ua, "getProfile", "getEmployeeProfile", "getStudentProfile"),
                                         "getId", "getEmployeeId", "getStudentId", "toString").toLowerCase();
            String dept = stringByGetters(tryGet(ua, "getDepartment", "getDept"),
                                          "getName", "getDepartmentName", "toString").toLowerCase();

            if (uname.contains(keyword) || pid.contains(keyword) || dept.contains(keyword)) {
                m.addRow(new Object[]{uname, getStatusString(ua), "", getLastUpdatedString(ua), ua});
            }
        }
    }
    
    // Helper: safely call getter methods via reflection
    private static Object tryGet(Object target, String... getters) {
        if (target == null) return null;
        for (String g : getters) {
            try {
                Object o = target.getClass().getMethod(g).invoke(target);
                if (o != null) return o;
            } catch (Exception ignore) {}
        }
        return null;
    }

    // Helper: extract a string from a nested object
    private static String stringByGetters(Object target, String... getters) {
        if (target == null) return "";
        for (String g : getters) {
            try {
                Object o = target.getClass().getMethod(g).invoke(target);
                if (o != null) return String.valueOf(o);
            } catch (Exception ignore) {}
        }
        return "";
    }

    private void initComponents() {
        Back = new javax.swing.JButton();
        Next = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        UserAccountTable = new javax.swing.JTable();

        setBackground(new java.awt.Color(0, 153, 153));
        setLayout(null);

        Back.setText("<< Back");
        Back.addActionListener(evt -> BackActionPerformed(evt));
        add(Back);
        Back.setBounds(30, 520, 90, 32);

        Next.setText("Next >>");
        Next.addActionListener(evt -> NextActionPerformed(evt));
        add(Next);
        Next.setBounds(500, 520, 90, 32);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14));
        jLabel1.setText("User Accounts");
        add(jLabel1);
        jLabel1.setBounds(30, 210, 190, 19);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 24));
        jLabel2.setText("Manage User Accounts");
        add(jLabel2);
        jLabel2.setBounds(21, 20, 550, 29);

        UserAccountTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{"User Name", "Status", "Last Activity", "Last Updated", "_ref"}
        ) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        });
        UserAccountTable.removeColumn(UserAccountTable.getColumnModel().getColumn(4));
        UserAccountTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                UserAccountTableMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(UserAccountTable);
        add(jScrollPane1);
        jScrollPane1.setBounds(30, 240, 560, 200);
    }

    private void buildExtraUI() {
        JLabel lU = new JLabel("Username:");
        lU.setBounds(30, 70, 80, 22);
        add(lU);
        txtUsername = new JTextField();
        txtUsername.setBounds(110, 70, 160, 22);
        add(txtUsername);

        JLabel lP = new JLabel("Password:");
        lP.setBounds(290, 70, 80, 22);
        add(lP);
        txtPassword = new JPasswordField();
        txtPassword.setBounds(360, 70, 160, 22);
        add(txtPassword);

        JLabel lR = new JLabel("Profile Type:");
        lR.setBounds(30, 110, 80, 22);
        add(lR);
        cmbProfileRole = new JComboBox<>(new String[]{"Employee", "Student"});
        cmbProfileRole.setBounds(110, 110, 160, 22);
        add(cmbProfileRole);
        cmbProfileRole.addActionListener(e -> refreshProfiles());

        JLabel lO = new JLabel("Profile:");
        lO.setBounds(290, 110, 60, 22);
        add(lO);
        cmbProfileObject = new JComboBox<>();
        cmbProfileObject.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setText(displayName(value));
                return this;
            }
        });
        cmbProfileObject.setBounds(360, 110, 260, 22);
        add(cmbProfileObject);

        btnCreate = new JButton("Create");
        btnCreate.setBounds(30, 150, 90, 26);
        btnCreate.addActionListener(this::onCreate);
        add(btnCreate);

        btnUpdatePwd = new JButton("Update Password");
        btnUpdatePwd.setBounds(130, 150, 150, 26);
        btnUpdatePwd.addActionListener(this::onUpdatePwd);
        add(btnUpdatePwd);

        btnToggle = new JButton("Enable/Disable");
        btnToggle.setBounds(290, 150, 130, 26);
        btnToggle.addActionListener(this::onToggle);
        add(btnToggle);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(430, 150, 90, 26);
        btnDelete.addActionListener(this::onDelete);
        add(btnDelete);

        JLabel lS = new JLabel("Search:");
        lS.setBounds(30, 460, 60, 22);
        add(lS);
        txtSearch = new JTextField();
        txtSearch.setBounds(90, 460, 220, 22);
        add(txtSearch);

        btnSearch = new JButton("Search");
        btnSearch.setBounds(320, 460, 90, 26);
        btnSearch.addActionListener(e -> searchAndFill(txtSearch.getText()));
        add(btnSearch);

        btnReset = new JButton("Reset");
        btnReset.setBounds(420, 460, 90, 26);
        btnReset.addActionListener(e -> {
            txtSearch.setText("");
            refreshTable();
        });
        add(btnReset);
    }

    private void onCreate(ActionEvent e) {
        String uname = txtUsername.getText().trim();
        String pwd = new String(txtPassword.getPassword());
        Object profile = cmbProfileObject.getSelectedItem();
        if (uname.isEmpty() || pwd.isEmpty() || profile == null) {
            JOptionPane.showMessageDialog(this, "Username/Password/Profile required");
            return;
        }
        try {
            business.getUserAccountDirectory()
                    .getClass()
                    .getMethod("newUserAccount", profile.getClass(), String.class, String.class)
                    .invoke(business.getUserAccountDirectory(), profile, uname, pwd);
            JOptionPane.showMessageDialog(this, "Account created");
            refreshTable();
        } catch (Exception ex) {
            try {
                Class<?> base = Class.forName("Business.Profiles.Profile");
                business.getUserAccountDirectory()
                        .getClass()
                        .getMethod("newUserAccount", base, String.class, String.class)
                        .invoke(business.getUserAccountDirectory(), profile, uname, pwd);
                JOptionPane.showMessageDialog(this, "Account created");
                refreshTable();
            } catch (Exception ex2) {
                JOptionPane.showMessageDialog(this, "Create failed: " + ex2.getMessage());
            }
        }
    }

    private boolean setPassword(UserAccount ua, String pwd) {
        try {
            ua.getClass().getMethod("setPassword", String.class).invoke(ua, pwd);
            return true;
        } catch (Exception ignore) {}
        try {
            ua.getClass().getMethod("resetPassword", String.class).invoke(ua, pwd);
            return true;
        } catch (Exception ignore) {}
        return false;
    }

    private void onUpdatePwd(java.awt.event.ActionEvent e) {
        int r = UserAccountTable.getSelectedRow();
        if (r < 0) { JOptionPane.showMessageDialog(this, "Select a row"); return; }
        UserAccount ua = (UserAccount) ((DefaultTableModel)UserAccountTable.getModel()).getValueAt(r, 4);

        String newPwd = JOptionPane.showInputDialog(this, "New password:");
        if (newPwd == null || newPwd.trim().isEmpty()) return;

        if (setPassword(ua, newPwd.trim())) {
            JOptionPane.showMessageDialog(this, "Password updated");
            refreshTable();
            return;
        }

        int c = JOptionPane.showConfirmDialog(
                this,
                "No password API. Recreate this account with new password?",
                "Recreate Account",
                JOptionPane.OK_CANCEL_OPTION
        );
        if (c != JOptionPane.OK_OPTION) return;

        if (recreateWithNewPassword(ua, newPwd.trim())) {
            JOptionPane.showMessageDialog(this, "Password updated by recreation");
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to recreate");
        }
    }

    private boolean recreateWithNewPassword(UserAccount ua, String newPwd) {
        try {
            Object profile = tryGet(ua, "getAssociatedPersonProfile");
            if (profile == null) profile = tryGet(ua, "getAssociatedProfile");
            if (profile == null) profile = tryGet(ua, "getProfile");
            String uname = getUsername(ua);
            if (profile == null || uname == null || uname.isEmpty()) return false;

            business.getUserAccountDirectory().getUserAccountList().remove(ua);
            try {
                business.getUserAccountDirectory()
                        .getClass()
                        .getMethod("newUserAccount", profile.getClass(), String.class, String.class)
                        .invoke(business.getUserAccountDirectory(), profile, uname, newPwd);
                return true;
            } catch (NoSuchMethodException ex) {
                Class<?> base = Class.forName("Business.Profiles.Profile");
                business.getUserAccountDirectory()
                        .getClass()
                        .getMethod("newUserAccount", base, String.class, String.class)
                        .invoke(business.getUserAccountDirectory(), profile, uname, newPwd);
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    private static Object tryGet(Object target, String getter) {
        try { return target.getClass().getMethod(getter).invoke(target); }
        catch (Exception e) { return null; }
    }


    private void onDelete(ActionEvent e) {
        int r = UserAccountTable.getSelectedRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(this, "Select a row");
            return;
        }
        UserAccount ua = (UserAccount) ((DefaultTableModel) UserAccountTable.getModel()).getValueAt(r, 4);
        int c = JOptionPane.showConfirmDialog(this, "Delete " + getUsername(ua) + " ?", "Confirm", JOptionPane.OK_CANCEL_OPTION);
        if (c != JOptionPane.OK_OPTION) return;
        if (!deleteAccount(ua)) {
            JOptionPane.showMessageDialog(this, "Delete failed");
            return;
        }
        refreshTable();
    }

    private void refreshProfiles() {
        cmbProfileObject.removeAllItems();
        String type = String.valueOf(cmbProfileRole.getSelectedItem());
        try {
            if ("Employee".equalsIgnoreCase(type)) {
                List<?> profiles = business.getEmployeeDirectory().getEmployeeProfileList();
                for (Object p : profiles) cmbProfileObject.addItem(p);
            } else {
                List<?> profiles = business.getStudentDirectory().getStudentProfileList();
                for (Object p : profiles) cmbProfileObject.addItem(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getUsername(UserAccount ua) {
        String[] gs = {"getUserName", "getUsername", "getUserID", "getId", "toString"};
        for (String g : gs) {
            try {
                Object o = ua.getClass().getMethod(g).invoke(ua);
                if (o != null) return String.valueOf(o);
            } catch (Exception ignore) {}
        }
        return "";
    }

    private String getStatusString(UserAccount ua) {
        String[] bs = {"getStatus", "isActive", "getActive"};
        for (String g : bs) {
            try {
                Object o = ua.getClass().getMethod(g).invoke(ua);
                if (o != null) return String.valueOf(o);
            } catch (Exception ignore) {}
        }
        return "";
    }

    private boolean isActive(UserAccount ua) {
        String[] bs = {"isActive", "getActive"};
        for (String g : bs) {
            try {
                Object o = ua.getClass().getMethod(g).invoke(ua);
                if (o instanceof Boolean) return (Boolean) o;
                if (o != null) return Boolean.parseBoolean(String.valueOf(o));
            } catch (Exception ignore) {}
        }
        String s = getStatusString(ua);
        return "true".equalsIgnoreCase(s) || "active".equalsIgnoreCase(s) || "ENABLED".equalsIgnoreCase(s);
    }

    private String getLastUpdatedString(UserAccount ua) {
        String[] gs = {"getLastUpdated", "getUpdatedAt", "getLastActivityTime"};
        for (String g : gs) {
            try {
                Object o = ua.getClass().getMethod(g).invoke(ua);
                if (o != null) return String.valueOf(o);
            } catch (Exception ignore) {}
        }
        return "";
    }



    private boolean setActive(UserAccount ua, boolean active) {
        try {
            ua.getClass().getMethod("setActive", boolean.class).invoke(ua, active);
            return true;
        } catch (Exception ignore) {}
        try {
            ua.getClass().getMethod("setStatus", boolean.class).invoke(ua, active);
            return true;
        } catch (Exception ignore) {}
        return false;
    }

    private void onToggle(java.awt.event.ActionEvent e) {
        int r = UserAccountTable.getSelectedRow();
        if (r < 0) { JOptionPane.showMessageDialog(this, "Select a row"); return; }
        UserAccount ua = (UserAccount) ((DefaultTableModel)UserAccountTable.getModel()).getValueAt(r, 4);

        boolean now = isActive(ua);
        if (setActive(ua, !now)) {
            refreshTable();
            return;
        }

        int c = JOptionPane.showConfirmDialog(
                this,
                "This template has no status field. Disable by deleting this account?",
                "Disable by Delete",
                JOptionPane.OK_CANCEL_OPTION
        );
        if (c == JOptionPane.OK_OPTION) {
            if (deleteAccount(ua)) refreshTable();
            else JOptionPane.showMessageDialog(this, "Delete failed");
        }
    }


    private boolean deleteAccount(UserAccount ua) {
        try {
            return business.getUserAccountDirectory().getUserAccountList().remove(ua);
        } catch (Exception e) {
            return false;
        }
    }


    private void BackActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((CardLayout) CardSequencePanel.getLayout()).previous(CardSequencePanel);
    }

    private void NextActionPerformed(java.awt.event.ActionEvent evt) {
        int r = UserAccountTable.getSelectedRow();
        if (r < 0) return;
        selecteduseraccount = (UserAccount) ((DefaultTableModel) UserAccountTable.getModel()).getValueAt(r, 4);
        if (selecteduseraccount == null) return;
        AdminUserAccount mppd = new AdminUserAccount(selecteduseraccount, CardSequencePanel);
        CardSequencePanel.add(mppd);
        ((CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }

    private void UserAccountTableMousePressed(java.awt.event.MouseEvent evt) {
        int size = UserAccountTable.getRowCount();
        int selectedrow = UserAccountTable.getSelectionModel().getLeadSelectionIndex();
        if (selectedrow < 0 || selectedrow > size - 1) return;
        selecteduseraccount = (UserAccount) ((DefaultTableModel) UserAccountTable.getModel()).getValueAt(selectedrow, 4);
        if (selecteduseraccount == null) return;
        txtUsername.setText(getUsername(selecteduseraccount));
    }

    private static String displayName(Object profile) {
        if (profile == null) return "";
        try {
            Object person = profile.getClass().getMethod("getPerson").invoke(profile);
            if (person != null) {
                Object n = person.getClass().getMethod("getName").invoke(person);
                if (n != null) return String.valueOf(n);
            }
        } catch (Exception ignore) {}
        return String.valueOf(profile);
    }

    private javax.swing.JButton Back;
    private javax.swing.JButton Next;
    private javax.swing.JTable UserAccountTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
}

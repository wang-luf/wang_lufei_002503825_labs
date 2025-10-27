package UserInterface.WorkAreas.AdminRole.ManagePersonnelWorkResp;

import Business.Business;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.List;

public class ManagePersonsJPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;

    private DefaultTableModel model;

    public ManagePersonsJPanel(Business bz, JPanel jp) {
        CardSequencePanel = jp;
        this.business = bz;
        initComponents();
        initLogic();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabelTitle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPersons = new javax.swing.JTable();
        lblId = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblDept = new javax.swing.JLabel();
        txtDept = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        cmbSearchType = new javax.swing.JComboBox<>();
        lblSearch = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();

        setBackground(new java.awt.Color(0, 153, 153));
        setLayout(null);

        jLabelTitle.setFont(new java.awt.Font("Arial", 0, 24));
        jLabelTitle.setText("Manage Personnel (HR)");
        add(jLabelTitle);
        jLabelTitle.setBounds(20, 10, 350, 30);

        tblPersons.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] { "ID", "Name", "Email", "Department" }
        ));
        jScrollPane1.setViewportView(tblPersons);
        add(jScrollPane1);
        jScrollPane1.setBounds(20, 200, 640, 160);

        lblId.setText("ID:");
        add(lblId);
        lblId.setBounds(20, 60, 30, 20);
        add(txtId);
        txtId.setBounds(50, 60, 100, 22);

        lblName.setText("Name:");
        add(lblName);
        lblName.setBounds(170, 60, 40, 20);
        add(txtName);
        txtName.setBounds(220, 60, 120, 22);

        lblEmail.setText("Email:");
        add(lblEmail);
        lblEmail.setBounds(360, 60, 40, 20);
        add(txtEmail);
        txtEmail.setBounds(400, 60, 140, 22);

        lblDept.setText("Dept:");
        add(lblDept);
        lblDept.setBounds(560, 60, 40, 20);
        add(txtDept);
        txtDept.setBounds(600, 60, 120, 22);

        btnAdd.setText("Add");
        btnAdd.addActionListener(evt -> btnAddActionPerformed(evt));
        add(btnAdd);
        btnAdd.setBounds(50, 100, 80, 25);

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(evt -> btnUpdateActionPerformed(evt));
        add(btnUpdate);
        btnUpdate.setBounds(150, 100, 90, 25);

        btnDelete.setText("Delete");
        btnDelete.addActionListener(evt -> btnDeleteActionPerformed(evt));
        add(btnDelete);
        btnDelete.setBounds(260, 100, 90, 25);

        lblSearch.setText("Search by:");
        add(lblSearch);
        lblSearch.setBounds(20, 150, 70, 20);

        cmbSearchType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "name", "id", "department" }));
        add(cmbSearchType);
        cmbSearchType.setBounds(90, 150, 110, 22);
        add(txtSearch);
        txtSearch.setBounds(210, 150, 150, 22);

        btnSearch.setText("Search");
        btnSearch.addActionListener(evt -> btnSearchActionPerformed(evt));
        add(btnSearch);
        btnSearch.setBounds(370, 150, 90, 25);

        btnReset.setText("Reset");
        btnReset.addActionListener(evt -> btnResetActionPerformed(evt));
        add(btnReset);
        btnReset.setBounds(470, 150, 90, 25);

        btnBack.setText("<< Back");
        btnBack.addActionListener(evt -> btnBackActionPerformed(evt));
        add(btnBack);
        btnBack.setBounds(20, 380, 90, 30);
    }

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((CardLayout) CardSequencePanel.getLayout()).previous(CardSequencePanel);
    }

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String dept = txtDept.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name is required.");
            return;
        }
        Object p = createPerson(id, name, email, dept);
        if (p == null) {
            JOptionPane.showMessageDialog(this, "Failed to create person.");
            return;
        }
        addRowFor(p);   
        JOptionPane.showMessageDialog(this, "Person added.");
    }

    
    private void addRowFor(Object p) {
        if (model == null) {
            model = (javax.swing.table.DefaultTableModel) tblPersons.getModel();
        }
        String rid    = txtId.getText().trim();
        String rname  = txtName.getText().trim();
        String remail = txtEmail.getText().trim();
        String rdept  = txtDept.getText().trim();
        model.addRow(new Object[]{ rid, rname, remail, rdept });
    }





    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {
        int r = tblPersons.getSelectedRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(this, "Select a row to update.");
            return;
        }
        String id = txtId.getText().trim();
        Object p = findPersonById(id);
        if (p == null) {
            JOptionPane.showMessageDialog(this, "Person not found by ID.");
            return;
        }
        updatePerson(p, txtName.getText().trim(), txtEmail.getText().trim(), txtDept.getText().trim());
        reloadTable(allPersons());
        JOptionPane.showMessageDialog(this, "Updated.");
    }

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        int r = tblPersons.getSelectedRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(this, "Select a row to delete.");
            return;
        }
        String id = String.valueOf(model.getValueAt(r, 0));
        deleteById(id);
        reloadTable(allPersons());
        JOptionPane.showMessageDialog(this, "Deleted.");
    }

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {
        String by = String.valueOf(cmbSearchType.getSelectedItem());
        String q = txtSearch.getText();
        reloadTable(filterPersons(by, q));
    }

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {
        txtSearch.setText("");
        reloadTable(allPersons());
    }

    private void initLogic() {
        model = (DefaultTableModel) tblPersons.getModel();
        model.setRowCount(0);
        tblPersons.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblPersons.getSelectedRow() >= 0) {
                int r = tblPersons.getSelectedRow();
                txtId.setText(s(model.getValueAt(r, 0)));
                txtName.setText(s(model.getValueAt(r, 1)));
                txtEmail.setText(s(model.getValueAt(r, 2)));
                txtDept.setText(s(model.getValueAt(r, 3)));
            }
        });
        reloadTable(allPersons());
    }

    private void reloadTable(List<Object> persons) {
        model.setRowCount(0);
        for (Object p : persons) {
            String id = getString(p, "getId");
            String name = getString(p, "getName");
            String email = getString(p, "getEmail");
            String dept = getString(p, "getDepartment");
            model.addRow(new Object[]{id, name, email, dept});
        }
    }

    private List<Object> allPersons() {
        Object pd = business.getPersonDirectory();
        List<Object> rs = tryList(pd, "getPersonList");
        if (rs != null) return rs;
        rs = tryList(pd, "getPersons");
        if (rs != null) return rs;
        rs = tryList(pd, "listAll");
        if (rs != null) return rs;
        rs = tryList(pd, "getAllPersons");
        if (rs != null) return rs;
        return new ArrayList<>();
    }

    private Object createPerson(String id, String name, String email, String dept) {
        Object pd = business.getPersonDirectory();
        Object person = null;

        person = invokeOrNull(pd, "newPerson",
                new Class<?>[]{String.class, String.class, String.class}, id, name, email);
        if (person == null) {
            person = invokeOrNull(pd, "newPerson",
                    new Class<?>[]{String.class, String.class}, id, name);
        }
        if (person == null) {
            person = invokeOrNull(pd, "newPerson",
                    new Class<?>[]{String.class}, name);
        }
        if (person == null) return null;

        setIfExists(person, new String[]{"setId","setPersonId","setUid"}, id);
        setIfExists(person, new String[]{"setName","setPersonName","setFullName"}, name);
        setIfExists(person, new String[]{"setEmail","setEmailId","setEmailAddress"}, email);
        setIfExists(person, new String[]{"setDepartment","setDept","setDepartmentName"}, dept);

        return person;
    }
    
    private static void setIfExists(Object target, String[] setters, String val) {
        if (target == null) return;
        for (String m : setters) {
            try {
                target.getClass().getMethod(m, String.class).invoke(target, val);
                return;
            } catch (Exception ignore) {}
        }
    }



    private Object findPersonById(String id) {
        Object pd = business.getPersonDirectory();
        Object p = invokeOrNull(pd, "findPerson", new Class<?>[]{String.class}, id);
        if (p != null) return p;
        return invokeOrNull(pd, "searchById", new Class<?>[]{String.class}, id);
    }

    private void updatePerson(Object person, String name, String email, String dept) {
        invokeIfExists(person, "setName", new Class<?>[]{String.class}, name);
        invokeIfExists(person, "setEmail", new Class<?>[]{String.class}, email);
        invokeIfExists(person, "setDepartment", new Class<?>[]{String.class}, dept);
    }

    private void deleteById(String id) {
        Object pd = business.getPersonDirectory();
        Object ok = invokeOrNull(pd, "removeById", new Class<?>[]{String.class}, id);
        if (ok == null) ok = invokeOrNull(pd, "deleteById", new Class<?>[]{String.class}, id);
        if (ok == null) {
            Object p = findPersonById(id);
            if (p != null) {
                ok = invokeOrNull(pd, "remove", new Class<?>[]{p.getClass()}, p);
                if (ok == null) invokeOrNull(pd, "delete", new Class<?>[]{p.getClass()}, p);
            }
        }
    }

    private List<Object> filterPersons(String by, String query) {
        List<Object> src = allPersons();
        List<Object> out = new ArrayList<>();
        String q = (query == null ? "" : query.trim().toLowerCase());
        for (Object p : src) {
            String id = getString(p, "getId").toLowerCase();
            String name = getString(p, "getName").toLowerCase();
            String dept = getString(p, "getDepartment");
            dept = (dept == null ? "" : dept.toLowerCase());
            if ("id".equals(by) && id.contains(q)) out.add(p);
            if ("name".equals(by) && name.contains(q)) out.add(p);
            if ("department".equals(by) && dept.contains(q)) out.add(p);
        }
        return out;
    }

    private static List<Object> tryList(Object target, String method) {
        try {
            java.lang.reflect.Method m = target.getClass().getMethod(method);
            Object o = m.invoke(target);
            if (o instanceof List) return (List<Object>) o;
        } catch (Exception ignore) {}
        return null;
    }

    private static Object invokeOrNull(Object target, String method, Class<?>[] types, Object... args) {
        try {
            return target.getClass().getMethod(method, types).invoke(target, args);
        } catch (Exception ignore) { return null; }
    }

    private static void invokeIfExists(Object target, String method, Class<?>[] types, Object... args) {
        try {
            target.getClass().getMethod(method, types).invoke(target, args);
        } catch (Exception ignore) {}
    }
    
    private static String getString(Object target, String primaryGetter) {
        if (target == null) return "";
        String[] getters;
        if ("getName".equals(primaryGetter)) {
            getters = new String[]{"getName", "getPersonName", "getFullName"};
        } else if ("getEmail".equals(primaryGetter)) {
            getters = new String[]{"getEmail", "getEmailId", "getEmailAddress"};
        } else if ("getId".equals(primaryGetter)) {
            getters = new String[]{"getId", "getPersonId", "getUid", "getUniqueId"};
        } else if ("getDepartment".equals(primaryGetter)) {
            getters = new String[]{"getDepartment", "getDept", "getDepartmentName"};
        } else {
            getters = new String[]{primaryGetter};
        }
        for (String g : getters) {
            try {
                Object o = target.getClass().getMethod(g).invoke(target);
                if (o != null) return String.valueOf(o);
            } catch (Exception ignore) {}
        }
        return "";
    }



    private static String s(Object v) { return v == null ? "" : String.valueOf(v); }

    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnBack;
    private javax.swing.JComboBox<String> cmbSearchType;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblDept;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPersons;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtDept;
    private javax.swing.JTextField txtSearch;

    private javax.swing.JButton Back;
    private javax.swing.JButton Next;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;

    private void BackActionPerformed(java.awt.event.ActionEvent evt) {
        CardSequencePanel.remove(this);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }

    private void NextActionPerformed(java.awt.event.ActionEvent evt) {
        AdministerPersonJPanel mppd = new AdministerPersonJPanel(business, CardSequencePanel);
        CardSequencePanel.add(mppd);
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).next(CardSequencePanel);
    }
}

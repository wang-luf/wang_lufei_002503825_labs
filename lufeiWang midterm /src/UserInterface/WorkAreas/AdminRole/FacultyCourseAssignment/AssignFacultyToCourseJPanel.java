package UserInterface.WorkAreas.AdminRole.FacultyCourseAssignment;

import Business.Business;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.List;

public class AssignFacultyToCourseJPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;

    private JComboBox<Object> cmbFaculty;
    private JComboBox<Object> cmbDepartment;
    private JComboBox<Object> cmbCourse;
    private JComboBox<Object> cmbSemester;
    private JComboBox<Object> cmbOffering;
    private JButton btnAssign;
    private JButton btnRemove;
    private JButton btnBack;
    private JTable tblAssignments;

    public AssignFacultyToCourseJPanel(Business b, JPanel jp) {
        this.business = b;
        this.CardSequencePanel = jp;
        initComponents();
        initLogic();
    }

    private void initComponents() {
        setLayout(null);

        JLabel t1 = new JLabel("Faculty:");
        t1.setBounds(20, 20, 70, 24);
        add(t1);
        cmbFaculty = new JComboBox<>();
        cmbFaculty.setBounds(90, 20, 220, 24);
        cmbFaculty.setRenderer(new DefaultListCellRenderer(){
            @Override public java.awt.Component getListCellRendererComponent(JList<?> list,Object value,int index,boolean isSelected,boolean cellHasFocus){
                super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
                setText(displayName(value));
                return this;
            }
        });
        add(cmbFaculty);

        JLabel t2 = new JLabel("Department:");
        t2.setBounds(330, 20, 90, 24);
        add(t2);
        cmbDepartment = new JComboBox<>();
        cmbDepartment.setBounds(420, 20, 220, 24);
        cmbDepartment.setRenderer(new DefaultListCellRenderer(){
            @Override public java.awt.Component getListCellRendererComponent(JList<?> list,Object value,int index,boolean isSelected,boolean cellHasFocus){
                super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
                setText(stringByGetters(value,"getName","getDepartmentName","toString"));
                return this;
            }
        });
        add(cmbDepartment);

        JLabel t3 = new JLabel("Course:");
        t3.setBounds(20, 60, 70, 24);
        add(t3);
        cmbCourse = new JComboBox<>();
        cmbCourse.setBounds(90, 60, 220, 24);
        cmbCourse.setRenderer(new DefaultListCellRenderer(){
            @Override public java.awt.Component getListCellRendererComponent(JList<?> list,Object value,int index,boolean isSelected,boolean cellHasFocus){
                super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
                String num = stringByGetters(value,"getNumber","getCourseNumber","toString");
                String name = stringByGetters(value,"getName","getTitle","toString");
                setText(num + (name.isEmpty()?"":" - "+name));
                return this;
            }
        });
        add(cmbCourse);

        JLabel t4 = new JLabel("Semester:");
        t4.setBounds(330, 60, 90, 24);
        add(t4);
        cmbSemester = new JComboBox<>();
        cmbSemester.setBounds(420, 60, 220, 24);
        cmbSemester.setRenderer(new DefaultListCellRenderer(){
            @Override public java.awt.Component getListCellRendererComponent(JList<?> list,Object value,int index,boolean isSelected,boolean cellHasFocus){
                super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
                setText(stringByGetters(value,"getName","getTermName","toString"));
                return this;
            }
        });
        add(cmbSemester);

        JLabel t5 = new JLabel("Offering:");
        t5.setBounds(20, 100, 70, 24);
        add(t5);
        cmbOffering = new JComboBox<>();
        cmbOffering.setBounds(90, 100, 220, 24);
        cmbOffering.setRenderer(new DefaultListCellRenderer(){
            @Override public java.awt.Component getListCellRendererComponent(JList<?> list,Object value,int index,boolean isSelected,boolean cellHasFocus){
                super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
                String courseNum = "";
                try {
                    Object course = value==null?null:value.getClass().getMethod("getCourse").invoke(value);
                    courseNum = stringByGetters(course,"getNumber","getCourseNumber","toString");
                } catch (Exception ignore) {}
                String sec = stringByGetters(value,"getSection","getOfferingNumber","toString");
                setText((courseNum.isEmpty()?"":(courseNum+"  ")) + "Sec " + sec);
                return this;
            }
        });
        add(cmbOffering);

        btnAssign = new JButton("Assign");
        btnAssign.setBounds(330, 100, 150, 26);
        btnAssign.addActionListener(this::onAssign);
        add(btnAssign);

        btnRemove = new JButton("Remove");
        btnRemove.setBounds(490, 100, 150, 26);
        btnRemove.addActionListener(this::onRemove);
        add(btnRemove);

        JScrollPane sp = new JScrollPane();
        sp.setBounds(20, 150, 620, 240);
        add(sp);
        tblAssignments = new JTable();
        tblAssignments.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"Faculty","Dept","Course","Section","Semester","_ref"}) {
            public boolean isCellEditable(int r,int c){ return false; }
        });
        sp.setViewportView(tblAssignments);
        tblAssignments.removeColumn(tblAssignments.getColumnModel().getColumn(5));

        btnBack = new JButton("<< Back");
        btnBack.setBounds(20, 410, 90, 28);
        btnBack.addActionListener(e -> {
            CardSequencePanel.remove(this);
            ((CardLayout)CardSequencePanel.getLayout()).previous(CardSequencePanel);
        });
        add(btnBack);
    }

    private void initLogic() {
        loadFaculties();
        loadDepartments();
        loadSemesters();
        cmbDepartment.addActionListener(e -> loadCoursesForSelectedDept());
        cmbCourse.addActionListener(e -> loadOfferingsForSelected());
        cmbSemester.addActionListener(e -> loadOfferingsForSelected());
        loadCoursesForSelectedDept();
        loadOfferingsForSelected();
    }

    private void loadFaculties() {
        cmbFaculty.removeAllItems();
        Object dir = business.getEmployeeDirectory();
        for (Object p : listByMethods(dir, "getEmployeeList","getEmployeeProfileList","getEmployeeProfiles"))
            cmbFaculty.addItem(p);
    }

    private void loadDepartments() {
        cmbDepartment.removeAllItems();
        Object cat = coalesce(tryGet(business,"getDepartmentCatalog"), tryGet(business,"getDepartmentDirectory"), tryGet(business,"getDepartments"));
        for (Object d : listByMethods(cat, "getDepartmentList","getDepartments","listAll"))
            cmbDepartment.addItem(d);
    }

    private void loadSemesters() {
        cmbSemester.removeAllItems();
        Object semDir = coalesce(tryGet(business,"getSemesterDirectory"), tryGet(business,"getTermDirectory"), tryGet(business,"getMasterCourseSchedule"));
        for (Object s : listByMethods(semDir, "getSemesterList","getTerms","getCourseSchedules","listAll"))
            cmbSemester.addItem(s);
    }

    private void loadCoursesForSelectedDept() {
        cmbCourse.removeAllItems();
        Object dept = cmbDepartment.getSelectedItem();
        if (dept == null) return;
        Object cc = coalesce(tryGet(dept,"getCourseCatalog"), tryGet(dept,"getCourseDirectory"), tryGet(dept,"getCourses"));
        for (Object c : listByMethods(cc, "getCourseList","getCourses","listAll"))
            cmbCourse.addItem(c);
    }
    
    private static Object coalesce(Object... xs) {
        for (Object x : xs) if (x != null) return x;
        return null;
    }

    private void loadOfferingsForSelected() {
        cmbOffering.removeAllItems();
        Object sem = cmbSemester.getSelectedItem();
        Object course = cmbCourse.getSelectedItem();
        if (sem == null || course == null) return;
        List<Object> offers = new ArrayList<>();
        offers.addAll(listByMethods(sem, "getCourseOfferList","getCourseOffers","getSchedule","getOfferings"));
        if (offers.isEmpty()) {
            try {
                Object schedule = sem.getClass().getMethod("getCourseSchedule").invoke(sem);
                offers.addAll(listByMethods(schedule, "getCourseOfferList","getCourseOffers"));
            } catch (Exception ignore) {}
        }
        List<Object> filtered = new ArrayList<>();
        if (offers.isEmpty()) {
            try {
                Object ownOffers = course.getClass().getMethod("getCourseOfferList").invoke(course);
                if (ownOffers instanceof List) offers = (List<Object>) ownOffers;
            } catch (Exception ignore) {}
        }
        if (!offers.isEmpty()) {
            for (Object o : offers) {
                Object ocourse = tryGet(o,"getCourse");
                if (ocourse != null && equalsByPointerOrKey(ocourse, course)) filtered.add(o);
            }
        }
        for (Object o : filtered) cmbOffering.addItem(o);
    }

    private boolean equalsByPointerOrKey(Object a, Object b) {
        if (a == b) return true;
        String ak = stringByGetters(a,"getNumber","getCourseNumber","getId","toString");
        String bk = stringByGetters(b,"getNumber","getCourseNumber","getId","toString");
        return !ak.isEmpty() && ak.equals(bk);
    }

    private void onAssign(java.awt.event.ActionEvent evt) {
        Object faculty = cmbFaculty.getSelectedItem();
        Object offering = cmbOffering.getSelectedItem();
        if (faculty == null) { JOptionPane.showMessageDialog(this,"Please select a faculty"); return; }
        if (offering == null) { JOptionPane.showMessageDialog(this,"Please select a course offering"); return; }
        try {
            boolean ok = invokeSetFaculty(offering, faculty);
            if (!ok) { JOptionPane.showMessageDialog(this,"Assign failed"); return; }
            upsertAssignmentRow(offering);
            JOptionPane.showMessageDialog(this,"Assigned successfully");
        } catch (Throwable t) {
            JOptionPane.showMessageDialog(this,"Assign failed: "+t.getMessage());
        }
    }

    private void onRemove(java.awt.event.ActionEvent evt) {
        int r = tblAssignments.getSelectedRow();
        if (r < 0) { JOptionPane.showMessageDialog(this,"Select a row first"); return; }
        DefaultTableModel m = (DefaultTableModel) tblAssignments.getModel();
        Object ref = m.getValueAt(r,5);
        if (ref == null) { JOptionPane.showMessageDialog(this,"No CourseOffer reference found"); return; }
        try {
            boolean ok = invokeSetFacultyNull(ref);
            if (!ok) { JOptionPane.showMessageDialog(this,"Remove failed"); return; }
            m.removeRow(r);
            JOptionPane.showMessageDialog(this,"Removed successfully");
        } catch (Throwable t) {
            JOptionPane.showMessageDialog(this,"Remove failed: "+t.getMessage());
        }
    }

    private void upsertAssignmentRow(Object offer) {
        DefaultTableModel m = (DefaultTableModel) tblAssignments.getModel();
        String facultyName = displayName(tryGet(offer,"getFaculty"));
        String dept = tryDeptName(offer);
        Object courseObj = tryGet(offer,"getCourse");
        String course = stringByGetters(courseObj,"getNumber","getCourseNumber","toString");
        String section = stringByGetters(offer,"getSection","getOfferingNumber","toString");
        String sem = stringByGetters(cmbSemester.getSelectedItem(),"getName","getTermName","toString");
        int existing = -1;
        for (int i = 0; i < m.getRowCount(); i++) {
            String c = String.valueOf(m.getValueAt(i,2));
            String s = String.valueOf(m.getValueAt(i,3));
            String sm= String.valueOf(m.getValueAt(i,4));
            if (c.equals(course) && s.equals(section) && sm.equals(sem)) { existing = i; break; }
        }
        Object[] row = new Object[]{facultyName, dept, course, section, sem, offer};
        if (existing >= 0) {
            for (int j=0;j<6;j++) m.setValueAt(row[j], existing, j);
        } else {
            m.addRow(row);
        }
    }

    private boolean invokeSetFaculty(Object offer, Object faculty) {
        try {
            for (java.lang.reflect.Method m : offer.getClass().getMethods()) {
                if (m.getName().equals("setFaculty") && m.getParameterCount()==1) {
                    m.invoke(offer, faculty);
                    return true;
                }
            }
        } catch (Exception ignore) {}
        return false;
    }

    private boolean invokeSetFacultyNull(Object offer) {
        try {
            for (java.lang.reflect.Method m : offer.getClass().getMethods()) {
                if (m.getName().equals("setFaculty") && m.getParameterCount()==1) {
                    m.invoke(offer, new Object[]{null});
                    return true;
                }
            }
        } catch (Exception ignore) {}
        return false;
    }

    private String tryDeptName(Object offer) {
        try {
            Object course = offer.getClass().getMethod("getCourse").invoke(offer);
            if (course != null) {
                Object dept = tryGet(course,"getDepartment");
                if (dept != null) return stringByGetters(dept,"getName","getDepartmentName","toString");
            }
        } catch (Exception ignore) {}
        return "";
    }

    private static Object tryGet(Object target, String getter) {
        if (target == null) return null;
        try { return target.getClass().getMethod(getter).invoke(target); }
        catch (Exception e) { return null; }
    }

    private static List<Object> listByMethods(Object target, String... methods) {
        List<Object> out = new ArrayList<>();
        if (target == null) return out;
        for (String m : methods) {
            try {
                Object o = target.getClass().getMethod(m).invoke(target);
                if (o instanceof List) return (List<Object>) o;
            } catch (Exception ignore) {}
        }
        return out;
    }

    private static String displayName(Object profile) {
        if (profile == null) return "";
        try {
            Object person = profile.getClass().getMethod("getPerson").invoke(profile);
            if (person != null) {
                String n = stringByGetters(person,"getName","getPersonName","toString");
                if (!n.isEmpty()) return n;
            }
        } catch (Exception ignore) {}
        return String.valueOf(profile);
    }

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
}
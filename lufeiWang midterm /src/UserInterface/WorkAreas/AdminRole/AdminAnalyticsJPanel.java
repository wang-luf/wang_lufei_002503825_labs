package UserInterface.WorkAreas.AdminRole;

import Business.Business;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class AdminAnalyticsJPanel extends JPanel {

    private final JPanel cardPanel;
    private final Business business;

    private JLabel lblPersons, lblStudents, lblEmployees, lblUsers, lblDepts, lblCourses, lblSemesters, lblOffers;
    private JTable tblUsers, tblFacultyLoad, tblOfferings;

    public AdminAnalyticsJPanel(Business b, JPanel cards) {
        this.business = b;
        this.cardPanel = cards;
        buildUI();
        refreshAll();
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        JPanel top = new JPanel(new GridLayout(2, 4, 12, 8));
        lblPersons = badgeLabel();
        lblStudents = badgeLabel();
        lblEmployees = badgeLabel();
        lblUsers = badgeLabel();
        lblDepts = badgeLabel();
        lblCourses = badgeLabel();
        lblSemesters = badgeLabel();
        lblOffers = badgeLabel();
        top.add(tile("Persons", lblPersons));
        top.add(tile("Students", lblStudents));
        top.add(tile("Employees", lblEmployees));
        top.add(tile("User Accounts", lblUsers));
        top.add(tile("Departments", lblDepts));
        top.add(tile("Courses", lblCourses));
        top.add(tile("Semesters", lblSemesters));
        top.add(tile("Offerings", lblOffers));

        JButton btnBack = new JButton("<< Back");
        btnBack.addActionListener(e -> {
            cardPanel.remove(this);
            ((CardLayout) cardPanel.getLayout()).previous(cardPanel);
        });
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> refreshAll());
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.add(top, BorderLayout.CENTER);
        JPanel ops = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        ops.add(btnBack);
        ops.add(btnRefresh);
        topBar.add(ops, BorderLayout.SOUTH);

        JTabbedPane tabs = new JTabbedPane();

        tblUsers = new JTable(new DefaultTableModel(new Object[][]{}, new String[]{"Username", "Status", "Last Updated"}) {
            public boolean isCellEditable(int r, int c) { return false; }
        });
        tabs.addTab("User Accounts", new JScrollPane(tblUsers));

        tblFacultyLoad = new JTable(new DefaultTableModel(new Object[][]{}, new String[]{"Faculty", "Assignments"}) {
            public boolean isCellEditable(int r, int c) { return false; }
        });
        tabs.addTab("Faculty Load", new JScrollPane(tblFacultyLoad));

        tblOfferings = new JTable(new DefaultTableModel(new Object[][]{}, new String[]{"Semester", "Course #", "Course Name", "Section", "Faculty"}) {
            public boolean isCellEditable(int r, int c) { return false; }
        });
        tabs.addTab("Course Offerings", new JScrollPane(tblOfferings));

        add(topBar, BorderLayout.NORTH);
        add(tabs, BorderLayout.CENTER);
    }

    private JLabel badgeLabel() {
        JLabel l = new JLabel("0", SwingConstants.CENTER);
        l.setFont(l.getFont().deriveFont(Font.BOLD, 20f));
        return l;
    }

    private JPanel tile(String title, JComponent metric) {
        JPanel p = new JPanel(new BorderLayout());
        JLabel t = new JLabel(title, SwingConstants.CENTER);
        t.setFont(t.getFont().deriveFont(Font.PLAIN, 12f));
        p.add(t, BorderLayout.NORTH);
        p.add(metric, BorderLayout.CENTER);
        p.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        return p;
    }

    private void refreshAll() {
        refreshCounts();
        fillUsers();
        fillFacultyLoad();
        fillOfferings();
    }

    private void refreshCounts() {
        lblPersons.setText(String.valueOf(sizeOf(listFrom(tryGet(business, "getPersonDirectory"), "getPersonList", "getPersons", "getAllPersons"))));
        lblStudents.setText(String.valueOf(sizeOf(listFrom(tryGet(business, "getStudentDirectory"), "getStudentList", "getStudentProfiles"))));
        lblEmployees.setText(String.valueOf(sizeOf(listFrom(tryGet(business, "getEmployeeDirectory"), "getEmployeeList", "getEmployeeProfiles"))));
        lblUsers.setText(String.valueOf(sizeOf(listFrom(tryGet(business, "getUserAccountDirectory"), "getUserAccountList"))));
        Object deptCatalog = tryGet(business, "getDepartmentCatalog", "getDepartmentDirectory", "getDepartments");
        List<?> depts = listFrom(deptCatalog, "getDepartmentList", "getDepartments");
        lblDepts.setText(String.valueOf(sizeOf(depts)));
        int courseCount = 0;
        if (depts != null) {
            for (Object d : depts) {
                courseCount += sizeOf(listFrom(tryGet(d, "getCourseCatalog", "getCourseDirectory"), "getCourseList", "getCourses"));
            }
        }
        lblCourses.setText(String.valueOf(courseCount));
        Object semDir = tryGet(business, "getSemesterDirectory", "getTermDirectory", "getMasterCourseSchedule");
        List<?> semesters = listFrom(semDir, "getSemesterList", "getTermList", "getCourseSchedules");
        lblSemesters.setText(String.valueOf(sizeOf(semesters)));
        int offerCount = 0;
        if (semesters != null) {
            for (Object s : semesters) {
                offerCount += sizeOf(listFrom(s, "getCourseOfferList", "getCourseOffers", "getSchedule"));
            }
        }
        if (offerCount == 0) {
            if (depts != null) {
                for (Object d : depts) {
                    List<?> catalog = listFrom(tryGet(d, "getCourseCatalog", "getCourseDirectory"), "getCourseList", "getCourses");
                    if (catalog != null) {
                        for (Object c : catalog) {
                            offerCount += sizeOf(listFrom(c, "getCourseOfferList", "getCourseOffers"));
                        }
                    }
                }
            }
        }
        lblOffers.setText(String.valueOf(offerCount));
    }

    private void fillUsers() {
        DefaultTableModel m = (DefaultTableModel) tblUsers.getModel();
        m.setRowCount(0);
        List<?> users = listFrom(tryGet(business, "getUserAccountDirectory"), "getUserAccountList");
        if (users == null) return;
        for (Object ua : users) {
            String uname = stringBy(ua, "getUserName", "getUsername", "getUserID", "toString");
            String status = stringBy(ua, "getStatus", "isActive", "getActive");
            String updated = stringBy(ua, "getLastUpdated", "getUpdatedAt", "getLastActivityTime");
            m.addRow(new Object[]{uname, status, updated});
        }
    }

    private void fillFacultyLoad() {
        DefaultTableModel m = (DefaultTableModel) tblFacultyLoad.getModel();
        m.setRowCount(0);
        Map<String, Integer> load = new LinkedHashMap<>();
        List<?> semesters = listFrom(tryGet(business, "getSemesterDirectory", "getTermDirectory", "getMasterCourseSchedule"), "getSemesterList", "getTermList", "getCourseSchedules");
        if (semesters != null) {
            for (Object sem : semesters) {
                List<?> offers = listFrom(sem, "getCourseOfferList", "getCourseOffers", "getSchedule");
                if (offers == null) continue;
                for (Object off : offers) {
                    Object fac = tryGet(off, "getFaculty", "getInstructor", "getFacultyProfile", "getEmployeeProfile");
                    String name = displayName(fac);
                    if (name.isEmpty()) continue;
                    load.put(name, load.getOrDefault(name, 0) + 1);
                }
            }
        }
        for (Map.Entry<String, Integer> e : load.entrySet()) {
            m.addRow(new Object[]{e.getKey(), e.getValue()});
        }
    }

    private void fillOfferings() {
        DefaultTableModel m = (DefaultTableModel) tblOfferings.getModel();
        m.setRowCount(0);
        List<?> semesters = listFrom(tryGet(business, "getSemesterDirectory", "getTermDirectory", "getMasterCourseSchedule"), "getSemesterList", "getTermList", "getCourseSchedules");
        if (semesters == null) return;
        for (Object sem : semesters) {
            String semName = stringBy(sem, "getName", "getSemesterName", "toString");
            List<?> offers = listFrom(sem, "getCourseOfferList", "getCourseOffers", "getSchedule");
            if (offers == null) continue;
            for (Object off : offers) {
                Object course = tryGet(off, "getCourse", "getSubjectCourse");
                String num = stringBy(course, "getCourseNumber", "getNumber", "getId");
                String cname = stringBy(course, "getCourseName", "getName", "toString");
                String sec = stringBy(off, "getSectionNumber", "getSection", "getId");
                String fac = displayName(tryGet(off, "getFaculty", "getInstructor", "getFacultyProfile", "getEmployeeProfile"));
                m.addRow(new Object[]{semName, num, cname, sec, fac});
            }
        }
    }

    private static Object tryGet(Object target, String... getters) {
        if (target == null) return null;
        for (String g : getters) {
            try {
                return target.getClass().getMethod(g).invoke(target);
            } catch (Exception ignore) {}
        }
        return null;
    }

    private static List<?> listFrom(Object target, String... getters) {
        Object o = tryGet(target, getters);
        if (o instanceof List) return (List<?>) o;
        return null;
    }

    private static int sizeOf(List<?> l) {
        return l == null ? 0 : l.size();
    }

    private static String stringBy(Object target, String... getters) {
        Object o = tryGet(target, getters);
        return o == null ? "" : String.valueOf(o);
    }

    private static String displayName(Object profileOrPerson) {
        if (profileOrPerson == null) return "";
        Object person = tryGet(profileOrPerson, "getPerson");
        if (person != null) {
            String n = stringBy(person, "getName", "getPersonName", "getFullName", "toString");
            if (!n.isEmpty()) return n;
        }
        return stringBy(profileOrPerson, "getName", "toString");
    }
}
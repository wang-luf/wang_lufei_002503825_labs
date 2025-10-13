/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class UserAccount {
    private static int NEXT_ID = 1;

    private final int id = NEXT_ID++;
    private String username;
    private String password;
    private String displayName;
    private Role role;
    private Integer employeeId;
    private Integer experienceYears;
    private Branch managedBranch;

    public UserAccount(String username, String password, String displayName, Role role) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.role = role;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getDisplayName() { return displayName; }
    public Role getRole() { return role; }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public void setRole(Role role) { this.role = role; }

    public Integer getEmployeeId() { return employeeId; }
    public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }

    public Integer getExperienceYears() { return experienceYears; }
    public void setExperienceYears(Integer experienceYears) { this.experienceYears = experienceYears; }

    public Branch getManagedBranch() { return managedBranch; }
    public void setManagedBranch(Branch managedBranch) { this.managedBranch = managedBranch; }

    @Override
    public String toString() { return displayName + " (" + role.getType() + ")"; }
}


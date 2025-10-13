/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public abstract class Role {
    public enum Type { SYSTEM_ADMIN, BRANCH_MANAGER, CUSTOMER }

    private final Type type;
    protected Role(Type type) { this.type = type; }
    public Type getType() { return type; }

    public static class SystemAdminRole extends Role {
        public SystemAdminRole() { super(Type.SYSTEM_ADMIN); }
    }

    public static class BranchManagerRole extends Role {
        public BranchManagerRole() { super(Type.BRANCH_MANAGER); }
    }

    public static class CustomerRole extends Role {
        public CustomerRole() { super(Type.CUSTOMER); }
    }
}


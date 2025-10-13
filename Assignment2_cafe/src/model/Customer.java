package model;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    private int customerId;
    private String firstName;
    private String lastName;
    private long contact;
    private boolean active = true;
    private final List<Order> orders = new ArrayList<>();

    public String getFullName(){ return (firstName + " " + lastName).trim(); }
    public List<Order> getOrders(){ return orders; }
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public long getContact() { return contact; }
    public void setContact(long contact) { this.contact = contact; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}

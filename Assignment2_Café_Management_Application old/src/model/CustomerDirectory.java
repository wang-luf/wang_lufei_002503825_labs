package model;

import java.util.*;

public class CustomerDirectory {
    private final List<Customer> customers = new ArrayList<>();
    public List<Customer> getAll(){ return customers; }
    public void add(Customer c){ customers.add(c); }
    public void remove(Customer c){ customers.remove(c); }
    
    public Customer findById(int id){
        return customers.stream().filter(x->x.getCustomerId()==id).findFirst().orElse(null);
    }
    public List<Customer> findByName(String name){
        String k = name.trim().toLowerCase();
        List<Customer> res = new ArrayList<>();
        for (Customer c: customers){
            String full = c.getFullName().toLowerCase();
            if (full.equals(k) || c.getFirstName().equalsIgnoreCase(k) || c.getLastName().equalsIgnoreCase(k)){
                res.add(c);
            }
        }
        return res;
    }
}


package model;
import java.util.*;

public class OrderDirectory {
    private final List<Order> orders = new ArrayList<>();
    public List<Order> getAll(){ return orders; }
    public void add(Order o){ orders.add(o); }
    public void remove(Order o){ orders.remove(o); }
    public Order findById(int id){
        return orders.stream().filter(x->x.getOrderId()==id).findFirst().orElse(null);
    }
}

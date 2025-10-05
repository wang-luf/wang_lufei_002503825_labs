package model;
import java.util.*;

public class ProductCatalog {
    private final List<Product> products = new ArrayList<>();
    public List<Product> getAll(){ return products; }
    public void add(Product p){ products.add(p); }
    public void remove(Product p){ products.remove(p); }

    public Product findById(int id){
        return products.stream().filter(x->x.getProductId()==id).findFirst().orElse(null);
    }
    
    public List<Product> findByName(String name){
        String k = name.trim().toLowerCase();
        List<Product> res = new ArrayList<>();
        for (Product p: products){
            if (p.getName()!=null && p.getName().toLowerCase().equals(k)) res.add(p);
        }
        return res;
    }
}

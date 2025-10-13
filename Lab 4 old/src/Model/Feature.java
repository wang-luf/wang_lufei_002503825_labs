/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Lufei
 */
public class Feature {
    private Product owner;
    private String Name;
    private Object value;
    
    
    
    public Feature(Product owner){
        this.owner=owner;
    }
    
    public Feature(Product owner, String Name, Object value) {
        this.owner = owner;
        this.Name = Name;
        this.value = value;
    }

    public Product getOwner() {
        return owner;
    }

    public void setOwner(Product owner) {
        this.owner = owner;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
    
    @Override
    public String toString(){
        return Name;
    
    }
    
    

}



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.util.ArrayList;

/**
 *
 * @author Lufei
 */
public class SupplierDirectory {
    
    private ArrayList<Supplier> suppliers;
    
    public SupplierDirectory() {
        suppliers = new ArrayList<Supplier>();
    }

    public ArrayList<Supplier> getSupplierList() {
        return suppliers;
    }
    
    public Supplier addSupplier() {
        Supplier s = new Supplier();
        suppliers.add(s);
        return s;
    }
    
    public void removeSupplier(Supplier s) {
        suppliers.remove(s);
    }
    
    public Supplier searchSupplier(String keyWord) {
        for(Supplier s : suppliers) {
            if(keyWord.equals(s.getSupplyName())) {
                return s;
            }
        }
        return null;
    }
    
}

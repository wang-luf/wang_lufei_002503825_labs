/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author l
 */
import java.util.ArrayList;

public class VitalSignHistory {
    private ArrayList<VitalSign> history;

    public VitalSignHistory() {
        history = new ArrayList<>();
    }

    public VitalSign addNewVital() {
        VitalSign vs = new VitalSign();
        history.add(vs);
        return vs;
    }

    public void deleteVital(VitalSign v) {
        history.remove(v);
    }

    public ArrayList<VitalSign> getHistory() {
        return history;
    }
}


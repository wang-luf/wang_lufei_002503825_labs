/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author l
 */
public class VitalSign {
    private String date;
    private float temperature;
    private double bloodPressure;
    private int pulse;
    private boolean isConscious;

    // getters & setters
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public float getTemperature() { return temperature; }
    public void setTemperature(float temperature) { this.temperature = temperature; }

    public double getBloodPressure() { return bloodPressure; }
    public void setBloodPressure(double bloodPressure) { this.bloodPressure = bloodPressure; }

    public int getPulse() { return pulse; }
    public void setPulse(int pulse) { this.pulse = pulse; }

    public boolean isConscious() { return isConscious; }
    public void setConscious(boolean conscious) { isConscious = conscious; }

    @Override
    public String toString() {
        return date;
    }
}


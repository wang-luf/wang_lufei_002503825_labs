/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Branch {
    private static int NEXT_ID = 1;

    private final int id = NEXT_ID++;
    private final String name;
    private final Library library; 

    public Branch(String name, Library library) {
        this.name = name;
        this.library = library;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public Library getLibrary() { return library; }

    @Override
    public String toString() { return name + " (ID " + id + ")"; }
}

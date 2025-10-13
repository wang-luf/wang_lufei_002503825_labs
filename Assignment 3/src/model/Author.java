/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Author {
    private static int NEXT_ID = 1;

    private final int id = NEXT_ID++;
    private final String name;

    public Author(String name) { this.name = name; }

    public int getId() { return id; }
    public String getName() { return name; }

    @Override
    public String toString() { return name; }
}

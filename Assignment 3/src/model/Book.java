/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

public class Book {
    private static int NEXT_SN = 1000; 

    private final int serialNumber = NEXT_SN++;
    private final String name;
    private final LocalDate registeredDate = LocalDate.now();
    private final int pages;
    private final String language;
    private final Author author;
    private boolean isRented = false;

    public Book(String name, int pages, String language, Author author) {
        this.name = name;
        this.pages = pages;
        this.language = language;
        this.author = author;
    }

    public int getSerialNumber() { return serialNumber; }
    public String getName() { return name; }
    public LocalDate getRegisteredDate() { return registeredDate; }
    public int getPages() { return pages; }
    public String getLanguage() { return language; }
    public Author getAuthor() { return author; }

    public boolean isRented() { return isRented; }
    public void setRented(boolean rented) { isRented = rented; }

    @Override
    public String toString() { return name + " (#" + serialNumber + ")"; }
}


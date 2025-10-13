/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private static int NEXT_ID = 1;

    private final int id = NEXT_ID++;
    private int buildingNo;

    private final List<Book> books = new ArrayList<>();
    private final List<Author> authors = new ArrayList<>();

    public Library(int buildingNo) { this.buildingNo = buildingNo; }

    public int getId() { return id; }
    public int getBuildingNo() { return buildingNo; }
    public void setBuildingNo(int buildingNo) { this.buildingNo = buildingNo; }

    public List<Book> getBooks() { return books; }
    public List<Author> getAuthors() { return authors; }

    public Author addAuthor(String name) {
        Author a = new Author(name);
        authors.add(a);
        return a;
    }

    public Book addBook(String name, int pages, String language, Author author) {
        Book b = new Book(name, pages, language, author);
        books.add(b);
        return b;
    }

    @Override
    public String toString() { return "Library#" + id + " (Bldg " + buildingNo + ")"; }
}

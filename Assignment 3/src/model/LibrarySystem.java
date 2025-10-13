/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LibrarySystem {

    private final List<Branch> branches = new ArrayList<>();
    private final List<UserAccount> userAccounts = new ArrayList<>();
    private final List<RentalRecord> rentalRecords = new ArrayList<>();
    private final List<Author> allAuthors = new ArrayList<>();
    private final List<Book> books = new ArrayList<>();
    
    public List<Library> getLibraries() {
        List<Library> libs = new ArrayList<>();
        for (Branch b : branches) {
            if (b.getLibrary() != null) libs.add(b.getLibrary());
        }
        return libs;
    }

    public Branch createBranch(String name, int buildingNo) {
        Library lib = new Library(buildingNo);
        Branch b = new Branch(name, lib);
        branches.add(b);
        return b;
    }

    public void deleteBranch(Branch b) {
        branches.remove(b);
    }
    
    public List<Book> getBooks() {
        return books;
    }

    public List<RentalRecord> getRentalRecords() {
        return rentalRecords;
    }

    public List<Branch> getBranches() { return branches; }


    public UserAccount createUser(String username, String password, String displayName, Role role) {
        UserAccount ua = new UserAccount(username, password, displayName, role);
        userAccounts.add(ua);
        return ua;
    }

    public UserAccount authenticate(String username, String password) {
        for (UserAccount ua : userAccounts) {
            if (Objects.equals(ua.getUsername(), username) && Objects.equals(ua.getPassword(), password)) {
                return ua;
            }
        }
        return null;
    }

    public List<UserAccount> getUserAccounts() { return userAccounts; }


    public Author registerAuthor(String name) {
        Author a = new Author(name);
        allAuthors.add(a);
        return a;
    }

    public List<Author> getAllAuthors() { return allAuthors; }

    public Author addAuthorToLibrary(Branch branch, String authorName) {
        Author author = branch.getLibrary().addAuthor(authorName);
        boolean exists = allAuthors.stream().anyMatch(a -> a.getName().equals(authorName));
        if (!exists) allAuthors.add(author);
        return author;
    }

    public boolean rentBook(UserAccount customer, Branch branch, Book book) {
        if (customer == null || branch == null || book == null) return false;
        if (book.isRented()) return false;
        if (!branch.getLibrary().getBooks().contains(book)) return false;

        book.setRented(true);
        RentalRecord r = new RentalRecord(book, customer, branch.getLibrary());
        rentalRecords.add(r);
        return true;
    }

    public boolean returnBook(RentalRecord record) {
        if (record == null || record.getStatus() != RentalRecord.Status.RENTED) return false;
        record.markReturned();
        record.getBook().setRented(false);
        return true;
    }


    public List<RentalRecord> getRentalsForCustomer(UserAccount c) {
        return rentalRecords.stream().filter(x -> x.getCustomer() == c).collect(Collectors.toList());
    }

    public List<RentalRecord> getRentalsForLibrary(Library lib) {
        return rentalRecords.stream().filter(x -> x.getLibrary() == lib).collect(Collectors.toList());
    }
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

public class RentalRecord {
    public enum Status { RENTED, RETURNED }

    private static int NEXT_ID = 1;

    private final int id = NEXT_ID++;
    private final Book book;
    private final UserAccount customer; 
    private final Library library;   
    private final LocalDateTime rentedAt;
    private LocalDateTime returnedAt;
    private Status status;

    public RentalRecord(Book book, UserAccount customer, Library library) {
        this.book = book;
        this.customer = customer;
        this.library = library;
        this.rentedAt = LocalDateTime.now();
        this.status = Status.RENTED;
    }

    public int getId() { return id; }
    public Book getBook() { return book; }
    public UserAccount getCustomer() { return customer; }
    public Library getLibrary() { return library; }
    public LocalDateTime getRentedAt() { return rentedAt; }
    public LocalDateTime getReturnedAt() { return returnedAt; }
    public Status getStatus() { return status; }

    public void markReturned() {
        if (status == Status.RENTED) {
            status = Status.RETURNED;
            returnedAt = LocalDateTime.now();
        }
    }
}


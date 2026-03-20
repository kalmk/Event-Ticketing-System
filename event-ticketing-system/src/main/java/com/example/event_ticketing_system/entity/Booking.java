package com.example.event_ticketing_system.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

// Booking is the Junction Table
@Entity
@Table(name = "booking")
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer booking_id;

    @Column(nullable = false, unique = true)
    private String booking_reference;

    @Column(nullable = false)
    private LocalDateTime booking_date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus payment_status;

    @ManyToOne
    @JoinColumn(name = "attendee_id", nullable = false)
    private Attendee attendee;

    @ManyToOne
    @JoinColumn(name = "ticket_type_id", nullable = false)
    private TicketType ticketType;

    @PrePersist
    protected void onCreate() {
        booking_date = LocalDateTime.now();
    }

    public void setBookingReference(String s) {
        this.booking_reference = s;
    }

    public void setBookingDate(LocalDateTime now) {
        this.booking_date = now;
    }

    public enum PaymentStatus {
        PENDING,
        CONFIRMED,
        CANCELLED
    }
}
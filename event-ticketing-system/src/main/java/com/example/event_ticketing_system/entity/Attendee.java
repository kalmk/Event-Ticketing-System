package com.example.event_ticketing_system.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "attendee")
@Data
public class Attendee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attendee_id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;
}
package com.example.event_ticketing_system.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "organizer")
@Data
public class Organizer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer organizer_id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private String phone;
}
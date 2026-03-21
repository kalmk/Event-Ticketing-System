package com.example.event_ticketing_system.dto;

import lombok.Data;

@Data
public class AttendeeResponseDTO {
    private Integer attendee_id;
    private String name;
    private String email;
}
package com.example.event_ticketing_system.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrganizerResponseDTO {
    private Integer organizerId;
    private String name;
    private String email;
    private String phone;
}
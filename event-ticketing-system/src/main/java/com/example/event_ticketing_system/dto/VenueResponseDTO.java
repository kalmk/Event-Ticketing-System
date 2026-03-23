package com.example.event_ticketing_system.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VenueResponseDTO {
    private Integer venue_id;
    private String name;
    private String address;
    private String city;
    private Integer total_capacity;
}
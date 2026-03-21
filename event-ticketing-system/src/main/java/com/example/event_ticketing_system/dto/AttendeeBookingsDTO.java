package com.example.event_ticketing_system.dto;

import lombok.Data;

import java.util.List;

@Data
public class AttendeeBookingsDTO {
    private String attendee_name;

    private List<BookingResponseDTO> bookings;
}

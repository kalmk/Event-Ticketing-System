package com.example.event_ticketing_system.dto;

import java.time.LocalDateTime;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDTO {
    private String bookingReference;
    private LocalDateTime bookingDate;
    private String paymentStatus;
    private String attendeeName;
    private String eventTitle;
    private String ticketTypeName;
    private BigDecimal price;
}

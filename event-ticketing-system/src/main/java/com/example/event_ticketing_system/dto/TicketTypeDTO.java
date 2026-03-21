package com.example.event_ticketing_system.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketTypeDTO {
    private Integer ticketTypeId;
    private String name;
    private BigDecimal price;
    private Integer quantity_available;
}

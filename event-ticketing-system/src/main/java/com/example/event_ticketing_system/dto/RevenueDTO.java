package com.example.event_ticketing_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueDTO {
    private String eventTitle;
    private Integer totalConfirmedRevenue;
}

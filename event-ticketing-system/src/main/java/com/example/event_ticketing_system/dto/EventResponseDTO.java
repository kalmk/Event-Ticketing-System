package com.example.event_ticketing_system.dto;

import java.time.LocalDateTime;
import lombok.Data;
import java.util.List;


@Data
public class EventResponseDTO {
    private Integer event_id;
    private String title;
    private String description;
    private LocalDateTime event_date;
    private String status;
    private String organizerName;
    private String venueName;

    private List<TicketTypeDTO> ticketTypes;
}
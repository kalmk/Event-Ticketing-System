package com.example.event_ticketing_system.controller;

import com.example.event_ticketing_system.dto.EventRequestDTO;
import com.example.event_ticketing_system.dto.EventResponseDTO;
import com.example.event_ticketing_system.dto.RevenueDTO;
import com.example.event_ticketing_system.service.EventService;

import com.example.event_ticketing_system.service.RevenueService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final RevenueService revenueService;

    // POST /api/events
    @PostMapping
    public ResponseEntity<EventResponseDTO> createEvent(@RequestBody EventRequestDTO dto) {

        EventResponseDTO response = eventService.createEvent(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    // GET /api/events
    @GetMapping
    public ResponseEntity<List<EventResponseDTO>>getUpcomingEvents() {
        return ResponseEntity.ok(eventService.getUpcomingEvents());
    }

    // GET /api/events/{id}
    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEventById(@PathVariable Integer id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    // GET /api/events/{id}/revenue
    @GetMapping("/{id}/revenue")
    public ResponseEntity<RevenueDTO> getEventRevenue(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(revenueService.getEventRevenue(id));
    }

}


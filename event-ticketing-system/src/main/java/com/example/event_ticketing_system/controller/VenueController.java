package com.example.event_ticketing_system.controller;

import com.example.event_ticketing_system.dto.VenueRequestDTO;
import com.example.event_ticketing_system.dto.VenueResponseDTO;
import com.example.event_ticketing_system.service.VenueService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/venues")
@RequiredArgsConstructor
public class VenueController {

    private final VenueService venueService;

    @PostMapping
    public ResponseEntity<VenueResponseDTO> createVenue(
            @RequestBody VenueRequestDTO requestDTO) {

        System.out.println("capacity = " + requestDTO.getTotal_capacity());

        VenueResponseDTO response = venueService.createVenue(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
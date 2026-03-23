package com.example.event_ticketing_system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.event_ticketing_system.dto.OrganizerRequestDTO;
import com.example.event_ticketing_system.dto.OrganizerResponseDTO;
import com.example.event_ticketing_system.service.OrganizerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/organizers")
@RequiredArgsConstructor
public class OrganizerController {

    private final OrganizerService organizerService;

    @PostMapping
    public ResponseEntity<OrganizerResponseDTO> createOrganizer(
            @RequestBody OrganizerRequestDTO requestDTO) {

        OrganizerResponseDTO response = organizerService.createOrganizer(requestDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
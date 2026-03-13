package com.example.event_ticketing_system.controller;

import com.example.event_ticketing_system.dto.AttendeeRequestDTO;
import com.example.event_ticketing_system.dto.AttendeeResponseDTO;
import com.example.event_ticketing_system.service.AttendeeService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/attendees")
@RequiredArgsConstructor
public class AttendeeController {
    private final AttendeeService attendeeService;

    @PostMapping
    public ResponseEntity<AttendeeResponseDTO> createAttendee(@RequestBody AttendeeRequestDTO dto) {

        AttendeeResponseDTO response = attendeeService.createAttendee(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}

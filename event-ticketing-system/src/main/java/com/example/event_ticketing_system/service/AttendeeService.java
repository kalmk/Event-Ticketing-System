package com.example.event_ticketing_system.service;

import org.springframework.stereotype.Service;

import com.example.event_ticketing_system.dto.AttendeeRequestDTO;
import com.example.event_ticketing_system.dto.AttendeeResponseDTO;
import com.example.event_ticketing_system.entity.Attendee;
import com.example.event_ticketing_system.repository.AttendeeRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;

    @Transactional
    public AttendeeResponseDTO createAttendee(AttendeeRequestDTO dto) {
        if (attendeeRepository.emailExists(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already registered");
        }

        Attendee attendee = new Attendee();
        attendee.setName(dto.getName());
        attendee.setEmail(dto.getEmail());

        Attendee saved = attendeeRepository.save(attendee);

        AttendeeResponseDTO response = new AttendeeResponseDTO();
        response.setAttendee_id(saved.getAttendee_id());
        response.setName(saved.getName());
        response.setEmail(saved.getEmail());

        return response;
    }
}


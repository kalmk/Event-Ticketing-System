package com.example.event_ticketing_system.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.event_ticketing_system.dto.OrganizerRequestDTO;
import com.example.event_ticketing_system.dto.OrganizerResponseDTO;
import com.example.event_ticketing_system.entity.Organizer;
import com.example.event_ticketing_system.repository.OrganizerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrganizerService {
    private final OrganizerRepository organizerRepository;

    @Transactional
    public OrganizerResponseDTO createOrganizer(OrganizerRequestDTO requestDTO) {

        Organizer organizer = new Organizer();
        organizer.setName(requestDTO.getName());
        organizer.setEmail(requestDTO.getEmail());
        organizer.setPhone(requestDTO.getPhone());

        Organizer saved = organizerRepository.save(organizer);

        return OrganizerResponseDTO.builder()
                .organizerId(saved.getOrganizer_id())
                .name(saved.getName())
                .email(saved.getEmail())
                .phone(saved.getPhone())
                .build();
    }
}
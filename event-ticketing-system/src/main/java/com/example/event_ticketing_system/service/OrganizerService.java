package com.example.event_ticketing_system.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.event_ticketing_system.entity.Organizer;
import com.example.event_ticketing_system.repository.OrganizerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrganizerService {
    // Not using @Autowired to ensure organizerRepository is always non-null
    // Guaranteed to be initialized
    private final OrganizerRepository organizerRepository;

    // Create a new organizer
    @Transactional
    public Organizer createOrganizer(Organizer organizer) {
        // In this project we don't have authentication and authorization

        return organizerRepository.save(organizer);
    }
}

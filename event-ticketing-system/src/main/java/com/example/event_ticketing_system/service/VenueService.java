package com.example.event_ticketing_system.service;

import com.example.event_ticketing_system.dto.VenueRequestDTO;
import com.example.event_ticketing_system.dto.VenueResponseDTO;
import com.example.event_ticketing_system.entity.Venue;
import com.example.event_ticketing_system.repository.VenueRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VenueService {
    private final VenueRepository venueRepository;

    @Transactional
    public VenueResponseDTO createVenue(VenueRequestDTO requestDTO) {

        // DTO → Entity
        Venue venue = new Venue();
        venue.setName(requestDTO.getName());
        venue.setAddress(requestDTO.getAddress());
        venue.setCity(requestDTO.getCity());
        venue.setTotal_capacity(requestDTO.getTotal_capacity());

        Venue saved = venueRepository.save(venue);

        // Entity → DTO
        return VenueResponseDTO.builder()
                .venue_id(saved.getVenue_id())
                .name(saved.getName())
                .address(saved.getAddress())
                .city(saved.getCity())
                .total_capacity(saved.getTotal_capacity())
                .build();
    }
}
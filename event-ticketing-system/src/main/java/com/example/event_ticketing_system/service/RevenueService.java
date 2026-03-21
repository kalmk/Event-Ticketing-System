package com.example.event_ticketing_system.service;

import com.example.event_ticketing_system.dto.RevenueDTO;
import com.example.event_ticketing_system.repository.EventRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RevenueService {

    private final EventRepository eventRepository;

    public RevenueDTO getEventRevenue(Integer event_id) {

        return eventRepository.getRevenueDTO(event_id)
                .orElseThrow(() -> new EntityNotFoundException("No data found"));
    }

}

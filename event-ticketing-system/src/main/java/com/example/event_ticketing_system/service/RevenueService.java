package com.example.event_ticketing_system.service;

import com.example.event_ticketing_system.dto.RevenueDTO;
import com.example.event_ticketing_system.entity.Event;
import com.example.event_ticketing_system.repository.EventRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RevenueService {

    private final EventRepository eventRepository;

    public RevenueDTO getEventRevenue(Integer event_id) throws Exception {
        // Find Event Title and Revenue
        //      Note: Our query returns title and revenue in an object array
        Object[] result = eventRepository.getRawRevenueData(event_id)
                .orElseThrow(() -> new EntityNotFoundException("No data found"));

        // Make a RevenueDTO and return it
        return new RevenueDTO((String) result[0], (Integer) result[1]);
    }

}

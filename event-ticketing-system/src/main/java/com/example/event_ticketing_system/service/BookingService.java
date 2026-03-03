package com.example.event_ticketing_system.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.event_ticketing_system.entity.Booking;
import com.example.event_ticketing_system.repository.BookingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;

    @Transactional
    public Booking bookTicket(Integer attendeeId, Integer ticketTypeId) {
        // TO DO:
        // IMPLEMENT LATER

        Booking booking = new Booking();

        // TO DO:
        // IMPLEMENT LATER
        return bookingRepository.save(booking);
    }
}

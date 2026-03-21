package com.example.event_ticketing_system.controller;

import com.example.event_ticketing_system.dto.BookingRequestDTO;
import com.example.event_ticketing_system.dto.BookingResponseDTO;
import com.example.event_ticketing_system.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponseDTO> bookTicket(@RequestBody BookingRequestDTO dto) throws Exception {
        BookingResponseDTO response = bookingService.createBooking(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookingResponseDTO> cancelBooking(@PathVariable Integer id) throws Exception {

        BookingResponseDTO response = bookingService.cancelBooking(id);

        return ResponseEntity.ok(response);
    }
}

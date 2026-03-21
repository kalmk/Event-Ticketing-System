package com.example.event_ticketing_system.service;

import com.example.event_ticketing_system.dto.BookingRequestDTO;
import com.example.event_ticketing_system.dto.BookingResponseDTO;
import com.example.event_ticketing_system.entity.Attendee;
import com.example.event_ticketing_system.entity.Booking;
import com.example.event_ticketing_system.entity.TicketType;
import com.example.event_ticketing_system.repository.AttendeeRepository;
import com.example.event_ticketing_system.repository.BookingRepository;
import com.example.event_ticketing_system.repository.TicketTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.UUID;

@RestControllerAdvice   // Using this to catch 'EntityNotFoundException' and throw a 404 not found error
@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final AttendeeRepository attendeeRepository;

    @Transactional
    public BookingResponseDTO createBooking(BookingRequestDTO dto) throws Exception {
        // Check if the ticket exists
        boolean available = bookingRepository.ticketExistsInQuantity(
                dto.getTicketTypeId()
        );

        if (!available) {
            throw new Exception("Tickets sold out for ticket id: " + dto.getTicketTypeId());
        }

        // Get Attendee and TicketType to check if they have already booked a ticket
        Attendee attendee = attendeeRepository.findById(dto.getAttendeeId())
                .orElseThrow(() -> new Exception("Attendee not found with id: " + dto.getAttendeeId()));
        TicketType ticketType = ticketTypeRepository.findById(dto.getTicketTypeId())
                .orElseThrow(() -> new Exception("Ticket Type not found with id: " + dto.getTicketTypeId()));


        // Check if the attendee has already booked the same ticket
        boolean alreadyBookedTicketType = bookingRepository.existsByAttendeeAndTicketType(
                attendee,
                ticketType
        );

        // Throw if already booked same ticket
        if (alreadyBookedTicketType) {
            throw new Exception("The current attendee already has a ticket of id: " + dto.getTicketTypeId());
        }


        // Decrement quantity_available on TicketType by 1
        ticketTypeRepository.decrementQuantityAvailable(dto.getTicketTypeId());

        // Generate Unique booking reference
        Booking booking = new Booking();
        booking.setBookingReference("BK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        // Set booking_date to current timestamp
        booking.setBookingDate(LocalDateTime.now());

        // Set payment_status to CONFIRMED on creation
        booking.setPayment_status(Booking.PaymentStatus.valueOf("CONFIRMED"));

        booking.setAttendee(attendee);
        booking.setTicketType(ticketType);

        // Save changes to our bookingRepo
        Booking saved = bookingRepository.save(booking);

        // Convert our saved booking object to BookingRequestDTO to return result
        BookingResponseDTO response = new BookingResponseDTO();
        response.setBookingReference(saved.getBooking_reference());
        response.setBookingDate(saved.getBooking_date());
        response.setPaymentStatus(String.valueOf(saved.getPayment_status()));
        response.setAttendeeName(saved.getAttendee().getName());
        response.setEventTitle(String.valueOf(saved.getTicketType().getEvent()));
        response.setPrice(saved.getTicketType().getPrice());

        return response;
    }


    @Transactional
    public BookingResponseDTO cancelBooking(BookingRequestDTO dto, Integer id) throws Exception {
        // Verify the booking exists and is not already cancelled
        Booking booking = bookingRepository.findByBookingId(id)
                .orElseThrow(() -> new Exception("Booking not found for attendee Id: " + dto.getAttendeeId()));

        if (booking.getPayment_status().equals("Cancelled")) {
            throw new Exception("This Booking has already been cancelled for attende Id: " + dto.getAttendeeId());
        }

        // Set payment_status to "CANCELLED"
        int rowsChanged = bookingRepository.cancelBookingById(booking.getBooking_id());

        if (rowsChanged <= 0) {
            throw new Exception("Error while cancelling ticket");
        }

        // Increment quantity_available on TicketType by 1
        ticketTypeRepository.incrementQuantityAvailable(dto.getTicketTypeId());

        // Save changes to our bookingRepo
        Booking saved = bookingRepository.save(booking);

        // Convert our saved booking object to BookingRequestDTO to return result
        BookingResponseDTO response = new BookingResponseDTO();
        response.setBookingReference(saved.getBooking_reference());
        response.setBookingDate(saved.getBooking_date());
        response.setPaymentStatus(String.valueOf(saved.getPayment_status()));
        response.setAttendeeName(saved.getAttendee().getName());
        response.setEventTitle(String.valueOf(saved.getTicketType().getEvent()));
        response.setPrice(saved.getTicketType().getPrice());

        return response;
    }
}

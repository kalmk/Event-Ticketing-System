package com.example.event_ticketing_system.service;

import com.example.event_ticketing_system.dto.BookingRequestDTO;
import com.example.event_ticketing_system.dto.BookingResponseDTO;
import com.example.event_ticketing_system.entity.Attendee;
import com.example.event_ticketing_system.entity.Booking;
import com.example.event_ticketing_system.entity.TicketType;
import com.example.event_ticketing_system.repository.AttendeeRepository;
import com.example.event_ticketing_system.repository.BookingRepository;
import com.example.event_ticketing_system.repository.TicketTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.UUID;

@RestControllerAdvice   // Using this to catch 'EntityNotFoundException' and throw a 404 not found error
@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final AttendeeRepository attendeeRepository;

    // Do I need this constructor?
    public BookingService(BookingRepository bookingRepository, TicketTypeRepository ticketTypeRepository, AttendeeRepository attendeeRepository) {
        this.bookingRepository = bookingRepository;
        this.ticketTypeRepository = ticketTypeRepository;
        this.attendeeRepository = attendeeRepository;
    }

    @Transactional
    public BookingResponseDTO createBooking(BookingRequestDTO dto) throws Exception {
        // Check if the ticket exists
        boolean available = bookingRepository.ticketExistsInQuantity(
                dto.getTicketTypeId()
        );
        
        if (!available) {
            throw new Exception("Tickets sold out for ticket id: " + dto.getTicketTypeId());
        }

        // Check if the attendee has already booked the same ticket
        boolean alreadyBookedTicketType = bookingRepository.existsByAttendeeAttendeeIdAndTicketTypeId(
                dto.getAttendeeId(),
                dto.getTicketTypeId()
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

        // Get Attendee and TicketType to finish building BookingResponseDTO
        Attendee attendee = attendeeRepository.findById(dto.getAttendeeId())
                .orElseThrow(() -> new Exception("Attendee not found with id: " + dto.getAttendeeId()));
        TicketType ticketType = ticketTypeRepository.findById(dto.getTicketTypeId())
                .orElseThrow(() -> new Exception("Ticket Type not found with id: " + dto.getTicketTypeId()));

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
}

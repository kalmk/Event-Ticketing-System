package com.example.event_ticketing_system.service;

import com.example.event_ticketing_system.dto.AttendeeBookingsDTO;
import com.example.event_ticketing_system.dto.BookingResponseDTO;
import com.example.event_ticketing_system.entity.Booking;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import com.example.event_ticketing_system.dto.AttendeeRequestDTO;
import com.example.event_ticketing_system.dto.AttendeeResponseDTO;
import com.example.event_ticketing_system.entity.Attendee;
import com.example.event_ticketing_system.repository.AttendeeRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

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

    public AttendeeBookingsDTO getAttendeeBookings(Integer attendee_id) throws Exception {
        // Find Attendee to get their name
        Attendee attendee = attendeeRepository.findById(attendee_id)
                .orElseThrow(() -> new Exception("Attendee not found for id: " + attendee_id));

        // Get list of bookings
        List<Booking> bookingList = attendeeRepository.findBookingsByAttendeeId(attendee_id);

        List<BookingResponseDTO> bookingResponseDTOList = new ArrayList<>();

        // Convert list of bookings to list of BookingResponseDTO
        for (Booking booking : bookingList) {
            // Use function to convert booking to BookingResponseDTO
            BookingResponseDTO bookingResponseDTO = getBookingResponseDTO(booking);

            // Append filled BookingResponseDTO object
            bookingResponseDTOList.add(bookingResponseDTO);
        }

        AttendeeBookingsDTO response = new AttendeeBookingsDTO();
        response.setAttendee_name(attendee.getName());
        response.setBookings(bookingResponseDTOList);

        return response;
    }

    // Used this function inside a for each loop in getAttendeeBookings() function
    private static @NonNull BookingResponseDTO getBookingResponseDTO(Booking booking) {
        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();

        // Fill BookingResponseDTO object
        bookingResponseDTO.setBookingId(booking.getBooking_id());
        bookingResponseDTO.setBookingReference(booking.getBooking_reference());
        bookingResponseDTO.setBookingDate(booking.getBooking_date());
        bookingResponseDTO.setPaymentStatus(String.valueOf(booking.getPayment_status()));
        bookingResponseDTO.setAttendeeName(booking.getAttendee().getName());
        bookingResponseDTO.setEventTitle(booking.getTicketType().getEvent().getTitle());
        bookingResponseDTO.setTicketTypeName(booking.getTicketType().getName());
        bookingResponseDTO.setPrice(booking.getTicketType().getPrice());

        return bookingResponseDTO;
    }
}

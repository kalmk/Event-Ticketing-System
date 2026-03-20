package com.example.event_ticketing_system.repository;

import com.example.event_ticketing_system.entity.Attendee;
import com.example.event_ticketing_system.entity.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.event_ticketing_system.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    // JPA Manual query to check if a ticket type exists AND is available
    @Query("SELECT COUNT(t) > 0 FROM TicketType t " +
            "WHERE t.ticket_type_id = :ticketTypeId AND t.quantity_available > 0")
    boolean ticketExistsInQuantity(@Param("ticketTypeId") Integer ticketTypeId);

    // JPA automatic query to check if this attendee already has booked this ticketType
    boolean existsByAttendeeAndTicketType(Attendee attendee, TicketType ticketType);
}

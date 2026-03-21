package com.example.event_ticketing_system.repository;

import com.example.event_ticketing_system.entity.Attendee;
import com.example.event_ticketing_system.entity.TicketType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.event_ticketing_system.entity.Booking;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query("SELECT b FROM Booking b WHERE b.booking_id = :booking_id")
    Optional<Booking> findByBookingId(Integer booking_id);

    @Modifying
    @Transactional
    @Query("UPDATE Booking b SET b.payment_status = 'CANCELLED' " +
            "WHERE b.booking_id = :booking_id")
    int cancelBookingById(Integer booking_id);

    @Query("SELECT COUNT(t) > 0 FROM TicketType t " +
            "WHERE t.ticket_type_id = :ticketTypeId AND t.quantity_available > 0")
    boolean ticketExistsInQuantity(@Param("ticketTypeId") Integer ticketTypeId);

    @Query("SELECT COUNT(b) > 0 FROM Booking b WHERE b.attendee = :attendee AND b.ticketType = :ticketType")
    boolean existsByAttendeeAndTicketType(@Param("attendee") Attendee attendee,
            @Param("ticketType") TicketType ticketType);
}

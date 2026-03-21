package com.example.event_ticketing_system.repository;

import com.example.event_ticketing_system.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.event_ticketing_system.entity.Attendee;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendeeRepository extends JpaRepository<Attendee, Integer> {
    // manual JPQL query
    @Query("SELECT COUNT(a) > 0 FROM Attendee a WHERE a.email = :email")
    boolean emailExists(@Param("email") String email);

    // Query used in BookingService to find attendee in order to finalize booking
    Optional<Attendee> findById(Integer attendeeId);

    @Query("SELECT b FROM Booking b WHERE b.attendee.attendee_id = :attendee_id")
    List<Booking> findBookingsByAttendeeId(@Param("attendee_id") Integer attendee_id);
}
package com.example.event_ticketing_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.event_ticketing_system.entity.Attendee;

@Repository
public interface AttendeeRepository extends JpaRepository<Attendee, Integer> {
    //manual JPQL query
    @Query("SELECT COUNT(a) > 0 FROM Attendee a WHERE a.email = :email")
    boolean emailExists(@Param("email") String email);

    // Query used in BookingService to find attendee in order to finalize booking
    Attendee findById(Integer attendeeId);
}
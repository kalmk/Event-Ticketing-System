package com.example.event_ticketing_system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.event_ticketing_system.dto.RevenueDTO;
import com.example.event_ticketing_system.entity.Event;
import com.example.event_ticketing_system.entity.Event.EventStatus;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByStatus(EventStatus status);

    @Query("SELECT new com.example.event_ticketing_system.dto.RevenueDTO(e.title, SUM(t.price)) " +
            "FROM Booking b JOIN b.ticketType t JOIN t.event e " +
            "WHERE e.event_id = :event_id AND b.payment_status = 'CONFIRMED' " +
            "GROUP BY e.title")
    Optional<RevenueDTO> getRevenueDTO(@Param("event_id") Integer event_id);
}

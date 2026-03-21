package com.example.event_ticketing_system.repository;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.event_ticketing_system.entity.Event;
import com.example.event_ticketing_system.entity.TicketType;

@Repository
public interface TicketTypeRepository extends JpaRepository<TicketType, Integer>{
    
    List<TicketType> findByEvent(Event event);

    // Query used to book a ticket by decrementing quantity_available by 1
    @Modifying
    @Transactional
    @Query("UPDATE TicketType t SET t.quantity_available = t.quantity_available - 1 " +
            "WHERE t.ticket_type_id = :ticketTypeId")
    void decrementQuantityAvailable(@Param("ticketTypeId") Integer ticketTypeId);

    @Modifying
    @Transactional
    @Query("UPDATE TicketType t SET t.quantity_available = t.quantity_available + 1" +
           "WHERE t.ticket_type_id = :ticketTypeId")
    void incrementQuantityAvailable(@Param("ticketTypeId") Integer ticketTypeId);

    // Query used in BookingService to find ticket in order to finalize booking
    Optional<TicketType> findTicketTypeByTicket_type_id(Integer ticketTypeId);
}

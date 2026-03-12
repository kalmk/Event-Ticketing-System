package com.example.event_ticketing_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.event_ticketing_system.entity.Event;
import com.example.event_ticketing_system.entity.TicketType;

@Repository
public interface TicketTypeRepository extends JpaRepository<TicketType, Integer>{
    
    List<TicketType> findByEvent(Event event);
}

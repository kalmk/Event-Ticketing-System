package com.example.event_ticketing_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.event_ticketing_system.entity.TicketType;

@Repository
public interface TicketTypeRepository extends JpaRepository<TicketType, Integer>{
    
}

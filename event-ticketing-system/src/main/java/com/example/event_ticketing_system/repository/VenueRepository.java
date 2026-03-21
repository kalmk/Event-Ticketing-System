package com.example.event_ticketing_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.event_ticketing_system.entity.Venue;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Integer> {

}

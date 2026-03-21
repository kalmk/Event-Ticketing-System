package com.example.event_ticketing_system.service;

import com.example.event_ticketing_system.dto.EventRequestDTO;
import com.example.event_ticketing_system.dto.EventResponseDTO;
import com.example.event_ticketing_system.dto.TicketTypeDTO;
import com.example.event_ticketing_system.entity.Event;
import com.example.event_ticketing_system.entity.Event.EventStatus;
import com.example.event_ticketing_system.entity.Organizer;
import com.example.event_ticketing_system.entity.TicketType;
import com.example.event_ticketing_system.entity.Venue;
import com.example.event_ticketing_system.repository.EventRepository;
import com.example.event_ticketing_system.repository.OrganizerRepository;
import com.example.event_ticketing_system.repository.TicketTypeRepository;
import com.example.event_ticketing_system.repository.VenueRepository;

import lombok.RequiredArgsConstructor;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class EventService {

	private final EventRepository eventRepository;
	private final OrganizerRepository organizerRepository;
	private final VenueRepository venueRepository;
	private final TicketTypeRepository ticketTypeRepository;

	@Transactional
	public EventResponseDTO createEvent(EventRequestDTO dto) {
		Organizer organizer = organizerRepository.findById(dto.getOrganizer_id())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Organizer not found"));

		Venue venue = venueRepository.findById(dto.getVenue_id())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Venue not found"));

		// Validate ticket quantities before saving
		int totalTickets = dto.getTicketTypes()
				.stream()
				.mapToInt(TicketTypeDTO::getQuantity_available)
				.sum();

		if (totalTickets != venue.getTotal_capacity()) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST,
					"Total tickets must equal venue capacity");
		}

		Event event = new Event();
		event.setTitle(dto.getTitle());
		event.setDescription(dto.getDescription());
		event.setEvent_date(dto.getEvent_date());
		event.setStatus(dto.getStatus());
		event.setOrganizer(organizer);
		event.setVenue(venue);

		Event savedEvent = eventRepository.save(event);

		// Save ticket types and convert to DTO list
		List<TicketTypeDTO> ticketDTOList = dto.getTicketTypes().stream().map(ticketDTO -> {

			TicketType ticket = new TicketType();
			ticket.setName(ticketDTO.getName());
			ticket.setPrice(ticketDTO.getPrice());
			ticket.setQuantity_available(ticketDTO.getQuantity_available());
			ticket.setEvent(savedEvent);

			TicketType savedTicket = ticketTypeRepository.save(ticket);

			return new TicketTypeDTO(
					savedTicket.getTicket_type_id(),
					savedTicket.getName(),
					savedTicket.getPrice(),
					savedTicket.getQuantity_available());

		}).toList();

		EventResponseDTO response = new EventResponseDTO();
		response.setEvent_id(savedEvent.getEvent_id());
		response.setTitle(savedEvent.getTitle());
		response.setDescription(savedEvent.getDescription());
		response.setEvent_date(savedEvent.getEvent_date());
		response.setStatus(savedEvent.getStatus().name());
		response.setOrganizerName(savedEvent.getOrganizer().getName());
		response.setVenueName(savedEvent.getVenue().getName());
		response.setTicketTypes(ticketDTOList);

		return response;
	}

	// Get all UPCOMING events
	public List<EventResponseDTO> getUpcomingEvents() {
		List<Event> events = eventRepository.findByStatus(EventStatus.UPCOMING);

		return events.stream().map(event -> {

			List<TicketTypeDTO> ticketTypes = ticketTypeRepository
					.findByEvent(event)
					.stream()
					.map(ticket -> new TicketTypeDTO(
							ticket.getTicket_type_id(),
							ticket.getName(),
							ticket.getPrice(),
							ticket.getQuantity_available()))
					.toList();

			EventResponseDTO response = new EventResponseDTO();
			response.setEvent_id(event.getEvent_id());
			response.setTitle(event.getTitle());
			response.setDescription(event.getDescription());
			response.setEvent_date(event.getEvent_date());
			response.setStatus(event.getStatus().name());
			response.setOrganizerName(event.getOrganizer().getName());
			response.setVenueName(event.getVenue().getName());
			response.setTicketTypes(ticketTypes);

			return response;

		}).toList();
	}

	// Get event by id
	public EventResponseDTO getEventById(Integer id) {

		Event event = eventRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND, "Event not found"));

		List<TicketTypeDTO> ticketTypes = ticketTypeRepository
				.findByEvent(event)
				.stream()
				.map(ticket -> new TicketTypeDTO(
						ticket.getTicket_type_id(),
						ticket.getName(),
						ticket.getPrice(),
						ticket.getQuantity_available()))
				.toList();

		EventResponseDTO response = new EventResponseDTO();
		response.setEvent_id(event.getEvent_id());
		response.setTitle(event.getTitle());
		response.setDescription(event.getDescription());
		response.setEvent_date(event.getEvent_date());
		response.setStatus(event.getStatus().name());
		response.setOrganizerName(event.getOrganizer().getName());
		response.setVenueName(event.getVenue().getName());
		response.setTicketTypes(ticketTypes);

		return response;
	}
}
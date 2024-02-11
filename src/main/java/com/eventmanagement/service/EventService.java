package com.eventmanagement.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventmanagement.exception.DateException;
import com.eventmanagement.exception.EventException;
import com.eventmanagement.pojos.Event;
import com.eventmanagement.repository.EventRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // Fetch all events
    public List<Event> getAllEvents() {
        logger.info("Fetching all events");
        return eventRepository.findAll();
    }

    // Create a new event
    public Event createEvent(Event event) {
        logger.info("Creating event: {}", event);
        // Check if the event date is in the past
        if (event.getDate().isBefore(LocalDate.now())) {
            throw new DateException("Event date cannot be in the past");
        }
        // Check if the event name already exists
        if (eventRepository.findByName(event.getName()).isPresent()) {
            throw new EventException ("Event with the same name already exists");
        }
        return eventRepository.save(event);
    }

    // Update event details
    public Event updateEvent(Long eventId, Event updatedEvent) {
        logger.info("Updating event with ID: {}", eventId);
        Event existingEvent = eventRepository.findById(eventId).orElse(null);
        if (existingEvent != null) {
            // Check if the event date is in the past
            if (updatedEvent.getDate().isBefore(LocalDate.now())) {
                throw new DateException("Event date cannot be in the past");
            }
            // Update event details
            existingEvent.setName(updatedEvent.getName());
            existingEvent.setDate(updatedEvent.getDate());
            existingEvent.setLocation(updatedEvent.getLocation());
            return eventRepository.save(existingEvent);
        } else {
            throw new EventException ("Event doesn't exist");
        }
    }

    // Get an event by name
    public Event getEventByName(String eventName) {
        logger.info("Fetching event by name: {}", eventName);
        Optional<Event> event = eventRepository.findByName(eventName);
        return event.orElseThrow(() -> new EventException("Event doesn't exist"));
    }
    
    // Get an event by ID
    public Event getEventById(Long eventId) {
        logger.info("Fetching event by ID: {}", eventId);
        Optional<Event> event = eventRepository.findById(eventId);
        return event.orElseThrow(() -> new EventException("Event doesn't exist"));
    }
    
    // Delete an event by ID
    public boolean deleteEventById(Long eventId) {
        logger.info("Deleting event with ID: {}", eventId);
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isPresent()) {
            eventRepository.deleteById(eventId);
            return true;
        } else {
            throw new EventException("Event doesn't exist");
        }
    }
}

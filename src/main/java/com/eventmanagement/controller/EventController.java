package com.eventmanagement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.eventmanagement.pojos.Event;
import com.eventmanagement.service.EventService;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> getAllEvents() {
        logger.info("Fetching all events");
        return eventService.getAllEvents();
    }

    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        logger.info("Creating event: {}", event.getName());
        return eventService.createEvent(event);
    }

    @PutMapping("/{eventId}")
    public Event updateEvent(@PathVariable Long eventId, @RequestBody Event event) {
        logger.info("Updating event with ID: {}", eventId);
        return eventService.updateEvent(eventId, event);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Event> getEventByName(@PathVariable String name) {
        logger.info("Fetching event by name: {}", name);
        Event event = eventService.getEventByName(name);
        if (event != null) {
            return ResponseEntity.ok(event);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long eventId) {
        logger.info("Deleting event with ID: {}", eventId);
        boolean deleted = eventService.deleteEventById(eventId);
        if (deleted) {
            return ResponseEntity.ok("Event deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

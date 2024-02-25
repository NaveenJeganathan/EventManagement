package com.eventmanagement.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eventmanagement.pojos.*;
import com.eventmanagement.service.*;
// in
// irr
@RestController
@RequestMapping("/attendee")
public class AttendeeController {

    private final AttendeeService attendeeService;
    private static final Logger logger = LoggerFactory.getLogger(AttendeeController.class);

    @Autowired
    public AttendeeController(AttendeeService attendeeService) {
        this.attendeeService = attendeeService;
       
    }

    @GetMapping
    public List<Attendee> getAllAttendee() {
        logger.info("Fetching all attendees");
        return attendeeService.getAllAttendee();
    }

    @PostMapping
    public Attendee createAttendee(@RequestBody Attendee attendee) {
        logger.info("Creating attendee: {}", attendee.getName());
        return attendeeService.createAttendee(attendee);
    }

    @PutMapping("/{attendeeId}")
    public Attendee updateAttendee(@PathVariable Long attendeeId, @RequestBody Attendee attendee) {
        logger.info("Updating attendee with ID: {}", attendeeId);
        return attendeeService.updateAttendee(attendeeId, attendee);
    }

    @PostMapping("/register/{eventId}")
    public ResponseEntity<String> registerForEvent(@PathVariable Long eventId, @RequestBody Attendee attendee) {
        logger.info("Registering attendee for event with ID: {}", eventId);
        String result = attendeeService.registerForEvent(eventId, attendee);
        if (result.equals("Successfully registered for the event")) {
            return ResponseEntity.ok(result);
        } else 
            return ResponseEntity.badRequest().body(result);
        
    }

    @GetMapping("/{name}")
    public ResponseEntity<Attendee> getAttendeeByName(@PathVariable String name) {
        logger.info("Fetching attendee by name: {}", name);
        Attendee attendee = attendeeService.getAttendeeByName(name);
        if (attendee != null) {
            return ResponseEntity.ok(attendee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{attendeeId}")
    public ResponseEntity<String> deleteAttendee(@PathVariable Long attendeeId) {
        logger.info("Deleting attendee with ID: {}", attendeeId);
        boolean deleted = attendeeService.deleteAttendeeById(attendeeId);
        if (deleted) {
            return ResponseEntity.ok("Attendee deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

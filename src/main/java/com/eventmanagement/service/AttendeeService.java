package com.eventmanagement.service;

import com.eventmanagement.exception.AttendeeException;
import com.eventmanagement.exception.EventException;
import com.eventmanagement.pojos.Attendee;
import com.eventmanagement.pojos.Event;
import com.eventmanagement.repository.AttendeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final EventService eventService;

    private static final Logger logger = LoggerFactory.getLogger(AttendeeService.class);

    @Autowired
    public AttendeeService(AttendeeRepository attendeeRepository, EventService eventService) {
        this.attendeeRepository = attendeeRepository;
        this.eventService = eventService;
    }

    // Fetch all attendees
    public List<Attendee> getAllAttendee() {
        logger.info("Fetching all attendees");
        return attendeeRepository.findAll();
    }

    // Create a new attendee
    public Attendee createAttendee(Attendee attendee) {
        logger.info("Creating attendee: {}", attendee);
        // Check if an attendee with the same email or phone number already exists
        if (attendeeRepository.findByEmail(attendee.getEmail()).isPresent()) {
            throw new AttendeeException("Attendee with the same email already exists");
        }
        if (attendeeRepository.findByPhone(attendee.getPhone()).isPresent()) {
            throw new AttendeeException("Attendee with the same phone number already exists");
        }
        return attendeeRepository.save(attendee);
    }

    // Update attendee details
    public Attendee updateAttendee(Long attendeeId, Attendee updatedAttendee) {
        logger.info("Updating attendee with ID: {}", attendeeId);
        Attendee existingAttendee = attendeeRepository.findById(attendeeId).orElse(null);
        if (existingAttendee != null) {
            // Check if email or phone number is unique if updated
            if (!existingAttendee.getEmail().equals(updatedAttendee.getEmail())
                    && attendeeRepository.findByEmail(updatedAttendee.getEmail()).isPresent()) {
                throw new AttendeeException("Attendee with the same email already exists");
            }
            if (!existingAttendee.getPhone().equals(updatedAttendee.getPhone())
                    && attendeeRepository.findByPhone(updatedAttendee.getPhone()).isPresent()) {
                throw new AttendeeException("Attendee with the same phone number already exists");
            }
            // Update attendee details
            existingAttendee.setName(updatedAttendee.getName());
            existingAttendee.setEmail(updatedAttendee.getEmail());
            existingAttendee.setPhone(updatedAttendee.getPhone());
            existingAttendee.setEvent(updatedAttendee.getEvent());
            return attendeeRepository.save(existingAttendee);
        } else {
            throw new AttendeeException("Attendee doesn't exist. Please register.");
        }
    }

    // Register an attendee for an event
    public String registerForEvent(Long eventId, Attendee attendee) {
        logger.info("Registering attendee for event with ID: {}", eventId);
        // Get the event by ID
        Event event = eventService.getEventById(eventId);
        // Find the attendee by email or phone number
        Attendee existingAttendee = attendeeRepository.findByEmail(attendee.getEmail())
                .orElse(attendeeRepository.findByPhone(attendee.getPhone()).orElse(null));

        // Check if the event and attendee exist
        if (event == null) {
            throw new EventException("Event doesn't exist");
        } else if (existingAttendee == null) {
            throw new AttendeeException("Attendee doesn't exist");
        } else if (existingAttendee.getEvent() != null && existingAttendee.getEvent().contains(event.getName())) {
            throw new AttendeeException("Attendee already registered for this event");
        } else {
            // Add the event to the attendee's list of events
            if (existingAttendee.getEvent() == null || existingAttendee.getEvent().isEmpty()) {
                ArrayList<String> eventName = new ArrayList<>();
                eventName.add(event.getName());
                existingAttendee.setEvent(eventName);
            } else {
                existingAttendee.getEvent().add(event.getName());
            }
            // Update the attendee
            updateAttendee(existingAttendee.getId(), existingAttendee);
            return "Successfully registered for the event";
        }
    }

    // Get an attendee by name
    public Attendee getAttendeeByName(String name) {
        logger.info("Fetching attendee by name: {}", name);
        Optional<Attendee> attendee = attendeeRepository.findByName(name);
        return attendee.orElseThrow(() -> new AttendeeException("Attendee doesn't exist"));
    }

    // Delete an attendee by ID
    public boolean deleteAttendeeById(Long attendeeId) {
        logger.info("Deleting attendee with ID: {}", attendeeId);
        if (attendeeRepository.existsById(attendeeId)) {
            attendeeRepository.deleteById(attendeeId);
            return true;
        } else {
            throw new AttendeeException("Attendee doesn't exist");
        }
    }
}

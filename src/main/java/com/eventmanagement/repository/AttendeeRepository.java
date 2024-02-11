package com.eventmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventmanagement.pojos.Attendee;

public interface AttendeeRepository extends JpaRepository<Attendee, Long> {
	 Optional<Attendee> findByEmail(String email);

	    Optional<Attendee> findByPhone(String phone);

	    Optional<Attendee> findByName(String name);
	    }
package com.eventmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventmanagement.pojos.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
	 Optional<Event> findByName(String name);
	
	
	 }
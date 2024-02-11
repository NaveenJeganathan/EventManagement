package com.eventmanagement.pojos;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "event")
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private LocalDate date;
	private String location;
	
	public Event() {
		super();
	}
	
	public Event(Long id, String name, LocalDate date, String location) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
		this.location = location;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	
	@Override
	public String toString() {
		return "Event [id=" + id + ", name=" + name + ", date=" + date + ", location=" + location +  "]";
	}

	

}

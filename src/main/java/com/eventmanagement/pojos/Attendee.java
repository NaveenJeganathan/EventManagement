package com.eventmanagement.pojos;

import java.util.ArrayList;

import javax.persistence.*;

@Entity
@Table(name = "attendees")
public class Attendee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String phone;
	private String email;
	private ArrayList<String> event;

	public Attendee() {
		super();
	}

	public Attendee(Long id, String name, String phone, String email, ArrayList<String> event) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.event = event;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<String> getEvent() {
		return event;
	}

	public void setEvent(ArrayList<String> event) {
		this.event = event;
	}

	@Override
	public String toString() {
		return "Attendee [id=" + id + ", name=" + name + ", phone=" + phone + ", email=" + email + ", event=" + event
				+ "]";
	}

	

	
}

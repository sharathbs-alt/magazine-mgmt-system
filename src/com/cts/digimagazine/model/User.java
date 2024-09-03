package com.cts.digimagazine.model;

import java.util.Date;
public class User {
	private int userId;
	private String username;
	private String email;
	private Date dateOfBirth;
	private Date registrationDate;
	
	public User(int userId, String username, String email, Date dateOfBirth, Date registrationDate) {
		super();
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
		this.registrationDate = registrationDate;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	
}

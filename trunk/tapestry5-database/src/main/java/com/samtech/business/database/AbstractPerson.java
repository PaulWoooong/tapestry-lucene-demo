package com.samtech.business.database;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



public abstract class AbstractPerson {
	
	@Column(name="name",length=30,nullable=false)
	private String name;
	@Enumerated(EnumType.STRING)
	@Column(name="gender",length=10)
	private Gender gender=Gender.MALE;
	@Temporal(TemporalType.DATE)
	@Column(name="birth_day")
	private Date birthDay;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public Date getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	
	
}

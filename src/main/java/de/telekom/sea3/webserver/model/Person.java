package de.telekom.sea3.webserver.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="persons")
public class Person {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Column(name="FIRSTNAME")
	private String firstname;
	
	@Column(name="LASTNAME")
	private String lastname;
	
	@Column(name="SALUTATION")
	private String salutation;
	
	@Column(name="BIRTHDATE")
	private LocalDate birthday;
	
	@Column(name="Email")
	private String emailaddress;

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmailaddress() {
		return emailaddress;
	}
	public boolean setEmailaddress(String emailAddress) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		if (emailAddress.matches(regex) && emailAddress.length()<101) {
			this.emailaddress = emailAddress;
			return true;
		}
		return false;		
	}
	
	public LocalDate getBirthday() {
		return birthday;
	}
	public void setBirthday(LocalDate date) {
		this.birthday = date;
	}
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getSalutation() {
		return salutation;
	}
	
	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}
	
	/*
	public void setSalutation(String salutation) {
		this.salutation = Salutation.fromString(salutation);
	}*/
	
	public Person(String firstname, String lastname, String salutation) {
		this.firstname=firstname;
		this.lastname=lastname;
		this.salutation=salutation;
		id=-1L;
	}
	
	public Person() {
		id=-1L;
	}
	
	public String toString() {
		return this.salutation.toString() + " " + firstname + " " + lastname;
	}
}

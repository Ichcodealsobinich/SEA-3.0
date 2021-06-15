package de.telekom.sea3.webserver.model;

import java.time.LocalDate;
import java.util.Date;

public class Person {

	private String firstname;
	private String lastname;
	private Salutation salutation;
	/*private LocalDate birthday;
	
	public LocalDate getBirthday() {
		return birthday;
	}
	public void setBirthday(LocalDate date) {
		this.birthday = date;
	}*/
	
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
	public Salutation getSalutation() {
		return salutation;
	}
	public void setSalutation(Salutation salutation) {
		this.salutation = salutation;
	}
	
	public void setSalutation(String salutation) {
		this.salutation = Salutation.fromString(salutation);
	}
}

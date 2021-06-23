/*create Table persons (ID BIGINT PRIMARY KEY AUTO_INCREMENT Not null, 
 * VERSION BIGINT NOT NULL DEFAULT 1, 
 * FIRSTNAME Varchar(40), 
 * LASTNAME Varchar(40), 
 * SALUTATION TINYINT, 
 * Email VARCHAR(100), 
 * BIRTHDATE Date);
 * */

package de.telekom.sea3.webserver.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="persons")
public class Person {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Version
	@Column(name="VERSION")
	private Long version;
	
	@Column(name="FIRSTNAME")
	private String firstname;
	
	@Column(name="LASTNAME")
	private String lastname;
	
	
	/* Using ordinal is more memory friendly than String,
	 * provided that the db column is from type INT.
	 * Otherwise this has no advantage.
	 */
	@Enumerated(EnumType.ORDINAL) 
	@Column(name="SALUTATION")
	private Salutation salutation;
	
	@Column(name="BIRTHDATE")
	private LocalDate birthday;
	
	@Column(name="Email")
	private String emailaddress;

	
	public Long getVersion() {
		return version;
	}
	
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
		if (emailAddress.strip().matches(regex)) {
			if (emailAddress.length()<101){
				this.emailaddress = emailAddress.strip();
				return true;
			} else {
				return false;
			} 
		} else {
			return false;
		}		
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
		return salutation.toString();
	}
	
	public void setSalutation(String salutation) {
		this.salutation = Salutation.fromString(salutation);
	}
	
	/*
	public void setSalutation(String salutation) {
		this.salutation = Salutation.fromString(salutation);
	}*/
	
	public Person(String firstname, String lastname, String salutation) {
		this.firstname=firstname;
		this.lastname=lastname;
		this.salutation=Salutation.fromString(salutation);
		id=-1L;
	}
	
	public Person() {
		id=-1L;
	}
	
	public String toString() {
		return this.salutation.toString() + " " + firstname + " " + lastname;
	}
}

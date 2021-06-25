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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import de.telekom.sea3.webserver.validations.EmailAddress;
import de.telekom.sea3.webserver.validations.Name;

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
	@NotNull(message = "Der Vorname darf nicht leer sein")
	@Length(min=2, max=40, message="Der Vorname muss zwischen 2 und 40 Zeichen lang sein")
	@Name(message="Der Vorname darf nur Buchstaben enthalten")
	private String firstname;
	
	@NotNull(message = "Der Vorname darf nicht leer sein")
	@Length(min=2, max=40, message="Der Vorname muss zwischen 2 und 40 Zeichen lang sein")
	@Column(name="LASTNAME")
	@Name(message="Der Nachname darf nur Buchstaben enthalten")
	private String lastname;
	
	
	/* Using ordinal is more memory friendly than String,
	 * provided that the db column is from type INT.
	 * Otherwise this has no advantage.
	 */
	@Enumerated(EnumType.ORDINAL) 
	@Column(name="SALUTATION")
	private Salutation salutation;
	
	@Column(name="BIRTHDATE")
	@Past(message="Geburtstag muss in der Vergangenheit liegen")
	private LocalDate birthday;
	
	@Column(name="Email")
	/* @Email(message="Die Emailadresse muss ein gültiges Format haben") //doesnt work
	@Pattern(regexp = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$", 
			 message="Email muss ein gültiges Format haben")*/
	@EmailAddress
	private String emailaddress;
	
	@Column(name="location")
	private String location;
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

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
		this.emailaddress = emailAddress.strip();
		return true;
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

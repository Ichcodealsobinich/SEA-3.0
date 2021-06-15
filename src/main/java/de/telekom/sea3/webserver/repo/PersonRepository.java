package de.telekom.sea3.webserver.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import de.telekom.sea3.webserver.model.Person;
import de.telekom.sea3.webserver.model.Persons;

@Repository
public class PersonRepository {

	private Persons persons = new Persons();
	
	public int getSize() {
		return persons.size();
	}
	
	public Persons getAllPersons() {
		return persons;
	}
	
	public Person getPerson(int id) {
		return persons.getPerson(id);
	}
	
	public Person addPerson(Person p) {
		persons.addPerson(p);
		return p;		
	}
}

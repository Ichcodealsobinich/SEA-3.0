package de.telekom.sea3.webserver.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.telekom.sea3.webserver.repo.PersonRepository;
import de.telekom.sea3.webserver.model.*;

@Service
public class PersonService {

	private PersonRepository personrepository;


	@Autowired
	public PersonService(PersonRepository personrepository) {
		this.personrepository = personrepository;
	}
	
	public int getSize() {
		return personrepository.getSize();
	}
	
	public Persons getAllPersons() {
		return personrepository.getAllPersons();
	}
	
	public Person getPerson(int id) {
		return personrepository.getPerson(id);
	}


	public Person addPerson(Person p) {
		return personrepository.addPerson(p);		
	}
	
	public boolean delete(int id) {
		return personrepository.delete(id);
	}
	
	public Person update(int id, Person p) {
		return personrepository.update(id, p);
	}
	
}

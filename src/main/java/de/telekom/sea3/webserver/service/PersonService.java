package de.telekom.sea3.webserver.service;

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
		return new Persons();
	}
	
}

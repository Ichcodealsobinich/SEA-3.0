package de.telekom.sea3.webserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.telekom.sea3.webserver.repo.PersonRepository;

@Service
public class PersonService {

	private PersonRepository personrepository;

	@Autowired
	public PersonService(PersonRepository personrepository) {
		this.personrepository = personrepository;
	}
	
}

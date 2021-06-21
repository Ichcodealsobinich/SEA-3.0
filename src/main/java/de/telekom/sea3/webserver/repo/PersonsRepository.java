package de.telekom.sea3.webserver.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.telekom.sea3.webserver.model.Person;


@Repository
public interface PersonsRepository extends CrudRepository<Person, Long>{
	//No code necessary
	//This is needed for Springboot to work.
	//DO NOT DELETE!
	
}

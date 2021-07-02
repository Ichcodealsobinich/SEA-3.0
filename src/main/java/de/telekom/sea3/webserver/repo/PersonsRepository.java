package de.telekom.sea3.webserver.repo;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.telekom.sea3.webserver.model.Person;


@Repository
public interface PersonsRepository extends CrudRepository<Person, Long>{
	//No code necessary
	//This is needed for Springboot to work.
	//DO NOT DELETE!
	
	//These queries are optional
	@Query(value="SELECT location from locations", nativeQuery=true)
	public List<String> getLocations();
	
	//Ignore errors for duplicate entries
	@Query(value="Insert IGNORE into locations (location) VALUES (:location)", nativeQuery=true)
	public void addLocation(String location);
	
	
}

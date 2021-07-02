package de.telekom.sea3.webserver.repo;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.telekom.sea3.webserver.model.Person;

@Repository
public interface PersonsRepositoryCustom extends CrudRepository<Person, Long> {
	public List<Person> genericFilter(HashMap<String, String> filter);
}

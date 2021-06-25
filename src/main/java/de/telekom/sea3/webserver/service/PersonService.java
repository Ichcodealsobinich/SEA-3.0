package de.telekom.sea3.webserver.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.telekom.sea3.webserver.repo.PersonsRepository;
import de.telekom.sea3.webserver.model.*;

@Service
public class PersonService {

	private PersonsRepository personsrepository;


	@Autowired
	public PersonService(PersonsRepository personsrepository) {
		this.personsrepository = personsrepository;
	}
	
	public List<Person> getAllPersons() {
		return (List<Person>) personsrepository.findAll();
	}
	
	public Optional<Person> getPerson(Long id) {
		return personsrepository.findById(id);
	}


	public Person addPerson(Person p) {
		/*if (p.getId()==null || p.getId()==-1L) {
			Long newId = personsrepository.getId()+1;
			System.out.println("id ist: + " + p.getId() + "; newId ist:" + newId);
			if (newId<1) {newId=1L;}
			p.setId(newId);
		}*/
		return personsrepository.save(p);		
	}
	
	public boolean delete(Long id) {
		try {
			personsrepository.deleteById(id);
			return true;
		}catch (IllegalArgumentException iAE) {
			return false;
		}
	}
	
	public Person update( Person p) {
		return personsrepository.save(p);
	}

	public long count() {
		return personsrepository.count();
	}
	
	public List<String> getLocations(){
		return personsrepository.getLocations();
	}
	
	public void addLocation(String location) {
		personsrepository.addLocation(location);
	}
	
}

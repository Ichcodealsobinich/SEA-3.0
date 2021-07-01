package de.telekom.sea3.webserver.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
	
	//First steps in functional programming and it looks awful :-(
	//Probably not a good idea to read all from DB and filter later
	public List<Person> filter(String filterAttribute, String filterValue) {
		List<Person> fList = (List<Person>) personsrepository.findAll();
		Predicate<Person> predicate = (p) -> (true);
		switch (filterAttribute) {
			case "location": predicate = (p) -> (p).getLocation().equals(filterValue); break;
			case "firstname":predicate = (p) -> (p).getFirstname().equals(filterValue); break;
			case "lastname": predicate = (p) -> (p).getLastname().equals(filterValue); break;
		}
		Collector<Person,?,List<Person>> collector = Collectors.toList();		
		return fList.stream().filter(predicate).collect(collector);
	}
	
	public List<Person> filterGeneric(HashMap<String, String> filter) {
		List<Person> fList = (List<Person>) personsrepository.findAll();
		Predicate<Person> predicate = (p) -> (true );
		Collector<Person,?,List<Person>> collector = Collectors.toList();	
		for (String f : filter.keySet()) {
			switch (f) {
				case "location": predicate = (p) -> (p).getLocation().equals(filter.get(f)); break;
				case "firstname":predicate = (p) -> (p).getFirstname().equals(filter.get(f));break;
				case "lastname": predicate = (p) -> (p).getLastname().equals(filter.get(f)); break;
			}
			fList = fList.stream().filter(predicate).collect(collector);
		}			
		return fList;
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

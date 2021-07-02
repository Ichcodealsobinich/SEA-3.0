package de.telekom.sea3.webserver.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.telekom.sea3.webserver.model.Person;
import de.telekom.sea3.webserver.model.Persons;
import de.telekom.sea3.webserver.repo.KnownFilters;
import de.telekom.sea3.webserver.service.PersonService;

@RestController
@Validated
public class PersonRESTController {

	private PersonService personservice;
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public PersonRESTController(PersonService ps) {
		this.personservice = ps;
	}
		
	/**@see <a href="http://localhost:8000/json/participants/all">URL</a>
	 * 
	 * @return A list of all participants as a JSON-String
	 */
	@GetMapping("/json/persons/all")
	public Persons getPersons(@RequestHeader HttpHeaders head) {
		String logMessage = String.format("Alle Personen abgerufen");
		logger.info(logMessage);
		List<Person> lp = personservice.getAllPersons();
		return new Persons(lp);
	}
	
	@GetMapping("/json/persons/filter/location/{location}")
	public Persons filterByLocation(@PathVariable("location") String location) {
		String logMessage = String.format("Alle Personen am Standort %s abgerufen", location);
		logger.info(logMessage);
		List<Person> lp = personservice.filter("location",location);
		return new Persons(lp);
	}
	
	@GetMapping("/json/persons/filter/firstname/{firstname}")
	public Persons filterByFirstname(@PathVariable("firstname") String firstname) {
		String logMessage = String.format("Alle Personen mit Vornamen %s abgerufen", firstname);
		logger.info(logMessage);
		List<Person> lp = personservice.filter("firstname",firstname);
		return new Persons(lp);
	}
	
	@GetMapping("/json/persons/filter")
	public Persons filterByWhatever(
			@RequestParam(name="firstname", required = false) String firstname,
			@RequestParam(name= "lastname", required = false) String lastname,
			@RequestParam(name="location", required = false) String location){
		HashMap<String, String> filter = new HashMap<String, String>();
		//for (String s: @RequestParams) {}
		if (firstname!=null) filter.put("firstname", firstname);
		if (lastname!=null) filter.put("lastname", lastname);
		if (location!=null) filter.put("location", location);
		
		//filterGeneric reads all entries from the database and then applies 
		//the given filters before returning the result
		List<Person> lp = personservice.filterGeneric(filter); 
		return new Persons(lp);
	}
	
	
	/**This endpoint is just like the one above but better:
	 * First of all, the list of request parameters is a generic list ("Map")
	 * with no need to know the parameters at compile time.
	 * 
	 * Second of all the method filterGeneric2 uses another approach: 
	 * Instead of loading all entries from the database and then apply filters,
	 * it builds a custom query based on the provided filters and then executes it 
	 * against the db. Should be more effient with large datasets
	 */
	@GetMapping("/json/person/filter")
	public Persons filterByWhatever2(@RequestParam Map<String,String> params){

		HashMap<String, String> filter = new HashMap<String, String>();
		for (var entry: params.entrySet()) {
			//KnownFilters contains a list of allowed filter parameters.
			//check if the current request parameter is in this list and
			//check if the corresponding value is not null
			if (KnownFilters.getFilters().contains(entry.getKey()) && entry.getValue()!=null) {
				filter.put(entry.getKey(), entry.getValue());
			}
		}
		
		//filterGeneric2 builds a custom SQL Query based on the given filters
		//and directly returns the result. Probably way more efficient with large 
		//datasets than filterGeneric
		List<Person> lp = personservice.filterGeneric2(filter);
		return new Persons(lp);
	}
	
	@GetMapping("/json/persons/filter/lastname/{lastname}")
	public Persons filterByLastname(@PathVariable("lastname") String lastname) {
		String logMessage = String.format("Alle Personen mit Vornamen %s abgerufen", lastname);
		logger.info(logMessage);
		List<Person> lp = personservice.filter("lastname",lastname);
		return new Persons(lp);
	}
	
	@GetMapping("/json/persons/{id}")
	public ResponseEntity<Person> getperson(@PathVariable("id") Long id) {
		
		String logMessage = String.format("Person mit der Id: %d abgerufen", id);
		logger.info(logMessage);
		
		Optional<Person> maybePerson = personservice.getPerson(id);		
		if (maybePerson.isEmpty()) {
			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Person>(maybePerson.get(),HttpStatus.OK );
		}

	}
	
	@PostMapping("/json/person")
	public ResponseEntity<Person> add(@Valid @RequestBody Person p){
		
		String logMessage = String.format("Neue Person soll angelegt werden");
		logger.info(logMessage);
		
		personservice.addLocation(p.getLocation());
		Person person = personservice.addPerson(p);
		ResponseEntity<Person> ret;
		if (person != null) {
			logMessage = String.format("Person mit Id: %d angelegt", person.getId());
			logger.info(logMessage);
			ret = ResponseEntity.ok(person);
		}else {
			logMessage = String.format("Person konnte nicht angelegt werden");
			logger.warn(logMessage);
			ret = new ResponseEntity<Person>(HttpStatus.BAD_REQUEST);
		}
		return ret;		
	}
	
	@PutMapping("/json/person")
	public ResponseEntity<Person> update(@Valid @RequestBody Person p){
		String logMessage = String.format("Person mit Id: %d soll geupdated werden", p.getId());
		logger.info(logMessage);
		personservice.addLocation(p.getLocation());
		Person person = personservice.update(p);
		ResponseEntity<Person> ret;
		if (person != null) {
			logMessage = String.format("Person mit Id: %d geupdated", person.getId());
			logger.info(logMessage);
			ret = ResponseEntity.ok(person);			
		}else {
			logMessage = String.format("Person konnte nicht geupdated werden");
			logger.warn(logMessage);			
			ret = new ResponseEntity<Person>(HttpStatus.BAD_REQUEST);
			System.out.println("Bad Request");
		}
		return ret;		
	}
	
	@DeleteMapping("/json/person/{id}")
	public boolean delete(@PathVariable("id") Long id){	
		String logMessage = String.format("Person mit Id: %d soll gelöscht werden", id);
		logger.info(logMessage);
		if (personservice.delete(id)) {
			logMessage = String.format("Person mit Id: %d wurde gelöscht", id);
			logger.info(logMessage);
			return true;
		} else {
			logMessage = String.format("Person mit Id: %d konnte nicht geupdated werden", id);
			logger.warn(logMessage);
			return false;
		}		
	}
	
	@PutMapping("/json/person/{id}")
	public Person update(@PathVariable("id") Long id, @Valid @RequestBody Person p){
		p.setId(id);
		personservice.addLocation(p.getLocation());
		return personservice.update(p);
	}
	
	@GetMapping("/json/persons/count")
	public long count() {
		return personservice.count();
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	        logger.warn("Bad Request: " + errorMessage);
	    });
	    return errors;
	}
	
	@GetMapping("/json/locations/")
	public List<String> getLocations() {
		logger.info("Standorte abgefragt");
		return personservice.getLocations();
		/*List<String> list = new ArrayList<String>();
		list.add("Bonn");
		list.add("St. Petersburg");
		list.add("Köln");
		list.add("Krefeld");
		list.add("Ulm");
		return list;*/
	}

}

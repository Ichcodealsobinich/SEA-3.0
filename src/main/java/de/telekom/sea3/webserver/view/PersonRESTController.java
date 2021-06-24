package de.telekom.sea3.webserver.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.telekom.sea3.webserver.model.Person;
import de.telekom.sea3.webserver.model.Persons;
import de.telekom.sea3.webserver.service.PersonService;

@RestController
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
	public ResponseEntity<Person> update(@RequestBody Person p){
		String logMessage = String.format("Person mit Id: %d soll geupdated werden", p.getId());
		logger.info(logMessage);
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
	public Person update(@PathVariable("id") Long id, @RequestBody Person p){
		p.setId(id);
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

}

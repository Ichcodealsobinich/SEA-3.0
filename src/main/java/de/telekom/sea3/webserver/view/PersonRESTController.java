package de.telekom.sea3.webserver.view;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.telekom.sea3.webserver.model.Count;
import de.telekom.sea3.webserver.model.Person;
import de.telekom.sea3.webserver.model.Persons;
import de.telekom.sea3.webserver.service.PersonService;

@RestController
public class PersonRESTController {

	private PersonService personservice;
	
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
		List<Person> lp = personservice.getAllPersons();
		return new Persons(lp);
	}
	
	@GetMapping("/json/persons/{id}")
	public Optional<Person> getperson(@PathVariable("id") Long id) {
		return personservice.getPerson(id);
	}
	
	@PostMapping("/json/person")
	public ResponseEntity<Person> add(@RequestBody Person p){
		Person person = personservice.addPerson(p);
		ResponseEntity<Person> ret;
		if (person != null) {
			ret = ResponseEntity.ok(person);
		}else {
			ret = new ResponseEntity<Person>(HttpStatus.BAD_REQUEST);
			System.out.println("Bad Request");
		}
		return ret;		
	}
	
	@DeleteMapping("/json/person/{id}")
	public boolean delete(@PathVariable("id") Long id){		
		return personservice.delete(id);
	}
	
	@PutMapping("/json/person/{id}")
	public Person update(@PathVariable("id") Long id, @RequestBody Person p){
		p.setId(id);
		return personservice.update(p);
	}
}

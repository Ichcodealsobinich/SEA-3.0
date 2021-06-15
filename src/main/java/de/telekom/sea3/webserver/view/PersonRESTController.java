package de.telekom.sea3.webserver.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@GetMapping("/json/count")
	public Count getSize() {		
		return new Count(personservice.getSize());
	}
	
	/**@see <a href="http://localhost:8000/json/participants/all">URL</a>
	 * 
	 * @return A list of all participants as a JSON-String
	 */
	@GetMapping("/json/persons/all")
	public Persons getPersons(@RequestHeader HttpHeaders head) {	
		System.out.println(head.getAccept().toString());
		return personservice.getAllPersons();
	}
	
	@GetMapping("/json/participant/{id}")
	public Person getperson(@PathVariable("id") int id) {
		return personservice.getPerson(id);
	}
	
	@PostMapping("/json/person")
	public Person add(@RequestBody Person p){		
		return personservice.addPerson(p);
	}
	
	@ResponseBody
	@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
	public String handleHttpMediaTypeNotAcceptableException() {
		System.out.println("Catched Exception with Media Type");
	    return "acceptable MIME type:" + MediaType.APPLICATION_JSON_VALUE;
	}
}
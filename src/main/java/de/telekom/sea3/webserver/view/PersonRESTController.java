package de.telekom.sea3.webserver.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import de.telekom.sea3.webserver.model.Person;
import de.telekom.sea3.webserver.model.Persons;
import de.telekom.sea3.webserver.service.PersonService;

@Controller
public class PersonRESTController {

	private PersonService personservice;
	
	@Autowired
	public PersonRESTController(PersonService ps) {
		this.personservice = ps;
	}
	
	@GetMapping("/participants")
	@ResponseBody
	public String getPersonsAsJSON() {		
		Persons persons = personservice.getAllPersons();		
		return persons.toJSON();
	}
	
	@PostMapping("/submitPerson")
	public @ResponseBody ResponseEntity<String> add(@RequestBody Person p){
		System.out.println(p.getFirstname().toString());
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}

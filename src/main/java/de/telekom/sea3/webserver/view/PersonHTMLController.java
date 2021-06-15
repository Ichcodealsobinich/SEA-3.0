package de.telekom.sea3.webserver.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import de.telekom.sea3.webserver.service.PersonService;

@Controller
public class PersonHTMLController {

	private PersonService personservice;
	
	@Autowired
	public PersonHTMLController(PersonService ps) {
		this.personservice = ps;
	}
	
	@GetMapping("/count")
	@ResponseBody
	public String getSize() {
		
		return String.valueOf(personservice.getSize());
	}	
	
	@GetMapping("/greeting/{name}")
	public @ResponseBody ResponseEntity<String> greeting(@PathVariable String name) {
		String html = String.format("<!DOCTYPE html>	"
				+ "<html lang='en'\\>"
					+ " <head>"
						+ "<title>SEA Kurs</title>"
						+ "</head>"
					+ "<body>"
						+"Hallo %s"
					+ "</body>"
				+"</html>",
				name);
		ResponseEntity<String> re = new ResponseEntity<String>(html, HttpStatus.OK);
		return re;
	}
}

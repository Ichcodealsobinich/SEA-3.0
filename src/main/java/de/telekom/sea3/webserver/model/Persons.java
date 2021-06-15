package de.telekom.sea3.webserver.model;

import java.util.ArrayList;
import java.util.List;

public class Persons {

	private List<Person> pList = new ArrayList<Person>();

	public List<Person> getPersons() {
		return pList;
	}

	public void setPersons(List<Person> pList) {
		this.pList = pList;
	}
	
	public void addPerson(Person p) {
		pList.add(p);
	}
	
	public Person getPerson(int id) {
		return pList.get(id);
	}
	
	public int size() {
		return pList.size();
	}	
	
}

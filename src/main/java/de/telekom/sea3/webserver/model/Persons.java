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
		p.setId(pList.size());
		pList.add(p);
	}
	
	public Person getPerson(int id) {
		return pList.get(id);
	}
	
	public Person setPerson(int id, Person p) {
		try {
			pList.set(id, p);
			return p;
		}catch (Exception e) {
			return null;
		}
	}
	
	public int size() {
		return pList.size();
	}	
	
	public boolean delete(int id) {
		try {
			pList.remove(id);
			return true;
		} catch (Exception e){
			return false;
		}
	}
	
}

package de.telekom.sea3.webserver.model;

import java.util.ArrayList;
import java.util.List;

public class Persons {

	private List<Person> pList = new ArrayList<Person>();

	public List<Person> get() {
		return pList;
	}

	public void set(List<Person> pList) {
		this.pList = pList;
	}
	
	public String toJSON() {
		return "";
	}
}

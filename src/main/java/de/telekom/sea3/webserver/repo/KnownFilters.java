package de.telekom.sea3.webserver.repo;

import java.util.ArrayList;
import java.util.List;

public abstract class KnownFilters {
	
	public static List<String> getFilters() {	
		List<String> knownFilters = new ArrayList<String>();
		knownFilters.add("location");
		knownFilters.add("lastname");
		knownFilters.add("firstname");
		return knownFilters;
	}
}

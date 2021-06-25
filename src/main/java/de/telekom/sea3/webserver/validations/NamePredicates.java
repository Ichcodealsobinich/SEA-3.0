package de.telekom.sea3.webserver.validations;

public class NamePredicates {
	public static boolean isAllowedInNames(int cCode) {
		if (Character.isLetter(cCode)) {return true;}
		if (Character.isWhitespace(cCode)) {return true;}
		return false;
	}
}

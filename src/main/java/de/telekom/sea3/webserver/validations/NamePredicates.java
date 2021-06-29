package de.telekom.sea3.webserver.validations;

public class NamePredicates {
	public static boolean isAllowedInNames(int cCode) {
		if (Character.isLetter(cCode)) {return true;}		//latin letters
		if (Character.isWhitespace(cCode)) {return true;}	//Whitespaces are allowed
		if (Character.isIdeographic(cCode)) {return true;}	//Chinese, Japanese, Korean, Vietnamese ideograph
		if (Character.isSupplementaryCodePoint(cCode)) {return true;}
		if (Character.isBmpCodePoint(cCode)) {return true;}
		return false;
	}
}

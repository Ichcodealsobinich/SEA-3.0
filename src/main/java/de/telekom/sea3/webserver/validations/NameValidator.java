package de.telekom.sea3.webserver.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<Name, String>{
	@Override
    public void initialize(Name name) {
    }

    @Override
    public boolean isValid(String name,
      ConstraintValidatorContext cxt) {
    	if (name == null) {return false;}
		if (name.length()<2 || name.length()>40) {return false;}
		return name.chars().allMatch(NamePredicates::isAllowedInNames);	
    }
}

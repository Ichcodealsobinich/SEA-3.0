package de.telekom.sea3.webserver.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailAddressValidator implements 
  ConstraintValidator<EmailAddress, String> {

    @Override
    public void initialize(EmailAddress email) {
    }

    @Override
    public boolean isValid(String email,
      ConstraintValidatorContext cxt) {
		String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
		if (email.strip().matches(regex)) {
			if (email.length()<101){				
				return true;
			} else {
				return false;
			} 
		} else {
			return false;
		}
    }

}

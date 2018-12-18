package org.health.supplychain.validator;

import org.health.supplychain.customexception.ValidatorException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlphaNumericValidator {
	
	private Pattern pattern;
//    private static final String namePattern = "^[a-zA-Z0-9]*$";
	private static final String namePattern = "^[a-zA-Z0-9!@#$&()\\-`.+,/\"]*$"; //TODO: pattern should consider unicode characters.
	private static final int minLength = 2;
    
 	public AlphaNumericValidator(){
        pattern = Pattern.compile(namePattern);
    }

    public void validate(String fieldName, Object value, boolean isMandatory) throws ValidatorException {
		 if (value.toString().trim().equals("")){
		 	if(isMandatory)
			 	throw new ValidatorException(fieldName + " is mandatory.");
		 }else{
//			 if(!pattern.matcher(value.toString().trim()).matches()) {
//				throw new ValidatorException("Please enter valid value for " + fieldName);
//			 }

			 if(value.toString().length() < minLength && isMandatory){
				 throw new ValidatorException("Please enter valid value for " + fieldName);
			 }
		}


    }
}

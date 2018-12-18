/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.health.supplychain.validator;

/**
 *
 * @author amequanint
 */
import org.health.supplychain.customexception.ValidatorException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PhoneValidator {

    private Pattern pattern;
    private Matcher matcher;

    private static final String phonePattern = "(^\\+[0-9]{2}|^\\+[0-9]{2}\\(0\\)|^\\(\\+[0-9]{2}\\)\\(0\\)|^00[0-9]{2}|^0)([0-9]{9}$|[0-9\\-\\s]{10}$)";


    public PhoneValidator(){
        pattern = Pattern.compile(phonePattern);
    }

    public void validate(String fieldName, Object value, boolean isMandatory) throws ValidatorException {

    	if(value.toString().equals("")){
    	    if(isMandatory)
                throw new ValidatorException(fieldName + " is mandatory.");
    	}else{
	        matcher = pattern.matcher(value.toString());
	         if (!matcher.matches() ) {
                 throw new ValidatorException("Please enter valid value for " + fieldName);
	        }
    	}
    }

}

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

public class NameValidator {

    private Pattern pattern;
    private Matcher matcher;
    private static final String namePattern = "^[A-Za-z]{1}[a-zA-Z /.-]{1,25}$"; //TODO: pattern should consider unicode characters.
    private static final int minLength = 2;

    public NameValidator(){
        pattern = Pattern.compile(namePattern);
    }

    public void validate(String fieldName, Object value, boolean isMandatory) throws ValidatorException {

    	if (value.toString().equals("")){
    	    if(isMandatory)
                throw new ValidatorException(fieldName + " is mandatory.");
        }else{
//	         if(!(pattern.matcher(value.toString()).matches()) && isMandatory){
//                 throw new ValidatorException("Please enter valid value for " + fieldName);
//	         }

            if(value.toString().length() < minLength && isMandatory){
                throw new ValidatorException("Please enter valid value for " + fieldName);
            }
        }

    }

}

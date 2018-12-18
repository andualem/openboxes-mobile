package org.health.supplychain.validator;

import org.health.supplychain.customexception.ValidatorException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Aworkneh on 7/23/2018.
 */

public class PositiveDecimalNumberValidator {

    private Pattern pattern;
    private Matcher matcher;
    private static final String numberPatern = "\\d+(\\.\\d{0,2})?";

    public PositiveDecimalNumberValidator(){
        pattern = Pattern.compile(numberPatern);
    }

    public void validate(String fieldName, Object value, boolean isMandatory) throws ValidatorException {

        if(value == null || value.toString().equals("")){
            if(isMandatory)
                throw new ValidatorException(fieldName + " is mandatory.");
        }else{
            if(!(pattern.matcher(value.toString()).matches())){
                throw new ValidatorException("Please enter valid value for " + fieldName);
            }
        }

    }


    public void validate(String fieldName, Object value, double maximum, boolean isMandatory) throws ValidatorException {

        if(value == null || value.toString().equals("")){
            if(isMandatory)
                throw new ValidatorException(fieldName + " is mandatory.");
        }else{
            if(!(pattern.matcher(value.toString()).matches())){
                throw new ValidatorException("Please enter valid value for " + fieldName);
            } else if (Double.valueOf(value.toString()) > maximum){
                throw new ValidatorException(fieldName + " should be less than " + maximum);
            }
        }

    }
}

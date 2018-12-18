package org.health.supplychain.webservice.entities;

import java.util.List;

/**
 * Created by Aworkneh on 8/27/2018.
 */

public class ProductErrorResponse {

    private int errorCode;
    private String errorMessage;
    private Data data;

    public int getErrorCode() { return this.errorCode; }

    public void setErrorCode(int errorCode) { this.errorCode = errorCode; }

    public String getErrorMessage() { return this.errorMessage; }

    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public Data getData() { return this.data; }

    public void setData(Data data) { this.data = data; }



    public class Error {
        private String String;
        private String field;
        private String rejectedValue;
        private String message;

        public String getString() { return this.String; }

        public void setString(String String) { this.String = String; }

        public String getField() { return this.field; }

        public void setField(String field) { this.field = field; }

        public String getRejectedValue() { return this.rejectedValue; }

        public void setRejectedValue(String rejectedValue) { this.rejectedValue = rejectedValue; }

        public String getMessage() { return this.message; }

        public void setMessage(String message) { this.message = message; }
    }

    public class Data
    {
        private List<Error> errors;

        public List<Error> getErrors() { return this.errors; }

        public void setErrors(List<Error> errors) { this.errors = errors; }
    }

}

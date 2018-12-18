package org.health.supplychain.webservice.entities;

/**
 * Created by Aworkneh on 8/27/2018.
 */

public class ErrorResponse {

    private int errorCode;
    private String errorMessage;

    public int getErrorCode() { return this.errorCode; }

    public void setErrorCode(int errorCode) { this.errorCode = errorCode; }

    public String getErrorMessage() { return this.errorMessage; }

    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}

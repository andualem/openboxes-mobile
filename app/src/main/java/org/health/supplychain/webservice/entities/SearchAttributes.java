package org.health.supplychain.webservice.entities;

public class SearchAttributes {
    private String property;

    private String operator;

    private String value;

    public void setProperty(String property){
        this.property = property;
    }
    public String getProperty(){
        return this.property;
    }
    public void setOperator(String operator){
        this.operator = operator;
    }
    public String getOperator(){
        return this.operator;
    }
    public void setValue(String value){
        this.value = value;
    }
    public String getValue(){
        return this.value;
    }
}

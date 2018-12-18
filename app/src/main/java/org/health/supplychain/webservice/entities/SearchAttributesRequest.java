package org.health.supplychain.webservice.entities;

import java.util.List;

public class SearchAttributesRequest {

    private List<SearchAttributes> searchAttributes;

    public void setSearchAttributes(List<SearchAttributes> searchAttributes){
        this.searchAttributes = searchAttributes;
    }
    public List<SearchAttributes> getSearchAttributes(){
        return this.searchAttributes;
    }
}

package org.health.supplychain.webservice.entities;

public class SearchRequest {
    private String id;
    private String name;
    private Integer offset;
    private Integer max;

    public SearchRequest(String id, String name, Integer offset, Integer max) {
        this.id = id;
        this.name = name;
        this.offset = offset;
        this.max = max;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }
}

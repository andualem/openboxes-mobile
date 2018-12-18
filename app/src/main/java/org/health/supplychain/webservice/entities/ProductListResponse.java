package org.health.supplychain.webservice.entities;

import java.util.List;

public class ProductListResponse {

    private List<Product> data;

    public List<Product> getData() { return this.data; }

    public void setData(List<Product> data) { this.data = data; }
}

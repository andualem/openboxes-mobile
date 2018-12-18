package org.health.supplychain.webservice.entities;

import java.util.Date;

public class InventoryItem {

    private String id;
    private Product product;
    private String lotNumber;
    private String expirationDate;


    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    public Product getProduct() { return this.product; }

    public void setProduct(Product product) { this.product = product; }

    public String getLotNumber() { return this.lotNumber; }

    public void setLotNumber(String lotNumber) { this.lotNumber = lotNumber; }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}

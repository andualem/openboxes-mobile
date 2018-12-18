package org.health.supplychain.webservice.entities;

import java.util.Date;

public class ProductAssociation {

    private String id;
    private String type;
    private Product product;
    private int conversionFactor;
    private String comments;
    private Date minExpirationDate;
    private int availableQuantity;

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    public String getType() { return this.type; }

    public void setType(String type) { this.type = type; }

    public Product getProduct() { return this.product; }

    public void setProduct(Product product) { this.product = product; }

    public int getConversionFactor() { return this.conversionFactor; }

    public void setConversionFactor(int conversionFactor) { this.conversionFactor = conversionFactor; }

    public String getComments() { return this.comments; }

    public void setComments(String comments) { this.comments = comments; }

    public Date getMinExpirationDate() { return this.minExpirationDate; }

    public void setMinExpirationDate(Date minExpirationDate) { this.minExpirationDate = minExpirationDate; }

    public int getAvailableQuantity() { return this.availableQuantity; }

    public void setAvailableQuantity(int availableQuantity) { this.availableQuantity = availableQuantity; }
}

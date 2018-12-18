package org.health.supplychain.webservice.entities;

/**
 * Created by Aworkneh on 8/27/2018.
 */

public class Data {
    private String id;
    private String productCode;
    private String name;
    private String description;
    private Category category;

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    public String getProductCode() { return this.productCode; }

    public void setProductCode(String productCode) { this.productCode = productCode; }

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return this.description; }

    public void setDescription(String description) { this.description = description; }

    public Category getCategory() { return this.category; }

    public void setCategory(Category category) { this.category = category; }
}

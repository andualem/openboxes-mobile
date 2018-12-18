package org.health.supplychain.webservice.entities;

import com.google.gson.annotations.SerializedName;

public class ProductRequest {

    private String productCode;
    private String name;
    private String description;
    @SerializedName("category.id")
    private String categoryId;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}

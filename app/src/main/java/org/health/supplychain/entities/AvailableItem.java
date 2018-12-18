package org.health.supplychain.entities;

import io.realm.RealmObject;

public class AvailableItem extends RealmObject {

    private String inventoryItemId;
    private String productId;
    private String productCode;
    private String productName;
    private String lotNumber;
    private String expirationDate;
    private String binLocationId;
    private String binLocationName;
    private double quantityAvailable;

    public String getInventoryItemId() {
        return inventoryItemId;
    }

    public void setInventoryItemId(String inventoryItemId) {
        this.inventoryItemId = inventoryItemId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getBinLocationId() {
        return binLocationId;
    }

    public void setBinLocationId(String binLocationId) {
        this.binLocationId = binLocationId;
    }

    public String getBinLocationName() {
        return binLocationName;
    }

    public void setBinLocationName(String binLocationName) {
        this.binLocationName = binLocationName;
    }

    public double getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(double quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }
}

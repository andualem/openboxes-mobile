package org.health.supplychain.webservice.entities;

import com.google.gson.annotations.SerializedName;

public class AvailableItem {
    private InventoryItem inventoryItem;
    private int quantity;

    //newly added - may be for the old API

    @SerializedName("inventoryItem.id")
    private String inventoryItemId;

    @SerializedName("product.name")
    private String productName;
    private String productCode;
    private String lotNumber;
    private String expirationDate;
    @SerializedName("binLocation.id")
    private String binLocationId;
    @SerializedName("binLocation.name")
    private String binLocationName;
    private double quantityAvailable;


    public InventoryItem getInventoryItem() { return this.inventoryItem; }

    public void setInventoryItem(InventoryItem inventoryItem) { this.inventoryItem = inventoryItem; }

    public int getQuantity() { return this.quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }



    //newly added


    public String getInventoryItemId() {
        return inventoryItemId;
    }

    public void setInventoryItemId(String inventoryItemId) {
        this.inventoryItemId = inventoryItemId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
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

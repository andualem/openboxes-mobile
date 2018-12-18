package org.health.supplychain.webservice.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PicklistItems {
    private String id;
    private InventoryItem inventoryItem;
    @SerializedName("binLocation.id")
    private String binLocationId;
    private String quantityPicked;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InventoryItem getInventoryItem() {
        return inventoryItem;
    }

    public void setInventoryItem(InventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
    }

    public String getBinLocationId() {
        return binLocationId;
    }

    public void setBinLocationId(String binLocationId) {
        this.binLocationId = binLocationId;
    }

    public String getQuantityPicked() {
        return quantityPicked;
    }

    public void setQuantityPicked(String quantityPicked) {
        this.quantityPicked = quantityPicked;
    }
}

package org.health.supplychain.webservice.entities;

import com.google.gson.annotations.SerializedName;

public class ShipmentItemRequest {

    // {"inventoryItem.id":"ff80818165eb053e0165eb07d1530002","shipment.id":"ff80818167654c700167654d96d40001",
    // "product.id":"ff80818155df9de40155df9e31000001","quantity":1}

    @SerializedName("inventoryItem.id")
    private String inventoryItemId;
    @SerializedName("shipment.id")
    private String shipmentId;
    @SerializedName("product.id")
    private String productId;
    private double quantity;

    public String getInventoryItemId() {
        return inventoryItemId;
    }

    public void setInventoryItemId(String inventoryItemId) {
        this.inventoryItemId = inventoryItemId;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}

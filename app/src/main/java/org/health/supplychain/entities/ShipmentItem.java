package org.health.supplychain.entities;

import io.realm.RealmObject;

public class ShipmentItem extends RealmObject {
    private String id;

    private AvailableItem inventoryItem;

    private int quantity;

    private String recipient;

    private String shipmentDetailId;

    private String shipmentDetailName;

    private String container;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AvailableItem getInventoryItem() {
        return inventoryItem;
    }

    public void setInventoryItem(AvailableItem inventoryItem) {
        this.inventoryItem = inventoryItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getShipmentDetailId() {
        return shipmentDetailId;
    }

    public void setShipmentDetailId(String shipmentDetailId) {
        this.shipmentDetailId = shipmentDetailId;
    }

    public String getShipmentDetailName() {
        return shipmentDetailName;
    }

    public void setShipmentDetailName(String shipmentDetailName) {
        this.shipmentDetailName = shipmentDetailName;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }
}

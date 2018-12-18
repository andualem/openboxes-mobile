package org.health.supplychain.webservice.entities;

public class ShipmentItems {
    private String id;

    private InventoryItem inventoryItem;

    private int quantity;

    private String recipient;

    private ShipmentDetail shipment;

    private String container;

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

    public ShipmentDetail getShipment() {
        return shipment;
    }

    public void setShipment(ShipmentDetail shipment) {
        this.shipment = shipment;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }
}

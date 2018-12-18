package org.health.supplychain.webservice.entities;

import java.util.List;

public class Shipment {

    private String id;

    private String name;

    private String status;

    private OriginDestination origin;

    private OriginDestination destination;

    private String expectedShippingDate;

    private String actualShippingDate;

    private String expectedDeliveryDate;

    private String actualDeliveryDate;

    private List<ShipmentItems> shipmentItems;

    private List<Containers> containers;

    private ShipmentType shipmentType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OriginDestination getOrigin() {
        return origin;
    }

    public void setOrigin(OriginDestination origin) {
        this.origin = origin;
    }

    public OriginDestination getDestination() {
        return destination;
    }

    public void setDestination(OriginDestination destination) {
        this.destination = destination;
    }

    public String getExpectedShippingDate() {
        return expectedShippingDate;
    }

    public void setExpectedShippingDate(String expectedShippingDate) {
        this.expectedShippingDate = expectedShippingDate;
    }

    public String getActualShippingDate() {
        return actualShippingDate;
    }

    public void setActualShippingDate(String actualShippingDate) {
        this.actualShippingDate = actualShippingDate;
    }

    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(String expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public String getActualDeliveryDate() {
        return actualDeliveryDate;
    }

    public void setActualDeliveryDate(String actualDeliveryDate) {
        this.actualDeliveryDate = actualDeliveryDate;
    }

    public List<ShipmentItems> getShipmentItems() {
        return shipmentItems;
    }

    public void setShipmentItems(List<ShipmentItems> shipmentItems) {
        this.shipmentItems = shipmentItems;
    }

    public List<Containers> getContainers() {
        return containers;
    }

    public void setContainers(List<Containers> containers) {
        this.containers = containers;
    }

    public ShipmentType getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(ShipmentType shipmentType) {
        this.shipmentType = shipmentType;
    }
}

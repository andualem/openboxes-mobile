package org.health.supplychain.entities;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class Shipment extends RealmObject {

    private String id;

    private String name;

    private String status;

    private Facility origin;

    private Facility destination;

    private String expectedShippingDate;

    private String actualShippingDate;

    private String expectedDeliveryDate;

    private String actualDeliveryDate;

    private RealmList<ShipmentItem> shipmentItemList;

    @Ignore
    private List<ShipmentItem> unmanagedShipmentItemList;

    private RealmList<Container> containerList;

    @Ignore
    private List<Container> unmananagedContainerList;

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

    public Facility getOrigin() {
        return origin;
    }

    public void setOrigin(Facility origin) {
        this.origin = origin;
    }

    public Facility getDestination() {
        return destination;
    }

    public void setDestination(Facility destination) {
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

    public RealmList<ShipmentItem> getShipmentItemList() {
        return shipmentItemList;
    }

    public void setShipmentItemList(RealmList<ShipmentItem> shipmentItemList) {
        this.shipmentItemList = shipmentItemList;
    }

    public RealmList<Container> getContainerList() {
        return containerList;
    }

    public void setContainerList(RealmList<Container> containerList) {
        this.containerList = containerList;
    }

    public List<ShipmentItem> getUnmanagedShipmentItemList() {
        return unmanagedShipmentItemList;
    }

    public void setUnmanagedShipmentItemList(List<ShipmentItem> unmanagedShipmentItemList) {
        this.unmanagedShipmentItemList = unmanagedShipmentItemList;
    }

    public List<Container> getUnmananagedContainerList() {
        return unmananagedContainerList;
    }

    public void setUnmananagedContainerList(List<Container> unmananagedContainerList) {
        this.unmananagedContainerList = unmananagedContainerList;
    }
}

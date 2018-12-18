package org.health.supplychain.webservice.entities;

import com.google.gson.annotations.SerializedName;

public class ShipmentInitialRequest {

    // {"destination.id":"2","expectedShippingDate":"12/27/2018 00:00 +03:00",
    // "name":"Boston HeadquartersMiami Warehouse20181201","origin.id":"1","shipmentType.id":"1"}

    @SerializedName("destination.id")
    private String destinationId;
    private String expectedShippingDate;
    private String name;
    @SerializedName("origin.id")
    private String originId;
    @SerializedName("shipmentType.id")
    private String shipmentTypeId;

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public String getExpectedShippingDate() {
        return expectedShippingDate;
    }

    public void setExpectedShippingDate(String expectedShippingDate) {
        this.expectedShippingDate = expectedShippingDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public String getShipmentTypeId() {
        return shipmentTypeId;
    }

    public void setShipmentTypeId(String shipmentTypeId) {
        this.shipmentTypeId = shipmentTypeId;
    }
}

package org.health.supplychain.webservice.entities;

import java.util.List;

public class ShipmentRequest {

    private List<Shipment> data;

    public List<Shipment> getData() {
        return data;
    }

    public void setData(List<Shipment> data) {
        this.data = data;
    }
}

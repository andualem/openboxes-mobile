package org.health.supplychain.webservice.entities;

import java.util.List;

public class ShipmentTypeResponse {

    private List<ShipmentType> data;

    public List<ShipmentType> getData() {
        return data;
    }

    public void setData(List<ShipmentType> data) {
        this.data = data;
    }
}

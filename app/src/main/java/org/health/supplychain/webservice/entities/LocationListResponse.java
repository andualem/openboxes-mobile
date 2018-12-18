package org.health.supplychain.webservice.entities;

import java.util.List;

public class LocationListResponse {

    private List<LocationData> data;

    public List<LocationData> getData() {
        return data;
    }

    public void setData(List<LocationData> data) {
        this.data = data;
    }
}

package org.health.supplychain.webservice.entities;

import java.util.List;

public class AvailableItemListResponse {

    private List<AvailableItem> data;

    public List<AvailableItem> getData() {
        return data;
    }

    public void setData(List<AvailableItem> data) {
        this.data = data;
    }
}

package org.health.supplychain.webservice.entities;

import java.util.List;

public class StockMovementListResponse {

    private List<StockMovement> data;

    public List<StockMovement> getData() { return this.data; }

    public void setData(List<StockMovement> data) { this.data = data; }
}

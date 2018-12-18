package org.health.supplychain.webservice.entities;

import java.util.List;

public class RequisitionRequest {

    private List<Requisition> data;

    public List<Requisition> getData() {
        return data;
    }

    public void setData(List<Requisition> data) {
        this.data = data;
    }
}

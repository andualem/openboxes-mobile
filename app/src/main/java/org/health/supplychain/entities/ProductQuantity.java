package org.health.supplychain.entities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProductQuantity extends RealmObject{

    @PrimaryKey
    private long id;
    private Product product;
    private AvailableItem availableItem;
    private double issuedQuantity;
    private double receivedQuantity;
    private double requestedQuantity;
    private String remark;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public AvailableItem getAvailableItem() {
        return availableItem;
    }

    public void setAvailableItem(AvailableItem availableItem) {
        this.availableItem = availableItem;
    }

    public double getIssuedQuantity() {
        return issuedQuantity;
    }

    public void setIssuedQuantity(double issuedQuantity) {
        this.issuedQuantity = issuedQuantity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getReceivedQuantity() {
        return receivedQuantity;
    }

    public void setReceivedQuantity(double receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }

    public double getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(double requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }
}

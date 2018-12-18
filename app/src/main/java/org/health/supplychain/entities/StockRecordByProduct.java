package org.health.supplychain.entities;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class StockRecordByProduct extends RealmObject {

    @PrimaryKey
    private String uuid;
    private Facility facility;
    private Facility fromToFacility;
    private double quantity;
    private int transactionType; //Received or Issued
    private double loss;
    private double adjustment;
    private double quantityOnHand;
    private long createdDate;
    private String createdBy;
    private long updatedDate;
    private String updatedBy;
    private int dmlFlag;

    private Product product;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Facility getFromToFacility() {
        return fromToFacility;
    }

    public void setFromToFacility(Facility fromToFacility) {
        this.fromToFacility = fromToFacility;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }

    public double getLoss() {
        return loss;
    }

    public void setLoss(double loss) {
        this.loss = loss;
    }

    public double getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(double adjustment) {
        this.adjustment = adjustment;
    }

    public double getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(double quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public long getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(long updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public int getDmlFlag() {
        return dmlFlag;
    }

    public void setDmlFlag(int dmlFlag) {
        this.dmlFlag = dmlFlag;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

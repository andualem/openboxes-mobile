package org.health.supplychain.entities;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Transaction extends RealmObject {

    @PrimaryKey
    private String uuid;
    private Facility sourceFacility;
    private Facility destinationFacility;
    private int transactionType; //issue, shipment, receive, request, issue-for-request, shipment-for-request, receive-for-request
    private long createdDate;
    private String createdBy;
    private long updatedDate;
    private String updatedBy;
    private int dmlFlag;

    private String createdByUsername;

    public Transaction(){
        this.uuid = UUID.randomUUID().toString();
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Facility getSourceFacility() {
        return sourceFacility;
    }

    public void setSourceFacility(Facility sourceFacility) {
        this.sourceFacility = sourceFacility;
    }

    public Facility getDestinationFacility() {
        return destinationFacility;
    }

    public void setDestinationFacility(Facility destinationFacility) {
        this.destinationFacility = destinationFacility;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
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

    public String getCreatedByUsername() {
        return createdByUsername;
    }

    public void setCreatedByUsername(String createdByUsername) {
        this.createdByUsername = createdByUsername;
    }
}

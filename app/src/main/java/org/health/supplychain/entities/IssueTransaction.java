package org.health.supplychain.entities;

import org.health.supplychain.constants.Constants;
import org.health.supplychain.utility.Utils;

import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class IssueTransaction extends RealmObject{

    @PrimaryKey
    private String uuid;
    private Transaction transaction;
    private RealmList<ProductQuantity> productQuantityRealmList;
    private long createdDate;
    private String createdBy;
    private long updatedDate;
    private String updatedBy;
    private int dmlFlag;
    private int syncStatus;
    private long syncDate;

    //new field
    private String expectedShippingDate;
    private String shipmentTypeId;

    public IssueTransaction(){
        this.uuid = UUID.randomUUID().toString();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public RealmList<ProductQuantity> getProductQuantityRealmList() {
        return productQuantityRealmList;
    }

    public void setProductQuantityRealmList(RealmList<ProductQuantity> productQuantityRealmList) {
        this.productQuantityRealmList = productQuantityRealmList;
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

    public int getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(int syncStatus) {
        this.syncStatus = syncStatus;
    }

    public long getSyncDate() {
        return syncDate;
    }

    public void setSyncDate(long syncDate) {
        this.syncDate = syncDate;
    }

    public String getExpectedShippingDate() {
        return expectedShippingDate;
    }

    public void setExpectedShippingDate(String expectedShippingDate) {
        this.expectedShippingDate = expectedShippingDate;
    }

    public String getShipmentTypeId() {
        return shipmentTypeId;
    }

    public void setShipmentTypeId(String shipmentTypeId) {
        this.shipmentTypeId = shipmentTypeId;
    }

    public String getName(){
        StringBuilder issueTranName = new StringBuilder();
        //TODO: check for NullPointer exceptions
        issueTranName.append(this.transaction.getSourceFacility().getName())
                .append(this.transaction.getDestinationFacility().getName())
                .append(Utils.convertDateToString(this.createdDate, Constants.DATESTAMP_FORMAT));

        return issueTranName.toString();
    }



}

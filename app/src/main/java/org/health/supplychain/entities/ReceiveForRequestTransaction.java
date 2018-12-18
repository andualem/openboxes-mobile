package org.health.supplychain.entities;

import java.security.PrivateKey;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ReceiveForRequestTransaction extends RealmObject {

    @PrimaryKey
    private String uuid;
    private ReceiveTransaction receiveTransaction;
    private RequestTransaction requestTransaction;
    private long createdDate;
    private String createdBy;
    private long updatedDate;
    private String updatedBy;
    private int dmlFlag;


    public ReceiveForRequestTransaction(){
        this.uuid = UUID.randomUUID().toString();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ReceiveTransaction getReceiveTransaction() {
        return receiveTransaction;
    }

    public void setReceiveTransaction(ReceiveTransaction receiveTransaction) {
        this.receiveTransaction = receiveTransaction;
    }

    public RequestTransaction getRequestTransaction() {
        return requestTransaction;
    }

    public void setRequestTransaction(RequestTransaction requestTransaction) {
        this.requestTransaction = requestTransaction;
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
}

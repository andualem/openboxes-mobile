package org.health.supplychain.entities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Aworkneh on 6/19/2018.
 */

public class SyncMetadata extends RealmObject {

    @PrimaryKey
    private long id;
    private String entityName;
    private int syncOperationType; // download or upload
    private int syncGroupCode;
    private int hasUnuploadedRecord;
    private int noDataToUpload;
    private long createdDate;
    private long updatedDate;
    private int operationStatus;
    private long lastOperationDate;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public int getSyncOperationType() {
        return syncOperationType;
    }

    public void setSyncOperationType(int syncOperationType) {
        this.syncOperationType = syncOperationType;
    }

    public int getSyncGroupCode() {
        return syncGroupCode;
    }

    public void setSyncGroupCode(int syncGroupCode) {
        this.syncGroupCode = syncGroupCode;
    }

    public int getHasUnuploadedRecord() {
        return hasUnuploadedRecord;
    }

    public void setHasUnuploadedRecord(int hasUnuploadedRecord) {
        this.hasUnuploadedRecord = hasUnuploadedRecord;
    }

    public int getNoDataToUpload() {
        return noDataToUpload;
    }

    public void setNoDataToUpload(int noDataToUpload) {
        this.noDataToUpload = noDataToUpload;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public long getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(long updatedDate) {
        this.updatedDate = updatedDate;
    }

    public int getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(int operationStatus) {
        this.operationStatus = operationStatus;
    }

    public long getLastOperationDate() {
        return lastOperationDate;
    }

    public void setLastOperationDate(long lastOperationDate) {
        this.lastOperationDate = lastOperationDate;
    }
}

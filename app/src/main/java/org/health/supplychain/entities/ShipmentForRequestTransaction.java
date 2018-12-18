package org.health.supplychain.entities;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ShipmentForRequestTransaction extends RealmObject {

    @PrimaryKey
    private String uuid;
    private IssueForRequestTransaction issueForRequestTransaction;
    private RequestTransaction requestTransaction;
    private long shippedDate;
    private String shippedBy;

    public ShipmentForRequestTransaction(){
        this.uuid = UUID.randomUUID().toString();
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public IssueForRequestTransaction getIssueForRequestTransaction() {
        return issueForRequestTransaction;
    }

    public void setIssueForRequestTransaction(IssueForRequestTransaction issueForRequestTransaction) {
        this.issueForRequestTransaction = issueForRequestTransaction;
    }

    public RequestTransaction getRequestTransaction() {
        return requestTransaction;
    }

    public void setRequestTransaction(RequestTransaction requestTransaction) {
        this.requestTransaction = requestTransaction;
    }

    public long getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(long shippedDate) {
        this.shippedDate = shippedDate;
    }

    public String getShippedBy() {
        return shippedBy;
    }

    public void setShippedBy(String shippedBy) {
        this.shippedBy = shippedBy;
    }
}

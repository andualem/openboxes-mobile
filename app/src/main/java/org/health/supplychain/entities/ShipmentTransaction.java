package org.health.supplychain.entities;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ShipmentTransaction extends RealmObject {

    @PrimaryKey
    private String uuid;
    private IssueTransaction issueTransaction;
    private long shippedDate;
    private String shippedBy;

    public ShipmentTransaction(){
        this.uuid = UUID.randomUUID().toString();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public IssueTransaction getIssueTransaction() {
        return issueTransaction;
    }

    public void setIssueTransaction(IssueTransaction issueTransaction) {
        this.issueTransaction = issueTransaction;
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

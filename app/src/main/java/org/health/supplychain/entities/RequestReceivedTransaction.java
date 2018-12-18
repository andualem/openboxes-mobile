package org.health.supplychain.entities;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RequestReceivedTransaction extends RealmObject {

    @PrimaryKey
    private String uuid;
    private RequestTransaction requestTransaction;
    private long receivedDate;

    public RequestReceivedTransaction(){
        this.uuid = UUID.randomUUID().toString();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public RequestTransaction getRequestTransaction() {
        return requestTransaction;
    }

    public void setRequestTransaction(RequestTransaction requestTransaction) {
        this.requestTransaction = requestTransaction;
    }

    public long getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(long receivedDate) {
        this.receivedDate = receivedDate;
    }
}

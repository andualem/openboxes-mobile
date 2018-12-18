package org.health.supplychain.entities;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Container extends RealmObject {
    private String id;

    private String name;

    private String type;

    private RealmList<ShipmentItem> shipmentItemList;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RealmList<ShipmentItem> getShipmentItemList() {
        return shipmentItemList;
    }

    public void setShipmentItemList(RealmList<ShipmentItem> shipmentItemList) {
        this.shipmentItemList = shipmentItemList;
    }
}

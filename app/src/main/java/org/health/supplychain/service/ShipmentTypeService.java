package org.health.supplychain.service;

import org.health.supplychain.entities.ShipmentType;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ShipmentTypeService implements IShipmentTypeService{
    @Override
    public boolean saveShipmentType(ShipmentType shipmentType) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        final ShipmentType savedShipmentType = realm.copyToRealm(shipmentType);
        realm.commitTransaction();
        return savedShipmentType.isManaged();
    }

    @Override
    public ShipmentType getShipmentTypeById(String id) {

        Realm realm = Realm.getDefaultInstance();
        ShipmentType shipmentType = realm.where(ShipmentType.class).equalTo("id", id).findFirst();
        return shipmentType;
    }

    @Override
    public List<ShipmentType> getAllShipmentType() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ShipmentType> shipmentTypeRealmResults = realm.where(ShipmentType.class).findAll();

        if(shipmentTypeRealmResults != null)
            return shipmentTypeRealmResults.subList(0, shipmentTypeRealmResults.size());
        return null;
    }

    @Override
    public boolean updateShipmentType(ShipmentType existingShipmentType, ShipmentType incomingShipmentType) {
        return false;
    }
}

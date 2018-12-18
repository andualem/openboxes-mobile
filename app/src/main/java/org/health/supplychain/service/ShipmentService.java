package org.health.supplychain.service;

import org.health.supplychain.entities.Container;
import org.health.supplychain.entities.Facility;
import org.health.supplychain.entities.Shipment;
import org.health.supplychain.entities.ShipmentItem;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ShipmentService implements IShipmentService{


    @Override
    public boolean saveShipment(Shipment shipment) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        final Shipment savedShipment = realm.copyToRealm(shipment);

        final List<ShipmentItem> managedShipmentItemList
                = realm.copyToRealm(shipment.getUnmanagedShipmentItemList());

        final List<Container> managedContainerList = realm.copyToRealm(shipment.getUnmananagedContainerList());

        savedShipment.getShipmentItemList().addAll(managedShipmentItemList);
        savedShipment.getContainerList().addAll(managedContainerList);

        realm.commitTransaction();
        return savedShipment.isManaged();
    }

    @Override
    public boolean updateShipment(Shipment existingShipment, Shipment incomingShipment) {
        return false;
    }

    @Override
    public Shipment getShipmentById(String id) {
        Realm realm = Realm.getDefaultInstance();
        Shipment shipment = realm.where(Shipment.class).equalTo("id", id).findFirst();
        return shipment;
    }

    @Override
    public List<Shipment> getShipmentByDestinationId(String destinationId, String status) {
        List<Shipment> filteredShipmentList = null, shipmentList = null;
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Shipment> shipments = realm.where(Shipment.class).findAll();

        if(shipments != null){
            shipmentList = shipments.subList(0, shipments.size());
            filteredShipmentList = new ArrayList<>();
            for(Shipment shipment: shipmentList){
                Facility destinationFacility = shipment.getDestination();
                if(destinationFacility != null && destinationFacility.getId().equals(destinationId))
                    filteredShipmentList.add(shipment);
            }
        }
        return filteredShipmentList;
//        return shipmentList;
    }


}

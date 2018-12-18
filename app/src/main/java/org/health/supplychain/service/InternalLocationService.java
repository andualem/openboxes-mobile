package org.health.supplychain.service;

import org.health.supplychain.entities.InternalLocation;
import org.health.supplychain.entities.LocationGroup;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class InternalLocationService implements IInternalLocationService {
    @Override
    public List<InternalLocation> getAllInternalLocations() {

        Realm realm = Realm.getDefaultInstance();
        RealmResults<InternalLocation> facilities = realm.where(InternalLocation.class).findAll();
        if(facilities != null)
            return facilities.subList(0, facilities.size());
        else
            return null;
    }

    @Override
    public boolean saveInternalLocation(InternalLocation internalLocation) {

        //TODO: requires future implementation
        LocationGroup locationGroup = internalLocation.getLocationGroup();
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        final InternalLocation savedInternalLocation = realm.copyToRealm(internalLocation);

        if(locationGroup != null) {
            final LocationGroup managedLocationGroup = realm.copyToRealm(locationGroup);
            savedInternalLocation.setLocationGroup(managedLocationGroup);
        }
        realm.commitTransaction();

        return savedInternalLocation != null;
    }

    @Override
    public InternalLocation getInternalLocationById(String id) {
        Realm realm = Realm.getDefaultInstance();
        InternalLocation internalLocation = realm.where(InternalLocation.class).equalTo("id", id).findFirst();
        return internalLocation;
    }

    @Override
    public boolean updateInternalLocation(InternalLocation exsitingInternalLocation, InternalLocation incomingInternalLocation) {
        return false;
    }
}

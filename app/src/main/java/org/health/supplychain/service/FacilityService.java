package org.health.supplychain.service;

import org.health.supplychain.entities.Facility;
import org.health.supplychain.entities.LocationGroup;
import org.health.supplychain.security.entities.UserSession;
import org.health.supplychain.security.service.IUserSessionService;
import org.health.supplychain.security.service.UserSessionService;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class FacilityService implements IFacilityService {


    @Override
    public List<Facility> getAllFacilities() {

        Realm realm = Realm.getDefaultInstance();
        RealmResults<Facility> facilities = realm.where(Facility.class).findAll();
        if(facilities != null && facilities.size() > 0)
            return facilities.subList(0, facilities.size());
        else
            return null;
    }

    @Override
    public List<Facility> getReceivingFacilities(String currentLocationId) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Facility> facilities = realm.where(Facility.class).
                notEqualTo("id", currentLocationId).findAll();
        if(facilities != null && facilities.size() > 0)
            return facilities.subList(0, facilities.size());
        else
            return null;
    }

    @Override
    public boolean saveFacility(Facility facility) {

        //TODO: requires future implementation
        LocationGroup locationGroup = facility.getLocationGroup();
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        final Facility savedFacility = realm.copyToRealm(facility);

        if(locationGroup != null) {
            final LocationGroup managedLocationGroup = realm.copyToRealm(locationGroup);
            savedFacility.setLocationGroup(managedLocationGroup);
        }
        realm.commitTransaction();

        return savedFacility != null;
    }

    @Override
    public Facility getFacilityById(String id) {
        Realm realm = Realm.getDefaultInstance();
        Facility facility = realm.where(Facility.class).equalTo("id", id).findFirst();
        return facility;
    }

    @Override
    public boolean updateFacility(Facility exsitingFacility, Facility incomingFacility) {
        return false;
    }

    @Override
    public Facility getUserCurrentFacility(String currentLocationId) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Facility> facilities = realm.where(Facility.class)
                .equalTo("id", currentLocationId)
                .findAll();

        if(facilities != null && !facilities.isEmpty())
            return facilities.first();
        else
            return null;
    }
}

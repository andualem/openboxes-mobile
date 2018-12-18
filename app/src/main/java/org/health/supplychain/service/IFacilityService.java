package org.health.supplychain.service;

import org.health.supplychain.entities.Facility;

import java.util.List;

public interface IFacilityService {

    public List<Facility> getAllFacilities();

    public List<Facility> getReceivingFacilities(String currentLocationId);

    public boolean saveFacility(Facility facility);

    public Facility getFacilityById(String id);

    public boolean updateFacility(Facility exsitingFacility, Facility incomingFacility);

    public Facility getUserCurrentFacility(String currentLocationId);

}

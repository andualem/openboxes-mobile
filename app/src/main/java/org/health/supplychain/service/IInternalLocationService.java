package org.health.supplychain.service;

import org.health.supplychain.entities.InternalLocation;

import java.util.List;

public interface IInternalLocationService {

    public List<InternalLocation> getAllInternalLocations();

    public boolean saveInternalLocation(InternalLocation internalLocation);

    public InternalLocation getInternalLocationById(String id);

    public boolean updateInternalLocation(InternalLocation exsitingInternalLocation, InternalLocation incomingInternalLocation);

}

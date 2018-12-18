package org.health.supplychain.service;

import org.health.supplychain.entities.ShipmentType;

import java.util.List;

public interface IShipmentTypeService {

    public boolean saveShipmentType(ShipmentType shipmentType);

    public ShipmentType getShipmentTypeById(String id);

    public List<ShipmentType> getAllShipmentType();

    public boolean updateShipmentType(ShipmentType existingShipmentType, ShipmentType incomingShipmentType);
}

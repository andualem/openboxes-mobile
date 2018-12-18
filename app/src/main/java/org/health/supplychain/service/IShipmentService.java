package org.health.supplychain.service;

import org.health.supplychain.entities.Shipment;

import java.util.List;

public interface IShipmentService {

    public boolean saveShipment(Shipment shipment);

    public boolean updateShipment(Shipment existingShipment, Shipment incomingShipment);

    public Shipment getShipmentById(String id);

    public List<Shipment> getShipmentByDestinationId (String destinationId, String status);

}

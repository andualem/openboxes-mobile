package org.health.supplychain.service;

import org.health.supplychain.entities.ShipmentTransaction;

public interface IShipmentTransactionService {

    public ShipmentTransaction getShipmentTransactionByUuid(String uuid);

    public void saveShipment(ShipmentTransaction shipmentTransaction);
}

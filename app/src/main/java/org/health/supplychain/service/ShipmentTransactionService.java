package org.health.supplychain.service;

import org.health.supplychain.entities.IssueTransaction;
import org.health.supplychain.entities.ShipmentTransaction;
import org.health.supplychain.entities.Transaction;

import io.realm.Realm;
import io.realm.RealmResults;

public class ShipmentTransactionService implements IShipmentTransactionService{

    @Override
    public ShipmentTransaction getShipmentTransactionByUuid(String uuid) {

        Realm realm = Realm.getDefaultInstance();
        ShipmentTransaction shipmentTransaction = realm.where(ShipmentTransaction.class)
        .equalTo("uuid", uuid).findFirst();

        return shipmentTransaction;
    }

    @Override
    public void saveShipment(ShipmentTransaction shipmentTransaction) {
        Transaction transaction;
        IssueTransaction issueTransaction;

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        transaction = realm.createObject(Transaction.class,
                shipmentTransaction.getIssueTransaction().getTransaction().getUuid());

    }
}

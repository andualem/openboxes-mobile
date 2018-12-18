package org.health.supplychain.service;

import org.health.supplychain.constants.Constants;
import org.health.supplychain.entities.Facility;
import org.health.supplychain.entities.ProductQuantity;
import org.health.supplychain.entities.ReceiveForRequestTransaction;
import org.health.supplychain.entities.ReceiveTransaction;
import org.health.supplychain.entities.RequestTransaction;
import org.health.supplychain.entities.Transaction;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

public class ReceiveTransactionService implements IReceiveTransactionService{

    private IAuthenticationService authenticationService;
    private ReceiveTransaction savedReceiveTransaction;
    private IFacilityService facilityService;


    @Override
    public boolean saveReceiveTransaction(Facility issuingFacility, String currentLocationId, List<ProductQuantity> productQuantityList,
                                          int transactionType) {
        Transaction transaction = new Transaction();

        facilityService = new FacilityService();
        Facility currentFacility = facilityService.getUserCurrentFacility(currentLocationId);

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        transaction = realm.createObject(Transaction.class, transaction.getUuid());
        transaction.setSourceFacility(issuingFacility);
        transaction.setDestinationFacility(currentFacility);
        transaction.setTransactionType(transactionType);
        transaction.setCreatedDate((new Date()).getTime());
        //TODO: update by user id
        transaction.setCreatedBy("OL");

        RealmList<ProductQuantity> productQuantities = new RealmList<>();
        productQuantities.addAll(productQuantityList);

        final List<ProductQuantity> managedProductQuantities
                = realm.copyToRealm(productQuantities);


        ReceiveTransaction receiveTransaction = new ReceiveTransaction();
        receiveTransaction = realm.createObject(ReceiveTransaction.class, receiveTransaction.getUuid());
        receiveTransaction.setTransaction(transaction);
        receiveTransaction.getProductQuantityRealmList().addAll(managedProductQuantities);
        receiveTransaction.setCreatedDate(transaction.getCreatedDate());
        receiveTransaction.setCreatedBy(transaction.getCreatedBy());

        realm.commitTransaction();

        savedReceiveTransaction = receiveTransaction;
        return true;
    }

    @Override
    public boolean saveReceiveForRequestTransaction(Facility issuingFacility, String currentLocationId, RequestTransaction requestTransaction, List<ProductQuantity> productQuantityList, int transactionType) {

        this.saveReceiveTransaction(issuingFacility, currentLocationId, productQuantityList, transactionType);

        if(savedReceiveTransaction != null){
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();

            final RequestTransaction mangedRequestTransaction = realm.copyToRealm(requestTransaction);

            ReceiveForRequestTransaction receiveForRequestTransaction = new ReceiveForRequestTransaction();
            receiveForRequestTransaction = realm.createObject(ReceiveForRequestTransaction.class,
                    receiveForRequestTransaction.getUuid());
            receiveForRequestTransaction.setReceiveTransaction(savedReceiveTransaction);
            receiveForRequestTransaction.setRequestTransaction(mangedRequestTransaction);
            receiveForRequestTransaction.setCreatedDate(savedReceiveTransaction.getCreatedDate());
            receiveForRequestTransaction.setCreatedBy(savedReceiveTransaction.getCreatedBy());

            realm.commitTransaction();
        }

        return true;
    }
}

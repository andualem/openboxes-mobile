package org.health.supplychain.service;

import org.health.supplychain.constants.Constants;
import org.health.supplychain.entities.Facility;
import org.health.supplychain.entities.IssueForRequestTransaction;
import org.health.supplychain.entities.IssueTransaction;
import org.health.supplychain.entities.Product;
import org.health.supplychain.entities.ProductQuantity;
import org.health.supplychain.entities.RequestTransaction;
import org.health.supplychain.entities.ShipmentType;
import org.health.supplychain.entities.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class IssuanceTransactionService implements IIssuanceTransactionService{

    private IAuthenticationService authenticationService;
    private IFacilityService facilityService;
    private IssueTransaction savedIssueTransaction;

    @Override
    public boolean saveIssuance(Facility receivingFacility, String currentLocationId, List<ProductQuantity> productQuantityList,
                                ShipmentType shipmentType, int transactionType) {
        Transaction transaction = new Transaction();
        facilityService = new FacilityService();
        Facility currentFacility = facilityService.getUserCurrentFacility(currentLocationId);

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        transaction = realm.createObject(Transaction.class, transaction.getUuid());
        transaction.setSourceFacility(currentFacility);
        transaction.setDestinationFacility(receivingFacility);
        transaction.setTransactionType(transactionType);
        transaction.setCreatedDate((new Date()).getTime());
        //TODO: update by user id
        transaction.setCreatedBy("OL");

        RealmList<ProductQuantity> productQuantities = new RealmList<>();
        productQuantities.addAll(productQuantityList);

        final List<ProductQuantity> managedProductQuantities
                = realm.copyToRealm(productQuantities);

        IssueTransaction issueTransaction = new IssueTransaction();
        issueTransaction = realm.createObject(IssueTransaction.class, issueTransaction.getUuid());
        issueTransaction.setTransaction(transaction);
        issueTransaction.getProductQuantityRealmList().addAll(managedProductQuantities);
        issueTransaction.setCreatedDate(transaction.getCreatedDate());
        issueTransaction.setCreatedBy(transaction.getCreatedBy());
        issueTransaction.setShipmentTypeId(shipmentType.getId());

        realm.commitTransaction();
        savedIssueTransaction  = issueTransaction;
        return issueTransaction.isManaged();
    }

    @Override
    public boolean saveIssuanceForRequest(Facility requestingFacility, String currentLocationId, RequestTransaction requestTransaction,
                                          List<ProductQuantity> productQuantityList, ShipmentType shipmentType, int transactionType) {
        this.saveIssuance(requestingFacility, currentLocationId, productQuantityList, shipmentType, transactionType);

        if(savedIssueTransaction != null) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();

            final RequestTransaction mangedRequestTransaction = realm.copyToRealm(requestTransaction);

            IssueForRequestTransaction issueForRequestTransaction = new IssueForRequestTransaction();
            issueForRequestTransaction = realm.createObject(IssueForRequestTransaction.class, issueForRequestTransaction.getUuid());
            issueForRequestTransaction.setIssueTransaction(savedIssueTransaction);
            issueForRequestTransaction.setRequestTransaction(mangedRequestTransaction);
            issueForRequestTransaction.setCreatedDate(savedIssueTransaction.getCreatedDate());
            issueForRequestTransaction.setCreatedBy(savedIssueTransaction.getCreatedBy());

            realm.commitTransaction();
        }

        return true;
    }

    @Override
    public IssueTransaction getIssueTransactionByUuid(String uuid) {
        Realm realm = Realm.getDefaultInstance();
        IssueTransaction issueTransaction = realm.where(IssueTransaction.class).equalTo("uuid", uuid).findFirst();
        return issueTransaction;
    }

    @Override
    public List<IssueTransaction> getUnsyncedIssueTransaction(String currentLocationId) {

        Realm realm = Realm.getDefaultInstance();
        RealmResults<IssueTransaction> issueTransaction = realm.where(IssueTransaction.class)
                .equalTo("syncStatus", Constants.SYNC_FAILURE).findAll();

        if(issueTransaction != null && issueTransaction.size() > 0) {
            List<IssueTransaction> issueTransactionList = issueTransaction.subList(0, issueTransaction.size());
            List<IssueTransaction> filteredIssueTransaction = new ArrayList<>();
            for(IssueTransaction issue: issueTransactionList){
                //TODO: check for null pointer exceptions
                if(issue.getTransaction().getSourceFacility().getId().equals(currentLocationId))
                    filteredIssueTransaction.add(issue);
            }

            return filteredIssueTransaction;
//            return issueTransactionList;
        }
        else
            return null;

    }
}

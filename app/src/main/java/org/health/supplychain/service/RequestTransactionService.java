package org.health.supplychain.service;

import org.health.supplychain.constants.Constants;
import org.health.supplychain.entities.Facility;
import org.health.supplychain.entities.IssueTransaction;
import org.health.supplychain.entities.ProductQuantity;
import org.health.supplychain.entities.RequestTransaction;
import org.health.supplychain.entities.Transaction;
import org.health.supplychain.security.entities.UserSession;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class RequestTransactionService implements IRequestTransactionService {

    private IAuthenticationService authenticationService;
    private IFacilityService facilityService;

    @Override
    public boolean saveRequest(Facility requestedFacility, UserSession userSession, List<ProductQuantity> productQuantityList) {

        Transaction transaction = new Transaction();

        facilityService = new FacilityService();
        Facility currentFacility = facilityService.getUserCurrentFacility(userSession.getLocationId());

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        transaction = realm.createObject(Transaction.class, transaction.getUuid());
        transaction.setSourceFacility(currentFacility);
        transaction.setDestinationFacility(requestedFacility);
        transaction.setTransactionType(Constants.TRANSACTION_REQUEST);
        transaction.setCreatedDate((new Date()).getTime());

        transaction.setCreatedBy(userSession.getUserId());
        transaction.setCreatedByUsername(userSession.getUsername());

        RealmList<ProductQuantity> productQuantities = new RealmList<>();
        productQuantities.addAll(productQuantityList);

        final List<ProductQuantity> managedProductQuantities
                = realm.copyToRealm(productQuantities);

        RequestTransaction requestTransaction = new RequestTransaction();
        requestTransaction = realm.createObject(RequestTransaction.class, requestTransaction.getUuid());
        requestTransaction.setTransaction(transaction);
        requestTransaction.getProductQuantityRealmList().addAll(managedProductQuantities);
        requestTransaction.setCreatedDate(transaction.getCreatedDate());
        requestTransaction.setCreatedBy(transaction.getCreatedBy());

        realm.commitTransaction();


        return requestTransaction.isManaged();
    }

    @Override
    public RequestTransaction getRequestTransactionByUuid(String requestUuid) {
        return null;
    }

    @Override
    public List<RequestTransaction> getUnsyncedRequestTransaction(String currentLocationId) {

        Realm realm = Realm.getDefaultInstance();
        RealmResults<RequestTransaction> requestTransaction = realm.where(RequestTransaction.class)
                .equalTo("syncStatus", Constants.SYNC_FAILURE).findAll();

        if(requestTransaction != null && requestTransaction.size() > 0) {
            List<RequestTransaction> requestTransactionList = requestTransaction.subList(0, requestTransaction.size());
            List<RequestTransaction> filteredRequestTransaction = new ArrayList<>();
            for(RequestTransaction request: requestTransactionList){
                //TODO: check for null pointer exceptions
                if(request.getTransaction().getSourceFacility().getId().equals(currentLocationId))
                    filteredRequestTransaction.add(request);
            }

            //TODO: returned filtered request transaction
//            return filteredRequestTransaction;
            return requestTransactionList;
        }
        else
            return null;
    }
}

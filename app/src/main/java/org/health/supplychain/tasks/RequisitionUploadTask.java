package org.health.supplychain.tasks;

import android.os.AsyncTask;

import org.health.supplychain.constants.Constants;
import org.health.supplychain.entities.AvailableItem;
import org.health.supplychain.entities.Facility;
import org.health.supplychain.entities.IssueTransaction;
import org.health.supplychain.entities.Product;
import org.health.supplychain.entities.ProductQuantity;
import org.health.supplychain.entities.RequestTransaction;
import org.health.supplychain.entities.Transaction;
import org.health.supplychain.service.IRequestTransactionService;
import org.health.supplychain.service.IShipmentTypeService;
import org.health.supplychain.service.RequestTransactionService;
import org.health.supplychain.service.ShipmentTypeService;
import org.health.supplychain.utility.Utils;
import org.health.supplychain.webservice.entities.InventoryItem;
import org.health.supplychain.webservice.entities.OriginDestination;
import org.health.supplychain.webservice.entities.ProductCategory;
import org.health.supplychain.webservice.entities.Requester;
import org.health.supplychain.webservice.entities.Requisition;
import org.health.supplychain.webservice.entities.RequisitionItem;
import org.health.supplychain.webservice.entities.RequisitionRequest;
import org.health.supplychain.webservice.entities.Shipment;
import org.health.supplychain.webservice.entities.ShipmentRequest;
import org.health.supplychain.webservice.entities.ShipmentResponse;
import org.health.supplychain.webservice.entities.ShipmentType;
import org.health.supplychain.webservice.service.APIServices;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import retrofit2.Response;

public class RequisitionUploadTask extends AsyncTask<Void, Void, String> {

    private Product product;
    private APIServices apiServices = new APIServices();
    private boolean hasUnSyncedRecord;
    private String session, locationId;
    private IRequestTransactionService requestTransactionService;


    public RequisitionUploadTask(String session, String locationId) {
        this.session = session;
        this.locationId = locationId;
    }

    @Override
    protected String doInBackground(Void... voids) {

        requestTransactionService = new RequestTransactionService();
        List<RequestTransaction> requestTransactionList = requestTransactionService.getUnsyncedRequestTransaction(locationId);
        RequisitionRequest requisitionRequest = getMappedRequisitionRequest(requestTransactionList);
        try {
            Response<RequisitionRequest> response = apiServices.pushRequisition(session, requisitionRequest);
            if(response.isSuccessful()){
                boolean isStatusUpdated = true;
                if(isStatusUpdated && !hasUnSyncedRecord) {
//                    super.setSuccess(isStatusUpdated);
                    return String.format(Constants.SYNC_SUCCESS_MESSAGE, "choose location");
                }
                else
                    return String.format(Constants.SYNC_FAILURE_MESSAGE, "choose location");
            } else {
                return response.errorBody().string();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return String.format(Constants.SYNC_FAILURE_MESSAGE, "choose location");
        }

    }


    private RequisitionRequest getMappedRequisitionRequest(List<RequestTransaction> requestTransactionList){
        RequisitionRequest requisitionRequest = null;
        if(requestTransactionList != null && !requestTransactionList.isEmpty()){
            List<Requisition> requisitionList = new ArrayList<>();
            Requisition requisition;

            for(RequestTransaction requestTransaction: requestTransactionList){
                requisition = new Requisition();
                requisition.setId(requestTransaction.getUuid());

                // origin & destination
                Transaction transaction = requestTransaction.getTransaction();
                Facility sourceFacility, destinationFacility;
                sourceFacility = transaction.getSourceFacility();
                destinationFacility = transaction.getDestinationFacility();
                requisition.setOrigin(getMappedFacility(sourceFacility));
                requisition.setDestination(getMappedFacility(destinationFacility));

                requisition.setName("Request from " + sourceFacility.getName());
//                requisition.setDateRequested(Utils.convertDateToString(requestTransaction.getCreatedDate(), Constants.DATE_TIME_FORMAT));
                requisition.setDateRequested("12/27/2018 00:00 +03:00");


                requisition.setRequestedBy(getMappedRequester(requestTransaction.getCreatedBy(), transaction.getCreatedByUsername()));

                RealmList<ProductQuantity> productQuantityRealmList = requestTransaction.getProductQuantityRealmList();
                List<ProductQuantity> productQuantityList = productQuantityRealmList.subList(0, productQuantityRealmList.size());
                requisition.setRequisitionItems(getMappedRequisitionItem(productQuantityList, requestTransaction.getUuid()));

                requisitionList.add(requisition);
            }

            requisitionRequest = new RequisitionRequest();
            requisitionRequest.setData(requisitionList);
            return requisitionRequest;
        }
        return requisitionRequest;
    }

    private OriginDestination getMappedFacility(Facility facility){
        OriginDestination originDestination = null;
        if(facility != null){
            originDestination = new OriginDestination();
            originDestination.setId(facility.getId());
            originDestination.setName(facility.getName());
            originDestination.setType(facility.getType());
            return originDestination;
        }

        return originDestination;
    }


    private List<RequisitionItem> getMappedRequisitionItem (List<ProductQuantity> productQuantityList, String requestUuid) {
        List<RequisitionItem> requisitionItemList;
        if(productQuantityList != null && !productQuantityList.isEmpty()){
            requisitionItemList = new ArrayList<>();
            RequisitionItem requisitionItem;
            for(ProductQuantity productQuantity: productQuantityList){
                requisitionItem = new RequisitionItem();
                requisitionItem.setRequisitionId(requestUuid);
                requisitionItem.setProduct(getMappedProduct(productQuantity.getProduct()));
                requisitionItem.setInventoryItem(getInventoryItem(productQuantity.getAvailableItem()));
                requisitionItem.setQuantity(productQuantity.getRequestedQuantity());
                requisitionItemList.add(requisitionItem);
            }
            return requisitionItemList;
        }
        return null;
    }

    private InventoryItem getInventoryItem(AvailableItem availableItem){
        if(availableItem != null) {
            InventoryItem inventoryItem = new InventoryItem();
            inventoryItem.setId(availableItem.getInventoryItemId());
            inventoryItem.setExpirationDate(availableItem.getExpirationDate());
            inventoryItem.setLotNumber(availableItem.getLotNumber());

            org.health.supplychain.webservice.entities.Product product = new
                    org.health.supplychain.webservice.entities.Product();

            product.setId(availableItem.getProductId());
            product.setName(availableItem.getProductName());
            product.setProductCode(availableItem.getProductCode());
            product.setId(availableItem.getProductId());

            inventoryItem.setProduct(product);
            return inventoryItem;
        }
        return null;
    }


    private Requester getMappedRequester (String createdBy, String username){
        Requester requester = new Requester();
        requester.setId(createdBy);
        requester.setFirstName(username);
        requester.setLastName(username);
        requester.setUsername(username);
        return requester;
    }

    private org.health.supplychain.webservice.entities.Product getMappedProduct(Product product){
        org.health.supplychain.webservice.entities.Product p = new org.health.supplychain.webservice.entities.Product();
        p.setId(product.getProductId());
        p.setProductCode(product.getCode());
        p.setName(product.getName());

        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(product.getCategoryName());
        productCategory.setName(product.getCategoryName());
        p.setCategory(productCategory);

        p.setDescription(product.getDescription());
        return p;
    }

}

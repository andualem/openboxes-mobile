package org.health.supplychain.tasks;

import android.os.AsyncTask;

import org.health.supplychain.constants.Constants;
import org.health.supplychain.entities.AvailableItem;
import org.health.supplychain.entities.Facility;
import org.health.supplychain.entities.IssueTransaction;
import org.health.supplychain.entities.Product;
import org.health.supplychain.entities.ProductQuantity;
import org.health.supplychain.entities.Transaction;
import org.health.supplychain.service.IIssuanceTransactionService;
import org.health.supplychain.service.IShipmentTypeService;
import org.health.supplychain.service.IssuanceTransactionService;
import org.health.supplychain.service.ShipmentTypeService;
import org.health.supplychain.webservice.entities.InventoryItem;
import org.health.supplychain.webservice.entities.OriginDestination;
import org.health.supplychain.webservice.entities.ShipmentDetail;
import org.health.supplychain.webservice.entities.ShipmentInitialRequest;
import org.health.supplychain.webservice.entities.ShipmentItems;
import org.health.supplychain.webservice.entities.SingleShipmentResponse;
import org.health.supplychain.webservice.service.APIServices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class SingleShipmentUploadTask extends AsyncTask<Void, Void, String> {

    private Product product;
    private APIServices apiServices = new APIServices();
    private boolean hasUnSyncedRecord;
    private String session, locationId, issueTransactionUuid;

    private IIssuanceTransactionService issuanceTransactionService;
    private IShipmentTypeService shipmentTypeService;
    public SingleShipmentResponse singleShipmentResponse;

    public SingleShipmentUploadTask(String session, String locationId, String issueTransactionUuid) {
        this.session = session;
        this.locationId = locationId;
        this.issueTransactionUuid = issueTransactionUuid;
    }

    @Override
    protected String doInBackground(Void... voids) {

        issuanceTransactionService = new IssuanceTransactionService();
        IssueTransaction issueTransaction = issuanceTransactionService.getIssueTransactionByUuid(issueTransactionUuid);
        ShipmentInitialRequest shipmentInitialRequest = getMappedShipmentInitialRequest(issueTransaction);
        try {
            Response<SingleShipmentResponse> response = apiServices.pushShipmentInitialRequest(this.session, shipmentInitialRequest);
            if(response.isSuccessful()){
                boolean isStatusUpdated = true;
                singleShipmentResponse = response.body();
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

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(singleShipmentResponse != null){
            String shipmentId = singleShipmentResponse.getData().getId();

            ShipmentItemUploadTask shipmentItemUploadTask = new ShipmentItemUploadTask(session, issueTransactionUuid, shipmentId);
            shipmentItemUploadTask.execute();
        }

    }

    private ShipmentInitialRequest getMappedShipmentInitialRequest(IssueTransaction issueTransaction){
        if(issueTransaction != null){
            ShipmentInitialRequest shipmentInitialRequest = new ShipmentInitialRequest();
            shipmentTypeService = new ShipmentTypeService();
            shipmentInitialRequest.setName(issueTransaction.getName());
            // origin & destination
            Transaction transaction = issueTransaction.getTransaction();
            Facility sourceFacility, destinationFacility;
            sourceFacility = transaction.getSourceFacility();
            destinationFacility = transaction.getDestinationFacility();
            shipmentInitialRequest.setOriginId(getMappedFacility(sourceFacility).getId());
            shipmentInitialRequest.setDestinationId(getMappedFacility(destinationFacility).getId());
            //TODO: requires fixing
//                shipment.setExpectedShippingDate(issueTran.getExpectedShippingDate());
            shipmentInitialRequest.setExpectedShippingDate("12/27/2018 00:00 +03:00");

//            shipmentInitialRequest.setShipmentTypeId(issueTransaction.getShipmentTypeId());
            shipmentInitialRequest.setShipmentTypeId("1");
            return shipmentInitialRequest;
        }
        return null;
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


    private List<ShipmentItems> getMappedShipmentItemList (List<ProductQuantity> productQuantityList, ShipmentDetail shipmentDetail){
        List<ShipmentItems> shipmentItemsList = null;
        ShipmentItems shipmentItems;
        if(productQuantityList != null && !productQuantityList.isEmpty()){
            shipmentItemsList = new ArrayList<>();
            for(ProductQuantity pq: productQuantityList) {
                shipmentItems = new ShipmentItems();
                shipmentItems.setInventoryItem(getInventoryItem(pq.getAvailableItem()));
                shipmentItems.setQuantity((int) pq.getIssuedQuantity());
                shipmentItems.setShipment(shipmentDetail);
                shipmentItemsList.add(shipmentItems);
            }

        }
        return shipmentItemsList;
    }

    private InventoryItem getInventoryItem(AvailableItem availableItem){
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
}

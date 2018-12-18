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
import org.health.supplychain.webservice.entities.Shipment;
import org.health.supplychain.webservice.entities.ShipmentDetail;
import org.health.supplychain.webservice.entities.ShipmentItems;
import org.health.supplychain.webservice.entities.ShipmentRequest;
import org.health.supplychain.webservice.entities.ShipmentResponse;
import org.health.supplychain.webservice.entities.ShipmentType;
import org.health.supplychain.webservice.service.APIServices;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class ShipmentUploadTask extends AsyncTask<Void, Void, String> {

    private Product product;
    private APIServices apiServices = new APIServices();
    private boolean hasUnSyncedRecord;
    private String session, locationId;

    private IIssuanceTransactionService issuanceTransactionService;
    private IShipmentTypeService shipmentTypeService;

    public ShipmentUploadTask(String session, String locationId) {
        this.session = session;
        this.locationId = locationId;
    }

    @Override
    protected String doInBackground(Void... voids) {

        issuanceTransactionService = new IssuanceTransactionService();
        List<IssueTransaction> issueTransactionList = issuanceTransactionService.getUnsyncedIssueTransaction(locationId);
        ShipmentRequest shipmentRequest = getMappedShipmentRequest(issueTransactionList);
        try {
            Response<ShipmentResponse> response = apiServices.pushShipment(this.session, shipmentRequest);
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


    private ShipmentRequest getMappedShipmentRequest(List<IssueTransaction> issueTransactionList){
        ShipmentRequest shipmentRequest = null;
        if(issueTransactionList != null && !issueTransactionList.isEmpty()){
            List<Shipment> shipmentList = new ArrayList<>();
            Shipment shipment;
            shipmentTypeService = new ShipmentTypeService();
            for(IssueTransaction issueTran: issueTransactionList){
                shipment = new Shipment();
                shipment.setName(issueTran.getName());
                // origin & destination
                Transaction transaction = issueTran.getTransaction();
                Facility sourceFacility, destinationFacility;
                sourceFacility = transaction.getSourceFacility();
                destinationFacility = transaction.getDestinationFacility();
                shipment.setOrigin(getMappedFacility(sourceFacility));
                shipment.setDestination(getMappedFacility(destinationFacility));
                //TODO: requires fixing
//                shipment.setExpectedShippingDate(issueTran.getExpectedShippingDate());
                shipment.setExpectedShippingDate("12/27/2018 00:00 +03:00");

//                org.health.supplychain.entities.ShipmentType savedShipmentType = shipmentTypeService.getShipmentTypeById(issueTran.getShipmentTypeId());
                org.health.supplychain.entities.ShipmentType savedShipmentType = shipmentTypeService.getShipmentTypeById("1");
                ShipmentType shipmentType = new ShipmentType();
                shipmentType.setId(savedShipmentType.getId());
                shipment.setShipmentType(shipmentType);


                // this part will be sent later
                /*
                RealmList<ProductQuantity> productQuantityRealmList = issueTran.getProductQuantityRealmList();
                List<ProductQuantity> productQuantityList =
                        productQuantityRealmList.subList(0, productQuantityRealmList.size());


                ShipmentDetail shipmentDetail = new ShipmentDetail();
                shipmentDetail.setId(shipment.getId());
                shipmentDetail.setName(shipment.getName());

                shipment.setShipmentItems(getMappedShipmentItemList(productQuantityList, shipmentDetail));
                */


                shipmentList.add(shipment);
            }

            shipmentRequest = new ShipmentRequest();
            shipmentRequest.setData(shipmentList);
            return shipmentRequest;
        }
        return shipmentRequest;
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

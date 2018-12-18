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
import org.health.supplychain.webservice.entities.ShipmentItemRequest;
import org.health.supplychain.webservice.entities.ShipmentItems;
import org.health.supplychain.webservice.entities.ShipmentRequest;
import org.health.supplychain.webservice.entities.ShipmentResponse;
import org.health.supplychain.webservice.entities.ShipmentType;
import org.health.supplychain.webservice.service.APIServices;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import retrofit2.Response;

public class ShipmentItemUploadTask extends AsyncTask<Void, Void, String> {

    private Product product;
    private APIServices apiServices = new APIServices();
    private boolean hasUnSyncedRecord;
    private String session, issueTranUuid, shipmentId;

    private IIssuanceTransactionService issuanceTransactionService;
    private IShipmentTypeService shipmentTypeService;

    public ShipmentItemUploadTask(String session, String issueTranUuid, String shipmentId) {
        this.session = session;
        this.issueTranUuid = issueTranUuid;
        this.shipmentId = shipmentId;
    }

    @Override
    protected String doInBackground(Void... voids) {

        issuanceTransactionService = new IssuanceTransactionService();
        IssueTransaction issueTransaction = issuanceTransactionService.getIssueTransactionByUuid(issueTranUuid);
        List<ShipmentItemRequest> shipmentItemRequestList = getMappedShipmentItemRequest(issueTransaction);
        try {
            Response<ShipmentResponse> response = apiServices.pushShipmentItem(this.session, shipmentItemRequestList);
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


    private List<ShipmentItemRequest> getMappedShipmentItemRequest(IssueTransaction issueTransaction){
        ShipmentRequest shipmentRequest = null;
        if(issueTransaction != null ){
            List<ShipmentItemRequest> shipmentItemRequestList;

            RealmList<ProductQuantity> productQuantityRealmList = issueTransaction.getProductQuantityRealmList();
            List<ProductQuantity> productQuantityList =
                    productQuantityRealmList.subList(0, productQuantityRealmList.size());

            shipmentItemRequestList = getMappedShipmentItemRequestList(productQuantityList, shipmentId);
            return shipmentItemRequestList;
        }
        return null;
    }

    private List<ShipmentItemRequest> getMappedShipmentItemRequestList (List<ProductQuantity> productQuantityList, String shipmentId){
        List<ShipmentItemRequest> shipmentItemRequestList = null;
        ShipmentItemRequest shipmentItemRequest;
        if(productQuantityList != null && !productQuantityList.isEmpty()){
            shipmentItemRequestList = new ArrayList<>();
            for(ProductQuantity pq: productQuantityList) {
                shipmentItemRequest = new ShipmentItemRequest();
                shipmentItemRequest.setInventoryItemId(pq.getAvailableItem().getInventoryItemId());
                shipmentItemRequest.setShipmentId(shipmentId);
                shipmentItemRequest.setProductId(pq.getProduct().getProductId());
                shipmentItemRequest.setQuantity(pq.getIssuedQuantity());
                shipmentItemRequestList.add(shipmentItemRequest);
            }

        }
        return shipmentItemRequestList;
    }

}

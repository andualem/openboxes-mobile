package org.health.supplychain.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;

import org.health.supplychain.constants.Constants;
import org.health.supplychain.entities.AvailableItem;
import org.health.supplychain.entities.Facility;
import org.health.supplychain.service.IRequisitionService;
import org.health.supplychain.service.RequisitionService;
import org.health.supplychain.webservice.entities.InventoryItem;
import org.health.supplychain.webservice.entities.OriginDestination;
import org.health.supplychain.webservice.entities.Product;
import org.health.supplychain.webservice.entities.Requisition;
import org.health.supplychain.webservice.entities.RequisitionItem;
import org.health.supplychain.webservice.entities.RequisitionRequest;
import org.health.supplychain.webservice.service.APIServices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * Created by Aworkneh on 5/4/2018.
 */

public class RequisitionDownloadTask extends AsyncTask<Void, Void, String> {

    private APIServices apiServices = new APIServices();
    private String sessionId;
    private String max;
//    private IFacilityService facilityService;
    private IRequisitionService requisitionService;
    private Context context;

    public RequisitionDownloadTask(Context context, String sessionId, ImageView imageView){
        this.sessionId = sessionId;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
//            Response<LocationListResponse> response = apiServices.getLocations(sessionId);
            Response<RequisitionRequest> response = apiServices.getRequisition(sessionId);
            if(response.isSuccessful()){
                RequisitionRequest requisitionRequest = response.body();
                List<Requisition> incomingRequisitionList;
                incomingRequisitionList = requisitionRequest != null? requisitionRequest.getData():null;
                List<org.health.supplychain.entities.Requisition> requisitionList = getMappedRequisitionList(incomingRequisitionList);
                if(requisitionList != null && !requisitionList.isEmpty()) {
                    boolean isCommoditySaved = saveRequisitionList(requisitionList);
//                    super.setSuccess(isCommoditySaved);
                    if(isCommoditySaved)
                        return String.format(Constants.SYNC_SUCCESS_MESSAGE, "input");
                    else
                        return String.format(Constants.SYNC_FAILURE_MESSAGE, "input");
                } else {
                    return String.format(Constants.SYNC_FAILURE_MESSAGE, "input");
                }
            } else {
                return response.errorBody().string();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return String.format(Constants.SYNC_FAILURE_MESSAGE, "input");
        }

    }

    private boolean saveRequisitionList(List<org.health.supplychain.entities.Requisition> requisitionList) {
        boolean isSaved = true;
        requisitionService = new RequisitionService();
        org.health.supplychain.entities.Requisition existingShipment;
        for(org.health.supplychain.entities.Requisition requisition: requisitionList){
            existingShipment = requisitionService.getRequisitionById(requisition.getId());
            if(existingShipment != null){
//                if(existingLocation.getUpdatedDate() >= commodity.getUpdatedDate()) // && commodity.getUpdatedDate() != 0)
//                    continue;
                isSaved &= requisitionService.updateRequisition(existingShipment, requisition);
            } else {
                isSaved &=  requisitionService.saveRequisition(requisition);
            }
        }
        return isSaved;
    }

    private List<org.health.supplychain.entities.Requisition> getMappedRequisitionList(List<Requisition> incomingRequisitionList){
        List<org.health.supplychain.entities.Requisition> requisitionList;
        if(incomingRequisitionList != null){
            requisitionList = new ArrayList<>();
            org.health.supplychain.entities.Requisition requisition;

            for(Requisition incomingRequisition: incomingRequisitionList){
                requisition = new org.health.supplychain.entities.Requisition();
                requisition.setId(incomingRequisition.getId());
                requisition.setName(incomingRequisition.getName());
                requisition.setStatus(incomingRequisition.getStatus());
                requisition.setOrigin(getMappedFacility(incomingRequisition.getOrigin()));
                requisition.setDestination(getMappedFacility(incomingRequisition.getDestination()));
                requisition.setDateRequested(incomingRequisition.getDateRequested());
                requisition.setUnmanagedRequisitionItemList(getMappedRequisitionItem(incomingRequisition.getRequisitionItems()));

                requisitionList.add(requisition);
            }
            return requisitionList;
        }
        return null;
    }


    private Facility getMappedFacility (OriginDestination originDestination){
        Facility facility = new Facility();
        if(originDestination != null){
            facility.setId(originDestination.getId());
            facility.setName(originDestination.getName());
            facility.setType(originDestination.getType());
            return facility;
        }
        return null;
    }

    private List<org.health.supplychain.entities.RequisitionItem> getMappedRequisitionItem(List<RequisitionItem> incomingRequisitionItems){
        List<org.health.supplychain.entities.RequisitionItem> requisitionItemList = null;
        org.health.supplychain.entities.RequisitionItem requisitionItem;

        if(incomingRequisitionItems != null && !incomingRequisitionItems.isEmpty()){
            requisitionItemList = new ArrayList<>();
            for(RequisitionItem incomingItem: incomingRequisitionItems){
                requisitionItem = new org.health.supplychain.entities.RequisitionItem();
                requisitionItem.setId(incomingItem.getId());
                requisitionItem.setQuantity(incomingItem.getQuantity());


//                AvailableItem availableItem = getMappedAvailableItem(incomingItem.getInventoryItem());
//                requisitionItem.setInventoryItem(availableItem);
                requisitionItemList.add(requisitionItem);
            }
        }

        return requisitionItemList;
    }

    private AvailableItem getMappedAvailableItem(InventoryItem inventoryItem){
        AvailableItem availableItem = null;

        if(inventoryItem != null){
            availableItem = new AvailableItem();
            availableItem.setInventoryItemId(inventoryItem.getId());
            Product product = inventoryItem.getProduct();
            if(product != null) {
                availableItem.setProductId(inventoryItem.getProduct().getId());
                availableItem.setProductName(product.getName());
                availableItem.setProductCode(product.getProductCode());
            }
            availableItem.setLotNumber(inventoryItem.getLotNumber());
            availableItem.setExpirationDate(inventoryItem.getExpirationDate());
        }
        return availableItem;
    }



}
package org.health.supplychain.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;

import org.health.supplychain.constants.Constants;
import org.health.supplychain.entities.AvailableItem;
import org.health.supplychain.entities.Container;
import org.health.supplychain.entities.Facility;
import org.health.supplychain.entities.ShipmentItem;
import org.health.supplychain.service.FacilityService;
import org.health.supplychain.service.IFacilityService;
import org.health.supplychain.service.IShipmentService;
import org.health.supplychain.service.ShipmentService;
import org.health.supplychain.webservice.entities.Containers;
import org.health.supplychain.webservice.entities.InventoryItem;
import org.health.supplychain.webservice.entities.LocationData;
import org.health.supplychain.webservice.entities.LocationGroup;
import org.health.supplychain.webservice.entities.LocationListResponse;
import org.health.supplychain.webservice.entities.OriginDestination;
import org.health.supplychain.webservice.entities.Product;
import org.health.supplychain.webservice.entities.Shipment;
import org.health.supplychain.webservice.entities.ShipmentDetail;
import org.health.supplychain.webservice.entities.ShipmentItems;
import org.health.supplychain.webservice.entities.ShipmentResponse;
import org.health.supplychain.webservice.service.APIServices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * Created by Aworkneh on 5/4/2018.
 */

public class ShipmentDownloadTask extends AsyncTask<Void, Void, String> {

    private APIServices apiServices = new APIServices();
    private String sessionId;
    private String max;
//    private IFacilityService facilityService;
    private IShipmentService shipmentService;
    private Context context;

    public ShipmentDownloadTask(Context context, String sessionId, String max, ImageView imageView){
        this.sessionId = sessionId;
        this.max = max;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
//            Response<LocationListResponse> response = apiServices.getLocations(sessionId);
            Response<ShipmentResponse> response = apiServices.getShipment(sessionId, max);
            if(response.isSuccessful()){
                ShipmentResponse shipmentResponse = response.body();
                List<Shipment> incomingShipmentList;
                incomingShipmentList = shipmentResponse != null? shipmentResponse.getData():null;
                List<org.health.supplychain.entities.Shipment> shipmentList = getMappedShipmentList(incomingShipmentList);
                if(shipmentList != null && !shipmentList.isEmpty()) {
                    boolean isCommoditySaved = saveShipmentList(shipmentList);
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

    private boolean saveShipmentList(List<org.health.supplychain.entities.Shipment> shipmentList) {
        boolean isSaved = true;
        shipmentService = new ShipmentService();
        org.health.supplychain.entities.Shipment existingShipment;
        for(org.health.supplychain.entities.Shipment shipment: shipmentList){
            existingShipment = shipmentService.getShipmentById(shipment.getId());
            if(existingShipment != null){
//                if(existingLocation.getUpdatedDate() >= commodity.getUpdatedDate()) // && commodity.getUpdatedDate() != 0)
//                    continue;
                isSaved &= shipmentService.updateShipment(existingShipment, shipment);
            } else {
                isSaved &=  shipmentService.saveShipment(shipment);
            }
        }
        return isSaved;
    }

    private List<org.health.supplychain.entities.Shipment> getMappedShipmentList(List<Shipment> incomingShipmentList){
        List<org.health.supplychain.entities.Shipment> shipmentList;
        if(incomingShipmentList != null){
            shipmentList = new ArrayList<>();
            org.health.supplychain.entities.Shipment shipment;

            for(Shipment shipmentResponse: incomingShipmentList){
                shipment = new org.health.supplychain.entities.Shipment();
                shipment.setId(shipmentResponse.getId());
                shipment.setName(shipmentResponse.getName());
                shipment.setStatus(shipmentResponse.getStatus());
                shipment.setOrigin(getMappedFacility(shipmentResponse.getOrigin()));
                shipment.setDestination(getMappedFacility(shipmentResponse.getDestination()));
                shipment.setExpectedShippingDate(shipmentResponse.getExpectedShippingDate());
                shipment.setActualShippingDate(shipmentResponse.getActualShippingDate());
                shipment.setExpectedDeliveryDate(shipmentResponse.getExpectedDeliveryDate());
                shipment.setActualDeliveryDate(shipmentResponse.getActualDeliveryDate());
                shipment.setUnmanagedShipmentItemList(getMappedShipmentItem(shipmentResponse.getShipmentItems()));
                shipment.setUnmananagedContainerList(getMappedContainerList(shipmentResponse.getContainers()));
                shipmentList.add(shipment);
            }
            return shipmentList;
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

    private List<org.health.supplychain.entities.ShipmentItem> getMappedShipmentItem(List<ShipmentItems> incomingShipmentItem){
        List<org.health.supplychain.entities.ShipmentItem> shipmentItemList = null;
        ShipmentItem shipmentItem;

        if(incomingShipmentItem != null && !incomingShipmentItem.isEmpty()){
            shipmentItemList = new ArrayList<>();
            for(ShipmentItems incomingItem: incomingShipmentItem){
                shipmentItem = new ShipmentItem();
                shipmentItem.setId(incomingItem.getId());
                shipmentItem.setContainer(incomingItem.getContainer());
                shipmentItem.setQuantity(incomingItem.getQuantity());
                shipmentItem.setRecipient(incomingItem.getRecipient());
                ShipmentDetail shipmentDetail = incomingItem.getShipment();
                if(shipmentDetail != null) {
                    shipmentItem.setShipmentDetailId(shipmentDetail.getId());
                    shipmentItem.setShipmentDetailName(shipmentDetail.getName());
                }

                AvailableItem availableItem = getMappedAvailableItem(incomingItem.getInventoryItem());
                shipmentItem.setInventoryItem(availableItem);
                shipmentItemList.add(shipmentItem);
            }
        }

        return shipmentItemList;
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


    private List<Container> getMappedContainerList(List<Containers> containersList){
        List<Container> containerList;
        Container container;
        if(containersList != null && containersList.isEmpty()){
            containerList = new ArrayList<>();
            for(Containers incomingContainer: containersList){
                container = new Container();
                container.setId(incomingContainer.getId());
                container.setName(incomingContainer.getName());
                container.setType(incomingContainer.getType());
                containerList.add(container);
            }
        }
        return null;
    }
}
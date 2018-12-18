package org.health.supplychain.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;

import org.health.supplychain.constants.Constants;
import org.health.supplychain.entities.Facility;
import org.health.supplychain.entities.Shipment;
import org.health.supplychain.service.FacilityService;
import org.health.supplychain.service.IFacilityService;
import org.health.supplychain.service.IShipmentTypeService;
import org.health.supplychain.service.ShipmentTypeService;
import org.health.supplychain.webservice.entities.LocationData;
import org.health.supplychain.webservice.entities.LocationGroup;
import org.health.supplychain.webservice.entities.LocationListResponse;
import org.health.supplychain.webservice.entities.ShipmentType;
import org.health.supplychain.webservice.entities.ShipmentTypeResponse;
import org.health.supplychain.webservice.service.APIServices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * Created by Aworkneh on 5/4/2018.
 */

public class ShipmentTypeDownloadTask extends AsyncTask<Void, Void, String> {

    private APIServices apiServices = new APIServices();
    private String sessionId;
    private IShipmentTypeService shipmentTypeService;
    private Context context;

    public ShipmentTypeDownloadTask(Context context, String sessionId){
        this.sessionId = sessionId;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            Response<ShipmentTypeResponse> response = apiServices.getShipmentType(sessionId);
            if(response.isSuccessful()){
                ShipmentTypeResponse shipmentTypeResponseList = response.body();
                List<ShipmentType> incomingShipmentList;
                incomingShipmentList = shipmentTypeResponseList != null? shipmentTypeResponseList.getData():null;
                List<org.health.supplychain.entities.ShipmentType> shipmentTypeList =
                        getMappedShipmentTypeList(incomingShipmentList);

                if(shipmentTypeList != null && !shipmentTypeList.isEmpty()) {
                    boolean isShipmentTypeSaved = saveShipmentTypeList(shipmentTypeList);
//                    super.setSuccess(isCommoditySaved);
                    if(isShipmentTypeSaved)
                        return String.format(Constants.SYNC_SUCCESS_MESSAGE, "shipment type");
                    else
                        return String.format(Constants.SYNC_FAILURE_MESSAGE, "shipment type");
                } else {
                    return String.format(Constants.SYNC_FAILURE_MESSAGE, "shipment type");
                }
            } else {
                return response.errorBody().string();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return String.format(Constants.SYNC_FAILURE_MESSAGE, "shipment type");
        }

    }

    private boolean saveShipmentTypeList(List<org.health.supplychain.entities.ShipmentType> shipmentTypeList) {
        boolean isSaved = true;
        shipmentTypeService = new ShipmentTypeService();
        Facility existingFacility;
        org.health.supplychain.entities.ShipmentType existingShipmentType;
        for(org.health.supplychain.entities.ShipmentType shipmentType: shipmentTypeList){
            existingShipmentType = shipmentTypeService.getShipmentTypeById(shipmentType.getId());
            if(existingShipmentType != null){
//                if(existingLocation.getUpdatedDate() >= commodity.getUpdatedDate()) // && commodity.getUpdatedDate() != 0)
//                    continue;
                isSaved &= shipmentTypeService.updateShipmentType(existingShipmentType, shipmentType);
            } else {
                isSaved &=  shipmentTypeService.saveShipmentType(shipmentType);
            }
        }
        return isSaved;
    }

    private List<org.health.supplychain.entities.ShipmentType> getMappedShipmentTypeList(List<ShipmentType> incomingShipmentList){
        List<org.health.supplychain.entities.ShipmentType> shipmentTypeList;
        if(incomingShipmentList != null){
            shipmentTypeList = new ArrayList<>();
            org.health.supplychain.entities.ShipmentType shipmentType;

            for(ShipmentType shipmentTypeResponse: incomingShipmentList){
                shipmentType = new org.health.supplychain.entities.ShipmentType();
                shipmentType.setId(shipmentTypeResponse.getId());
                shipmentType.setName(shipmentTypeResponse.getName());
                shipmentType.setDescription(shipmentTypeResponse.getDescription());
                shipmentTypeList.add(shipmentType);
            }
            return shipmentTypeList;
        }
        return null;
    }


}
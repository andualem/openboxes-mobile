package org.health.supplychain.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;

import org.health.supplychain.constants.Constants;
import org.health.supplychain.entities.InternalLocation;
import org.health.supplychain.service.InternalLocationService;
import org.health.supplychain.service.IInternalLocationService;
import org.health.supplychain.webservice.entities.LocationData;
import org.health.supplychain.webservice.entities.LocationGroup;
import org.health.supplychain.webservice.entities.LocationListResponse;
import org.health.supplychain.webservice.service.APIServices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * Created by Aworkneh on 5/4/2018.
 */

public class InternalLocationDownloadTask extends AsyncTask<Void, Void, String> {

    private APIServices apiServices = new APIServices();
    private Long officerId, organizationId;
    private String sessionId;
    private String locationId;
    private IInternalLocationService internalLocationService;
    private Context context;

    public InternalLocationDownloadTask(Context context, Long officerId, Long organizationId,
                                        String sessionId, String locationId, ImageView imageView){
        this.officerId = officerId;
        this.organizationId = organizationId;
        this.sessionId = sessionId;
        this.locationId = locationId;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            Response<LocationListResponse> response = apiServices.getInternalLocations(sessionId, locationId);
            if(response.isSuccessful()){
                LocationListResponse locationResponseList = response.body();
                List<LocationData> incomingLocationList;
                incomingLocationList = locationResponseList != null? locationResponseList.getData():null;
                List<InternalLocation> locationList = getMappedLocationList(incomingLocationList);
                if(locationList != null && !locationList.isEmpty()) {
                    boolean isCommoditySaved = saveLocationList(locationList);
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

    private boolean saveLocationList(List<InternalLocation> internalLocationList) {
        boolean isSaved = true;
        internalLocationService = new InternalLocationService();
        InternalLocation existingInternalLocation;
        for(InternalLocation internalLocation: internalLocationList){
            existingInternalLocation = internalLocationService.getInternalLocationById(internalLocation.getId());
            if(existingInternalLocation != null){
//                if(existingLocation.getUpdatedDate() >= commodity.getUpdatedDate()) // && commodity.getUpdatedDate() != 0)
//                    continue;
                isSaved &= internalLocationService.updateInternalLocation(existingInternalLocation, internalLocation);
            } else {
                isSaved &=  internalLocationService.saveInternalLocation(internalLocation);
            }
        }
        return isSaved;
    }

    private List<InternalLocation> getMappedLocationList(List<LocationData> incomingLocationList){
        List<InternalLocation> internalLocationList;
        if(incomingLocationList != null){
            internalLocationList = new ArrayList<>();
            InternalLocation internalLocation;
            LocationData parentLocation;

            for(LocationData locationResponse: incomingLocationList){
                internalLocation = new InternalLocation();
                internalLocation.setId(locationResponse.getId());
                internalLocation.setName(locationResponse.getName());
                internalLocation.setDescription(locationResponse.getDescription());
                parentLocation = locationResponse.getParentLocation();
                if(parentLocation != null)
                    internalLocation.setParentLocationId(parentLocation.getId());
                internalLocation.setLocationNumber(locationResponse.getLocationNumber());
                internalLocation.setLocationGroup(getMappedLocationGroup(locationResponse.getLocationGroup()));
                internalLocation.setLocationTypeCode(locationResponse.getLocationTypeCode());
                internalLocationList.add(internalLocation);
            }
            return internalLocationList;
        }
        return null;
    }

    private org.health.supplychain.entities.LocationGroup getMappedLocationGroup(LocationGroup locationGroupResponse){
        org.health.supplychain.entities.LocationGroup locationGroup = null;
        if(locationGroupResponse != null){
            locationGroup = new org.health.supplychain.entities.LocationGroup();
            locationGroup.setId(locationGroupResponse.getId());
            locationGroup.setName(locationGroupResponse.getName());
        }
        return locationGroup;
    }

}
package org.health.supplychain.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;

import org.health.supplychain.constants.Constants;
import org.health.supplychain.entities.Facility;
import org.health.supplychain.service.FacilityService;
import org.health.supplychain.service.IFacilityService;
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

public class LocationDownloadTask extends AsyncTask<Void, Void, String> {

    private APIServices apiServices = new APIServices();
    private Long officerId, organizationId;
    private String sessionId;
    private IFacilityService facilityService;
    private Context context;

    public LocationDownloadTask(Context context, Long officerId, Long organizationId, String sessionId, ImageView imageView){
        this.officerId = officerId;
        this.organizationId = organizationId;
        this.sessionId = sessionId;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            Response<LocationListResponse> response = apiServices.getLocations(sessionId);
            if(response.isSuccessful()){
                LocationListResponse locationResponseList = response.body();
                List<org.health.supplychain.webservice.entities.LocationData> incomingLocationList;
                incomingLocationList = locationResponseList != null? locationResponseList.getData():null;
                List<Facility> locationList = getMappedLocationList(incomingLocationList);
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

    private boolean saveLocationList(List<Facility> facilityList) {
        boolean isSaved = true;
        facilityService = new FacilityService();
        Facility existingFacility;
        for(Facility facility: facilityList){
            existingFacility = facilityService.getFacilityById(facility.getId());
            if(existingFacility != null){
//                if(existingLocation.getUpdatedDate() >= commodity.getUpdatedDate()) // && commodity.getUpdatedDate() != 0)
//                    continue;
                isSaved &= facilityService.updateFacility(existingFacility, facility);
            } else {
                isSaved &=  facilityService.saveFacility(facility);
            }
        }
        return isSaved;
    }

    private List<Facility> getMappedLocationList(List<org.health.supplychain.webservice.entities.LocationData> incomingLocationList){
        List<Facility> facilityList;
        if(incomingLocationList != null){
            facilityList = new ArrayList<>();
            Facility facility;
            LocationData parentLocation;

            for(org.health.supplychain.webservice.entities.LocationData locationResponse: incomingLocationList){
                facility = new Facility();
                facility.setId(locationResponse.getId());
                facility.setName(locationResponse.getName());
                facility.setDescription(locationResponse.getDescription());
                parentLocation = locationResponse.getParentLocation();
                if(parentLocation != null)
                    facility.setParentLocationId(parentLocation.getId());
                facility.setLocationNumber(locationResponse.getLocationNumber());
                facility.setLocationGroup(getMappedLocationGroup(locationResponse.getLocationGroup()));
                facility.setLocationTypeCode(locationResponse.getLocationTypeCode());
                facilityList.add(facility);
            }
            return facilityList;
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
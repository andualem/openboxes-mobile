package org.health.supplychain.webservice.entities;

/**
 * Created by Aworkneh on 8/27/2018.
 */

public class LocationData {

    private String id;
    private String name;
    private String description;
    private String locationNumber;
    private LocationGroup locationGroup;
    private LocationData parentLocation;
    private LocationType locationType;
    private String locationTypeCode;

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return this.description; }

    public void setDescription(String description) { this.description = description; }

    public String getLocationNumber() { return this.locationNumber; }

    public void setLocationNumber(String locationNumber) { this.locationNumber = locationNumber; }

    public LocationGroup getLocationGroup() {
        return locationGroup;
    }

    public void setLocationGroup(LocationGroup locationGroup) {
        this.locationGroup = locationGroup;
    }

    public LocationData getParentLocation() {
        return parentLocation;
    }

    public void setParentLocation(LocationData parentLocation) {
        this.parentLocation = parentLocation;
    }

    public LocationType getLocationType() { return this.locationType; }

    public void setLocationType(LocationType locationType) { this.locationType = locationType; }

    public String getLocationTypeCode() { return this.locationTypeCode; }

    public void setLocationTypeCode(String locationTypeCode) { this.locationTypeCode = locationTypeCode; }
}

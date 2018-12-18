package org.health.supplychain.webservice.entities;

/**
 * Created by Aworkneh on 8/27/2018.
 */

public class ParentLocation {
    private String id;
    private String name;
    private String description;
    private String locationNumber;
    private String locationGroup;
    private String parentLocation;
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

    public String getLocationGroup() { return this.locationGroup; }

    public void setLocationGroup(String locationGroup) { this.locationGroup = locationGroup; }

    public String getParentLocation() { return this.parentLocation; }

    public void setParentLocation(String parentLocation) { this.parentLocation = parentLocation; }

    public LocationType getLocationType() { return this.locationType; }

    public void setLocationType(LocationType locationType) { this.locationType = locationType; }

    public String getLocationTypeCode() { return this.locationTypeCode; }

    public void setLocationTypeCode(String locationTypeCode) { this.locationTypeCode = locationTypeCode; }
}

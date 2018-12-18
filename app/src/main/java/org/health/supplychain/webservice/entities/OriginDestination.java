package org.health.supplychain.webservice.entities;

public class OriginDestination {
    private String id;
    private String name;
    private String type;

    private String description;

    private String locationNumber;

    private LocationGroup locationGroup;

    private String parentLocation;

    private LocationType locationType;

    private int sortOrder;

    private boolean hasBinLocationSupport;

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocationNumber() {
        return locationNumber;
    }

    public void setLocationNumber(String locationNumber) {
        this.locationNumber = locationNumber;
    }

    public LocationGroup getLocationGroup() {
        return locationGroup;
    }

    public void setLocationGroup(LocationGroup locationGroup) {
        this.locationGroup = locationGroup;
    }

    public String getParentLocation() {
        return parentLocation;
    }

    public void setParentLocation(String parentLocation) {
        this.parentLocation = parentLocation;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public boolean isHasBinLocationSupport() {
        return hasBinLocationSupport;
    }

    public void setHasBinLocationSupport(boolean hasBinLocationSupport) {
        this.hasBinLocationSupport = hasBinLocationSupport;
    }
}

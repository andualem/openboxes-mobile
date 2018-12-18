package org.health.supplychain.webservice.entities;

/**
 * Created by Aworkneh on 8/27/2018.
 */

public class LocationType {
    private String id;
    private String name;
    private String description;
    private String locationTypeCode;

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return this.description; }

    public void setDescription(String description) { this.description = description; }

    public String getLocationTypeCode() { return this.locationTypeCode; }

    public void setLocationTypeCode(String locationTypeCode) { this.locationTypeCode = locationTypeCode; }
}

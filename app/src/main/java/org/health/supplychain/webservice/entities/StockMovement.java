package org.health.supplychain.webservice.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StockMovement {
    private String id;
    private String name;
    private String description;
    private String identifier;
    private OriginDestination origin;
    private OriginDestination destination;
    private String dateRequested;
    private Requester requestedBy;
    private List<LineItem> lineItems;

    @SerializedName("stocklist.id")
    private String stocklistId;

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return this.description; }

    public void setDescription(String description) { this.description = description; }

    public String getIdentifier() { return this.identifier; }

    public void setIdentifier(String identifier) { this.identifier = identifier; }

    public OriginDestination getOrigin() { return this.origin; }

    public void setOrigin(OriginDestination origin) { this.origin = origin; }

    public OriginDestination getOriginDestination() { return this.destination; }

    public void setOriginDestination(OriginDestination destination) { this.destination = destination; }

    public String getDateRequested() { return this.dateRequested; }

    public void setDateRequested(String dateRequested) { this.dateRequested = dateRequested; }

    public Requester getRequestedBy() { return this.requestedBy; }

    public void setRequestedBy(Requester requestedBy) { this.requestedBy = requestedBy; }

    public OriginDestination getDestination() {
        return destination;
    }

    public void setDestination(OriginDestination destination) {
        this.destination = destination;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public String getStocklistId() {
        return stocklistId;
    }

    public void setStocklistId(String stocklistId) {
        this.stocklistId = stocklistId;
    }
}

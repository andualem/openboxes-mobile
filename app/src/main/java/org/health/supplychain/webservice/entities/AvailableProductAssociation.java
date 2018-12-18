package org.health.supplychain.webservice.entities;

import java.util.List;

public class AvailableProductAssociation {
    private Product product;
    private List<AvailableItem> availableItems;
    private boolean hasAssociations;
    private boolean hasEarlierExpiringItems;
    private List<ProductAssociation> productAssociations;

    public Product getProduct() { return this.product; }

    public void setProduct(Product product) { this.product = product; }

    public List<AvailableItem> getAvailableItems() { return this.availableItems; }

    public void setAvailableItems(List<AvailableItem> availableItems) { this.availableItems = availableItems; }

    public boolean getHasAssociations() { return this.hasAssociations; }

    public void setHasAssociations(boolean hasAssociations) { this.hasAssociations = hasAssociations; }

    public boolean getHasEarlierExpiringItems() { return this.hasEarlierExpiringItems; }

    public void setHasEarlierExpiringItems(boolean hasEarlierExpiringItems) { this.hasEarlierExpiringItems = hasEarlierExpiringItems; }

    public List<ProductAssociation> getProductAssociations() { return this.productAssociations; }

    public void setProductAssociations(List<ProductAssociation> productAssociations) { this.productAssociations = productAssociations; }
}

package org.health.supplychain.webservice.entities;

import java.util.List;

public class LineItem {
    private String id;
    private String productCode;
    private Product product;
    private String palletName;
    private String boxName;
    private String statusCode;
    private String quantityRequested;
    private String quantityAllowed;
    private String quantityAvailable;
    private String quantityCanceled;
    private String quantityRevised;
    private List<LineItem> substitutions;
    private String reasonCode;
    private String comments;
    private String recipient;
    private Double sortOrder;

    //new field for substitute;
    private Product newProduct;
    private String substitute; //true or false
    private int newQuantity;
    private String revert; //true or false
    private String cancel; //true or false
    private String delete; //true or false


    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    public String getProductCode() { return this.productCode; }

    public void setProductCode(String productCode) { this.productCode = productCode; }

    public Product getProduct() { return this.product; }

    public void setProduct(Product product) { this.product = product; }

    public String getPalletName() { return this.palletName; }

    public void setPalletName(String palletName) { this.palletName = palletName; }

    public String getBoxName() { return this.boxName; }

    public void setBoxName(String boxName) { this.boxName = boxName; }

    public String getStatusCode() { return this.statusCode; }

    public void setStatusCode(String statusCode) { this.statusCode = statusCode; }

    public String getQuantityRequested() { return this.quantityRequested; }

    public void setQuantityRequested(String quantityRequested) { this.quantityRequested = quantityRequested; }

    public String getQuantityAllowed() { return this.quantityAllowed; }

    public void setQuantityAllowed(String quantityAllowed) { this.quantityAllowed = quantityAllowed; }

    public String getQuantityAvailable() { return this.quantityAvailable; }

    public void setQuantityAvailable(String quantityAvailable) { this.quantityAvailable = quantityAvailable; }

    public String getQuantityCanceled() { return this.quantityCanceled; }

    public void setQuantityCanceled(String quantityCanceled) { this.quantityCanceled = quantityCanceled; }

    public String getQuantityRevised() { return this.quantityRevised; }

    public void setQuantityRevised(String quantityRevised) { this.quantityRevised = quantityRevised; }

    public List<LineItem> getSubstitutions() { return this.substitutions; }

    public void setSubstitutions(List<LineItem> substitutions) { this.substitutions = substitutions; }

    public String getReasonCode() { return this.reasonCode; }

    public void setReasonCode(String reasonCode) { this.reasonCode = reasonCode; }

    public String getComments() { return this.comments; }

    public void setComments(String comments) { this.comments = comments; }

    public String getRecipient() { return this.recipient; }

    public void setRecipient(String recipient) { this.recipient = recipient; }

    public Double getSortOrder() { return this.sortOrder; }

    public void setSortOrder(Double sortOrder) { this.sortOrder = sortOrder; }

    public Product getNewProduct() {
        return newProduct;
    }

    public void setNewProduct(Product newProduct) {
        this.newProduct = newProduct;
    }

    public String getSubstitute() {
        return substitute;
    }

    public void setSubstitute(String substitute) {
        this.substitute = substitute;
    }

    public int getNewQuantity() {
        return newQuantity;
    }

    public void setNewQuantity(int newQuantity) {
        this.newQuantity = newQuantity;
    }
}

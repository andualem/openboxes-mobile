package org.health.supplychain.entities;

import com.google.gson.annotations.SerializedName;


import java.util.List;

import io.realm.RealmObject;

public class RequisitionItem extends RealmObject {
    private String id;

    private String status;

    @SerializedName("requisition.id")
    private String requisitionId;

    private Product product;

//    private InventoryItem inventoryItem;

    private double quantity;

    private String quantityApproved;

    private String quantityCanceled;

    private String cancelReasonCode;

    private String cancelComments;

    private int orderIndex;

//    private List<String> changes;

    private String modification;

    private String substitution;

//    private List<PicklistItems> picklistItems;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(String requisitionId) {
        this.requisitionId = requisitionId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getQuantityApproved() {
        return quantityApproved;
    }

    public void setQuantityApproved(String quantityApproved) {
        this.quantityApproved = quantityApproved;
    }

    public String getQuantityCanceled() {
        return quantityCanceled;
    }

    public void setQuantityCanceled(String quantityCanceled) {
        this.quantityCanceled = quantityCanceled;
    }

    public String getCancelReasonCode() {
        return cancelReasonCode;
    }

    public void setCancelReasonCode(String cancelReasonCode) {
        this.cancelReasonCode = cancelReasonCode;
    }

    public String getCancelComments() {
        return cancelComments;
    }

    public void setCancelComments(String cancelComments) {
        this.cancelComments = cancelComments;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

//    public List<String> getChanges() {
//        return changes;
//    }
//
//    public void setChanges(List<String> changes) {
//        this.changes = changes;
//    }

    public String getModification() {
        return modification;
    }

    public void setModification(String modification) {
        this.modification = modification;
    }

    public String getSubstitution() {
        return substitution;
    }

    public void setSubstitution(String substitution) {
        this.substitution = substitution;
    }


}

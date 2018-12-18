package org.health.supplychain.entities;

import org.health.supplychain.webservice.entities.OriginDestination;
import org.health.supplychain.webservice.entities.Requester;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class Requisition extends RealmObject {

    private String id;

    private String name;

    private String requisitionNumber;

    private String description;

    private boolean isTemplate;

    private String type;

    private String status;

    private String commodityClass;

    private String dateRequested;

    private String dateReviewed;

    private String dateVerified;

    private String dateChecked;

    private String dateDelivered;

    private String dateIssued;

    private String dateReceived;

    private Facility origin;

    private Facility destination;

    private String requestedBy;

    private String reviewedBy;

    private String verifiedBy;

    private String checkedBy;

    private String deliveredBy;

    private String issuedBy;

    private String receivedBy;

    private String recipient;

    private RealmList<RequisitionItem> requisitionItems;

    @Ignore
    private List<RequisitionItem> UnmanagedRequisitionItemList;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequisitionNumber() {
        return requisitionNumber;
    }

    public void setRequisitionNumber(String requisitionNumber) {
        this.requisitionNumber = requisitionNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isTemplate() {
        return isTemplate;
    }

    public void setTemplate(boolean template) {
        isTemplate = template;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCommodityClass() {
        return commodityClass;
    }

    public void setCommodityClass(String commodityClass) {
        this.commodityClass = commodityClass;
    }

    public String getDateRequested() {
        return dateRequested;
    }

    public void setDateRequested(String dateRequested) {
        this.dateRequested = dateRequested;
    }

    public String getDateReviewed() {
        return dateReviewed;
    }

    public void setDateReviewed(String dateReviewed) {
        this.dateReviewed = dateReviewed;
    }

    public String getDateVerified() {
        return dateVerified;
    }

    public void setDateVerified(String dateVerified) {
        this.dateVerified = dateVerified;
    }

    public String getDateChecked() {
        return dateChecked;
    }

    public void setDateChecked(String dateChecked) {
        this.dateChecked = dateChecked;
    }

    public String getDateDelivered() {
        return dateDelivered;
    }

    public void setDateDelivered(String dateDelivered) {
        this.dateDelivered = dateDelivered;
    }

    public String getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(String dateIssued) {
        this.dateIssued = dateIssued;
    }

    public String getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(String dateReceived) {
        this.dateReceived = dateReceived;
    }

    public Facility getOrigin() {
        return origin;
    }

    public void setOrigin(Facility origin) {
        this.origin = origin;
    }

    public Facility getDestination() {
        return destination;
    }

    public void setDestination(Facility destination) {
        this.destination = destination;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(String reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public String getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(String verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public String getCheckedBy() {
        return checkedBy;
    }

    public void setCheckedBy(String checkedBy) {
        this.checkedBy = checkedBy;
    }

    public String getDeliveredBy() {
        return deliveredBy;
    }

    public void setDeliveredBy(String deliveredBy) {
        this.deliveredBy = deliveredBy;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public RealmList<RequisitionItem> getRequisitionItems() {
        return requisitionItems;
    }

    public void setRequisitionItems(RealmList<RequisitionItem> requisitionItems) {
        this.requisitionItems = requisitionItems;
    }


    public List<RequisitionItem> getUnmanagedRequisitionItemList() {
        return UnmanagedRequisitionItemList;
    }

    public void setUnmanagedRequisitionItemList(List<RequisitionItem> unmanagedRequisitionItemList) {
        UnmanagedRequisitionItemList = unmanagedRequisitionItemList;
    }
}

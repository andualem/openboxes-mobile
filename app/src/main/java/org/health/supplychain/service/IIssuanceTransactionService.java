package org.health.supplychain.service;

import org.health.supplychain.entities.Facility;
import org.health.supplychain.entities.IssueTransaction;
import org.health.supplychain.entities.ProductQuantity;
import org.health.supplychain.entities.RequestTransaction;
import org.health.supplychain.entities.ShipmentType;

import java.util.List;

public interface IIssuanceTransactionService {

    public boolean saveIssuance(Facility receivingFacility, String currentLocationId, List<ProductQuantity> productQuantityList,
                                ShipmentType shipmentType, int transactionType);

    public boolean saveIssuanceForRequest(Facility requestingFacility, String currentLocationId, RequestTransaction requestTransaction,
                                          List<ProductQuantity> productQuantityList, ShipmentType shipmentType, int transactionType);


    public IssueTransaction getIssueTransactionByUuid(String uuid);

    public List<IssueTransaction> getUnsyncedIssueTransaction(String currentLocationId);
}

package org.health.supplychain.service;

import org.health.supplychain.entities.Facility;
import org.health.supplychain.entities.ProductQuantity;
import org.health.supplychain.entities.ReceiveTransaction;
import org.health.supplychain.entities.RequestTransaction;

import java.util.List;

public interface IReceiveTransactionService {

    public boolean saveReceiveTransaction(Facility issuingFacility, String currentLocationId,
                                          List<ProductQuantity> productQuantityList,
                                          int transactionType);

    public boolean saveReceiveForRequestTransaction(Facility issuingFacility, String currentLocationId,
                                                    RequestTransaction requestTransaction,
                                                    List<ProductQuantity> productQuantityList,
                                                    int transactionType);
}

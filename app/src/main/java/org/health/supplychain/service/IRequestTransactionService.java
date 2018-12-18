package org.health.supplychain.service;

import org.health.supplychain.entities.Facility;
import org.health.supplychain.entities.ProductQuantity;
import org.health.supplychain.entities.RequestTransaction;
import org.health.supplychain.security.entities.UserSession;

import java.util.List;

public interface IRequestTransactionService {

    public boolean saveRequest(Facility requestedFacility, UserSession userSession, List<ProductQuantity> productQuantityList);

    public RequestTransaction getRequestTransactionByUuid(String requestUuid);

    public List<RequestTransaction> getUnsyncedRequestTransaction(String currentLocationId);
}

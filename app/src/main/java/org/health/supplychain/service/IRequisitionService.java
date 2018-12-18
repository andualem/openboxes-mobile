package org.health.supplychain.service;

import org.health.supplychain.entities.Requisition;
import org.health.supplychain.entities.RequisitionItem;

import java.util.List;

import io.realm.Realm;

public interface IRequisitionService {

    public boolean saveRequisition(Requisition requisition);

    public Requisition getRequisitionById(String id);

    public boolean updateRequisition(Requisition existing, Requisition incomming);
}

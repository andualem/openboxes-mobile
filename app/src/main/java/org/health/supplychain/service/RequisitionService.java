package org.health.supplychain.service;

import org.health.supplychain.entities.Requisition;
import org.health.supplychain.entities.RequisitionItem;

import java.util.List;

import io.realm.Realm;

public class RequisitionService implements IRequisitionService{

    @Override
    public boolean saveRequisition(Requisition requisition) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        final Requisition savedRequisition = realm.copyToRealm(requisition);

        final List<RequisitionItem> managedRequisitionItemList
                = realm.copyToRealm(requisition.getUnmanagedRequisitionItemList());



        savedRequisition.getRequisitionItems().addAll(managedRequisitionItemList);

        realm.commitTransaction();
        return savedRequisition.isManaged();
    }

    @Override
    public Requisition getRequisitionById(String id) {
        return null;
    }

    @Override
    public boolean updateRequisition(Requisition existing, Requisition incomming) {
        return false;
    }
}

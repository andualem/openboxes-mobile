package org.health.supplychain.service;

import org.health.supplychain.entities.AvailableItem;
import org.health.supplychain.entities.LocationGroup;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class AvailableItemService implements IAvailableItemService {
    @Override
    public List<AvailableItem> getAllFacilities() {

        Realm realm = Realm.getDefaultInstance();
        RealmResults<AvailableItem> availableItems = realm.where(AvailableItem.class).findAll();
        if(availableItems != null && availableItems.size() > 0)
            return availableItems.subList(0, availableItems.size());
        else
            return null;
    }

    @Override
    public boolean saveAvailableItem(AvailableItem availableItem) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        final AvailableItem savedAvailableItem = realm.copyToRealm(availableItem);
        realm.commitTransaction();

        return savedAvailableItem != null;
    }

    @Override
    public AvailableItem getAvailableItemById(String id) {
        Realm realm = Realm.getDefaultInstance();
        AvailableItem availableItem = realm.where(AvailableItem.class).equalTo("inventoryItemId", id).findFirst();
        return availableItem;
    }

    @Override
    public List<AvailableItem> getAvailableItemListByProductCode(String productCode) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<AvailableItem> availableItems = realm.where(AvailableItem.class)
                .equalTo("productCode", productCode)
                .findAll();
        if(availableItems != null && availableItems.size() > 0)
            return availableItems.subList(0, availableItems.size());
        else
            return null;
    }

    @Override
    public boolean updateAvailableItem(AvailableItem exsitingAvailableItem, AvailableItem incomingAvailableItem) {
        return false;
    }
}

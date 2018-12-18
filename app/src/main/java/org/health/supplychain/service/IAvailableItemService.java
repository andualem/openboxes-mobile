package org.health.supplychain.service;

import org.health.supplychain.entities.AvailableItem;

import java.util.List;

public interface IAvailableItemService {

    public List<AvailableItem> getAllFacilities();

    public boolean saveAvailableItem(AvailableItem availableItem);

    public AvailableItem getAvailableItemById(String id);

    public List<AvailableItem> getAvailableItemListByProductCode(String productCode);

    public boolean updateAvailableItem(AvailableItem exsitingAvailableItem, AvailableItem incomingAvailableItem);

}

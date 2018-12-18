package org.health.supplychain.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import org.health.supplychain.R;
import org.health.supplychain.constants.Constants;
import org.health.supplychain.entities.Product;
import org.health.supplychain.service.AvailableItemService;
import org.health.supplychain.service.IAvailableItemService;
import org.health.supplychain.service.IProductService;
import org.health.supplychain.service.ProductService;
import org.health.supplychain.webservice.entities.AvailableItem;
import org.health.supplychain.webservice.entities.AvailableItemListResponse;
import org.health.supplychain.webservice.entities.BinLocation;
import org.health.supplychain.webservice.entities.ProductCategory;
import org.health.supplychain.webservice.entities.ProductListResponse;
import org.health.supplychain.webservice.service.APIServices;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import retrofit2.Response;

/**
 * Created by Aworkneh on 5/4/2018.
 */

public class InventoryItemDownloadTask extends AsyncTask<Void, Void, String> {

    private APIServices apiServices = new APIServices();
    private String locationId, productId, productCode;
    private String sessionId;
    private IAvailableItemService availableItemService;
    private Context context;
    private boolean hasSearchParameter = false;
    String id, name;
    Integer offset, max;
    private List<org.health.supplychain.entities.AvailableItem> availableItemList;
    private Spinner spAvailableItem;

    public InventoryItemDownloadTask(Context context, String sessionId, String locationId,
                                     String productId, String productCode, Spinner spAvailableItem){
        this.locationId = locationId;
        this.productId = productId;
        this.productCode = productCode;
        this.sessionId = sessionId;
        this.context = context;
        this.spAvailableItem = spAvailableItem;
    }


    @Override
    protected String doInBackground(Void... voids) {
        try {
            Response<AvailableItemListResponse> response = apiServices.getInventoryItems(sessionId, productId, locationId);

            if(response.isSuccessful()){
                AvailableItemListResponse availableItemsListResponse = response.body();
                List<AvailableItem> incomingAvailableItemsList;
                incomingAvailableItemsList = availableItemsListResponse != null? availableItemsListResponse.getData():null;
                List<org.health.supplychain.entities.AvailableItem> availableItemList = getMappedAvailableItemtList(incomingAvailableItemsList);
                if(availableItemList != null && !availableItemList.isEmpty()) {
                    boolean isCommoditySaved = saveAvailableItemList(availableItemList);
//                    super.setSuccess(isCommoditySaved);
                    if(isCommoditySaved)
                        return String.format(Constants.SYNC_SUCCESS_MESSAGE, "input");
                    else
                        return String.format(Constants.SYNC_FAILURE_MESSAGE, "input");
                } else {
                    return String.format(Constants.SYNC_FAILURE_MESSAGE, "input");
                }
            } else {
                return response.errorBody().string();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return String.format(Constants.SYNC_FAILURE_MESSAGE, "input");
        }

    }

    private boolean saveAvailableItemList(List<org.health.supplychain.entities.AvailableItem> availableItemList) {
        boolean isSaved = true;
        availableItemService = new AvailableItemService();
        org.health.supplychain.entities.AvailableItem existingAvailableItem;
        for(org.health.supplychain.entities.AvailableItem availableItem: availableItemList){
            existingAvailableItem = availableItemService.getAvailableItemById(availableItem.getInventoryItemId());
            if(existingAvailableItem != null){
//                if(existingProduct.getUpdatedDate() >= commodity.getUpdatedDate()) // && commodity.getUpdatedDate() != 0)
//                    continue;
                isSaved &= availableItemService.updateAvailableItem(existingAvailableItem, availableItem);
            } else {
                isSaved &=  availableItemService.saveAvailableItem(availableItem);
            }
        }
        return isSaved;
    }

    private List<org.health.supplychain.entities.AvailableItem> getMappedAvailableItemtList(List<AvailableItem> incomingAvailableItemsList){
        List<org.health.supplychain.entities.AvailableItem> availableItemList;
        if(incomingAvailableItemsList != null){
            availableItemList = new ArrayList<>();
            org.health.supplychain.entities.AvailableItem availableItem;

            for(AvailableItem incomingAvailableItem: incomingAvailableItemsList){
                availableItem = new org.health.supplychain.entities.AvailableItem();
                availableItem.setInventoryItemId(incomingAvailableItem.getInventoryItemId());
                availableItem.setProductCode(incomingAvailableItem.getProductCode());
                availableItem.setProductName(incomingAvailableItem.getProductName());
                availableItem.setLotNumber(incomingAvailableItem.getLotNumber());
                availableItem.setExpirationDate(incomingAvailableItem.getExpirationDate());
                availableItem.setBinLocationId(incomingAvailableItem.getBinLocationId());
                availableItem.setBinLocationName(incomingAvailableItem.getBinLocationName());
                availableItem.setQuantityAvailable(incomingAvailableItem.getQuantityAvailable());
                availableItemList.add(availableItem);
            }
            return availableItemList;
        }
        return null;
    }


    public List<org.health.supplychain.entities.AvailableItem> getAvailableItemList() {
        return availableItemList;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

//        System.out.println("Inventory Download Task - s = " + s);
//        if(s.contains(Constants.SYNC_SUCCESS_MESSAGE)){
            availableItemService = new AvailableItemService();
            availableItemList =
                    availableItemService.getAvailableItemListByProductCode(productCode);
            System.out.println("Inventory Download Task - onPostExecute");
            populateAvailableItemList();
//        }
    }







    private String BIN_LOCATION = "BIN_LOCATION", LOT_NUMBER = "LOT_NUMBER", QUANTITY = "QUANTITY", EXPIRY_DATE = "EXPIRY_DATE";
    private List<WeakHashMap<String, String>> availabelItemDetailList = new ArrayList<WeakHashMap<String,String>>();
    private String[] availableItemDetailContent = new String[] {BIN_LOCATION, QUANTITY, LOT_NUMBER, EXPIRY_DATE};
    private int[] availableItemView = new int[] {R.id.title, R.id.quantity, R.id.subtitle, R.id.moreDetail};
    private void populateAvailableItemList(){

        getMappedAvailableItemList();
        SimpleAdapter simpleAdapter = new SimpleAdapter(context,
                availabelItemDetailList, R.layout.four_item_witioutshadow_list, availableItemDetailContent, availableItemView);
        spAvailableItem.setAdapter(simpleAdapter);
    }


    private void getMappedAvailableItemList(){


        if(availableItemList != null){
            System.out.println("Inventory Download Task - getMappedAvailableItemList");
            availabelItemDetailList = new ArrayList<>();
            WeakHashMap<String, String> availabelItemDetail;
            for(org.health.supplychain.entities.AvailableItem availableItem: availableItemList){
                availabelItemDetail = new WeakHashMap<>();
                availabelItemDetail.put(BIN_LOCATION, availableItem.getBinLocationName());
                availabelItemDetail.put(LOT_NUMBER, availableItem.getLotNumber());
                availabelItemDetail.put(QUANTITY, String.valueOf(availableItem.getQuantityAvailable()));
                availabelItemDetail.put(EXPIRY_DATE, availableItem.getExpirationDate());
                availabelItemDetailList.add(availabelItemDetail);
            }
        }

    }
}
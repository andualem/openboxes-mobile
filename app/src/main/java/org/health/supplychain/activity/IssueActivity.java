package org.health.supplychain.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import org.health.supplychain.R;
import org.health.supplychain.constants.Constants;
import org.health.supplychain.customexception.ValidatorException;
import org.health.supplychain.entities.AvailableItem;
import org.health.supplychain.entities.Facility;
import org.health.supplychain.entities.Product;
import org.health.supplychain.entities.ProductQuantity;
import org.health.supplychain.entities.ShipmentType;
import org.health.supplychain.security.entities.UserSession;
import org.health.supplychain.security.service.IUserSessionService;
import org.health.supplychain.security.service.UserSessionService;
import org.health.supplychain.service.AvailableItemService;
import org.health.supplychain.service.FacilityService;
import org.health.supplychain.service.IAvailableItemService;
import org.health.supplychain.service.IFacilityService;
import org.health.supplychain.service.IIssuanceTransactionService;
import org.health.supplychain.service.IProductService;
import org.health.supplychain.service.IShipmentTypeService;
import org.health.supplychain.service.IssuanceTransactionService;
import org.health.supplychain.service.ProductService;
import org.health.supplychain.service.ShipmentTypeService;
import org.health.supplychain.tasks.InventoryItemDownloadTask;
import org.health.supplychain.validator.PositiveDecimalNumberValidator;
import org.health.supplychain.view.MessageDialogView;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import io.realm.Realm;

public class IssueActivity extends AppCompatActivity{

    private Spinner spReceivingFacilities;
    private Spinner spShipmentType;
    private ListView lvIssuedProductList;
    private Spinner spProduct;
    private Spinner spAvailableItem;
    private EditText etIssuedQuantity;
    private EditText etIssueRemark;
    private Button btnAddItem;
    private Button btnSaveIssuance;
    ArrayAdapter<String> dataAdapter;

    private Facility selectedFacility;
    private Product selectedProduct;
    private ShipmentType selectedShipmentType;
    private double quantity;
    private String remark;
    private List<ProductQuantity> productQuantityList;



    private IFacilityService facilityService;
    private IProductService productService;
    private IShipmentTypeService shipmentTypeService;
    private List<Product> productList;
    private List<String> productNameList;
    private List<Facility> facilityList;
    private List<ShipmentType> shipmentTypeList;
    private List<String> facilityNameList;
    private List<String> shipmentTypeNameList;

    private IIssuanceTransactionService issuanceTransactionService;
    private InventoryItemDownloadTask inventoryItemDownloadTask;
    private IUserSessionService userSessionService;
    private AvailableItem selectedAvailableItem;
    private List<AvailableItem> availableItemList;
    private Number currentIdNum;
    private long productQuantityId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.issue_activity_layout);
        spReceivingFacilities = findViewById(R.id.spReceivingFacilities);
        spShipmentType = findViewById(R.id.spShipmentType);
        spAvailableItem = findViewById(R.id.spAvailableItem);
        lvIssuedProductList = findViewById(R.id.lvIssuedProductList);
        spProduct = findViewById(R.id.spProduct);
        etIssuedQuantity = findViewById(R.id.etIssuedQuantity);
        etIssueRemark = findViewById(R.id.etIssueRemark);
        btnAddItem = findViewById(R.id.btnAddItem);
        btnSaveIssuance = findViewById(R.id.btnSaveIssuance);
        productQuantityList = new ArrayList<>();

        getFacilityList();
        if(facilityNameList != null) {
            dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_layout, R.id.text, facilityNameList);
            spReceivingFacilities.setAdapter(dataAdapter);
        }

        getShipmentTypeList();
        if(shipmentTypeNameList != null) {
            dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_layout, R.id.text, shipmentTypeNameList);
            spShipmentType.setAdapter(dataAdapter);
        }

        getProductList();
        if(productNameList != null) {
            dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_layout, R.id.text, productNameList);
            spProduct.setAdapter(dataAdapter);
        }

        spProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedProduct = productList.get(i);
                String locationId , sessionId;
                UserSession userSession;

                userSessionService = new UserSessionService();
                userSession = userSessionService.getUserSessionDetail(IssueActivity.this);
                if(userSession != null) {
                    //download inventory item for selected product
                    System.out.println("Selected Product Code = " + selectedProduct.getCode());
                    inventoryItemDownloadTask = new InventoryItemDownloadTask(IssueActivity.this,
                            userSession.getSessionId(), userSession.getLocationId(),
                            selectedProduct.getProductId(), selectedProduct.getCode(),  spAvailableItem);
                    inventoryItemDownloadTask.execute();

//                    availableItemList = inventoryItemDownloadTask.getAvailableItemList();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProductDetail();
                cleanForm();
                updateIssuedProductList();
            }
        });


        btnSaveIssuance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isSaved = saveIssuanceTransaction();
                MessageDialogView messageDialogView = new MessageDialogView(IssueActivity.this);
                messageDialogView.showMessage(getString(R.string.success),
                        getString(R.string.saved_successfully),
                        Constants.MESSAGE_TYPE_SUCCESS);
            }
        });

        getLastProductQuantityId();
    }


    private void getLastProductQuantityId() {
        Realm realm = Realm.getDefaultInstance();
        currentIdNum = realm.where(ProductQuantity.class).max("id");
        productQuantityId = ((currentIdNum == null)? 0L: currentIdNum.longValue()) + 1;
    }

    private void getFacilityList(){
        userSessionService = new UserSessionService();
        UserSession userSession = userSessionService.getUserSessionDetail(IssueActivity.this);

        facilityService = new FacilityService();
        facilityList = facilityService.getReceivingFacilities(userSession.getLocationId());
        if(facilityList != null && !facilityList.isEmpty()){
            facilityNameList = new ArrayList<>();
            for(Facility facility: facilityList){
                facilityNameList.add(facility.getName());
            }

        }
    }


    private void getShipmentTypeList() {
        shipmentTypeService = new ShipmentTypeService();
        shipmentTypeList = shipmentTypeService.getAllShipmentType();

        if(shipmentTypeList != null && !shipmentTypeList.isEmpty()){
            shipmentTypeNameList = new ArrayList<>();
            for(ShipmentType shipmentType: shipmentTypeList){
                shipmentTypeNameList.add(shipmentType.getName());
            }
        }
    }

    private void getProductList(){
        productService = new ProductService();
        productList = productService.getAllProduct();
        if(productList != null && !productList.isEmpty()){
            productNameList = new ArrayList<>();
            for(Product product: productList){
                productNameList.add(product.getName());
            }
        }
    }

    private void addProductDetail(){

        if(!validateForm())
            return;

        selectedProduct = productList.get((int) spProduct.getSelectedItemId());
        quantity = Double.valueOf(etIssuedQuantity.getText().toString());
        remark = etIssueRemark.getText().toString();

        ProductQuantity productQuantity = new ProductQuantity();
        productQuantity.setId(productQuantityId++);
        productQuantity.setIssuedQuantity(quantity);
        productQuantity.setProduct(selectedProduct);
        productQuantity.setAvailableItem(selectedAvailableItem);
        productQuantity.setRemark(remark);
        productQuantityList.add(productQuantity);
    }

    private void cleanForm(){
        etIssuedQuantity.setText("");
        etIssueRemark.setText("");
        //clean the old list
        productDetailList.clear();
    }



    private void updateIssuedProductList(){
        getListOfProductDetail();
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,
                productDetailList, R.layout.two_item_list, productDetailContent, view);
        lvIssuedProductList.setAdapter(simpleAdapter);
    }

    private List<WeakHashMap<String, String>> productDetailList = new ArrayList<WeakHashMap<String,String>>();
    private String[] productDetailContent = new String[] {"title", "category"};
    private int[] view = new int[] {R.id.title,
            R.id.subtitle,};

    private void getListOfProductDetail(){
        WeakHashMap<String, String> producteDetail;

        for(ProductQuantity productQuantity: productQuantityList){
            producteDetail = new WeakHashMap<String, String>();
            producteDetail.put("title", productQuantity.getProduct().getName());
            producteDetail.put("category", productQuantity.getIssuedQuantity() +
                    getString(R.string.tab) +
                    productQuantity.getProduct().getMeasurementUnit());
            productDetailList.add(producteDetail);
        }

    }

    private boolean saveIssuanceTransaction(){
        if(productQuantityList == null
                || productQuantityList.isEmpty())
            return false;

        selectedFacility = facilityList.get(spReceivingFacilities.getSelectedItemPosition());
        selectedShipmentType = shipmentTypeList.get(spShipmentType.getSelectedItemPosition());
        UserSession userSession = userSessionService.getUserSessionDetail(IssueActivity.this);

        issuanceTransactionService = new IssuanceTransactionService();
        return  issuanceTransactionService.saveIssuance(selectedFacility, userSession.getLocationId(), productQuantityList,
                selectedShipmentType, Constants.TRANSACTION_ISSUANCE);
    }


    private boolean validateForm(){
        boolean isValid = true;
        PositiveDecimalNumberValidator positiveDecimalNumberValidator = new PositiveDecimalNumberValidator();
        IAvailableItemService availableItemService = new AvailableItemService();
        availableItemList =
                availableItemService.getAvailableItemListByProductCode(selectedProduct.getCode());

        selectedAvailableItem = availableItemList.get(spAvailableItem.getSelectedItemPosition());
        if(selectedAvailableItem == null)
            return false;

        try {
            positiveDecimalNumberValidator.validate("Quantity", etIssuedQuantity.getText(),
                    selectedAvailableItem.getQuantityAvailable(), true);
        }catch (ValidatorException vEx){ etIssuedQuantity.setError(vEx.getMessage()); isValid = false;}

        return isValid;
    }


//    private String BIN_LOCATION = "BIN_LOCATION", LOT_NUMBER = "LOT_NUMBER", QUANTITY = "QUANTITY", EXPIRY_DATE = "EXPIRY_DATE";
//    private List<WeakHashMap<String, String>> availabelItemDetailList = new ArrayList<WeakHashMap<String,String>>();
//    private String[] availableItemDetailContent = new String[] {BIN_LOCATION, QUANTITY, LOT_NUMBER, EXPIRY_DATE};
//    private int[] availableItemView = new int[] {R.id.title, R.id.quantity, R.id.subtitle, R.id.moreDetail};
//    private void populateAvailableItemList(String sessionId, String locationId, String productId, String productCode){
//
//        //download inventory item for selected product
//        inventoryItemDownloadTask = new InventoryItemDownloadTask(IssueActivity.this,
//                sessionId, locationId, productId, productCode,  null);
//        inventoryItemDownloadTask.execute();
//
//        getMappedAvailableItemList(inventoryItemDownloadTask.getAvailableItemList());
//        SimpleAdapter simpleAdapter = new SimpleAdapter(this,
//                availabelItemDetailList, R.layout.four_item_list, availableItemDetailContent, availableItemView);
//        spAvailableItem.setAdapter(simpleAdapter);
//    }
//
//
//    private void getMappedAvailableItemList(List<AvailableItem> availableItemList){
//
//
//        if(availableItemList != null){
//            availabelItemDetailList = new ArrayList<>();
//            WeakHashMap<String, String> availabelItemDetail;
//            for(AvailableItem availableItem: availableItemList){
//                availabelItemDetail = new WeakHashMap<>();
//                availabelItemDetail.put(BIN_LOCATION, availableItem.getBinLocationName());
//                availabelItemDetail.put(LOT_NUMBER, availableItem.getLotNumber());
//                availabelItemDetail.put(QUANTITY, String.valueOf(availableItem.getQuantityAvailable()));
//                availabelItemDetail.put(EXPIRY_DATE, availableItem.getExpirationDate());
//                availabelItemDetailList.add(availabelItemDetail);
//            }
//        }
//
//    }



}

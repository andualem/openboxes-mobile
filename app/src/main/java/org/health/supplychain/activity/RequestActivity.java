package org.health.supplychain.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import org.health.supplychain.R;
import org.health.supplychain.constants.Constants;
import org.health.supplychain.customexception.ValidatorException;
import org.health.supplychain.entities.Facility;
import org.health.supplychain.entities.Product;
import org.health.supplychain.entities.ProductQuantity;
import org.health.supplychain.security.entities.UserSession;
import org.health.supplychain.security.service.IUserSessionService;
import org.health.supplychain.security.service.UserSessionService;
import org.health.supplychain.service.FacilityService;
import org.health.supplychain.service.IFacilityService;
import org.health.supplychain.service.IProductService;
import org.health.supplychain.service.IRequestTransactionService;
import org.health.supplychain.service.ProductService;
import org.health.supplychain.service.RequestTransactionService;
import org.health.supplychain.validator.PositiveDecimalNumberValidator;
import org.health.supplychain.view.MessageDialogView;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import io.realm.Realm;

public class RequestActivity extends AppCompatActivity{

    private Spinner spRequestedFacilities;
    private ListView lvRequestedProductList;
    private Spinner spProduct;
    private EditText etRequestedQuantity;
    private EditText etRequestRemark;
    private Button btnAddItem;
    private Button btnSaveRequest;
    ArrayAdapter<String> dataAdapter;

    private Facility selectedFacility;
    private Product selectedProduct;
    private double quantity;
    private String remark;
    private List<ProductQuantity> productQuantityList;


    private IFacilityService facilityService;
    private IProductService productService;
    private List<Product> productList;
    private List<String> productNameList;
    private List<Facility> facilityList;
    private List<String> facilityNameList;

    private IRequestTransactionService requestTransactionService;
    private IUserSessionService userSessionService;

    private Number currentIdNum;
    private long productQuantityId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.request_activity_layout);
        userSessionService = new UserSessionService();

        spRequestedFacilities = findViewById(R.id.spRequestedFacilities);
        lvRequestedProductList = findViewById(R.id.lvRequestedProductList);
        spProduct = findViewById(R.id.spProduct);
        etRequestedQuantity = findViewById(R.id.etRequestedQuantity);
        etRequestRemark = findViewById(R.id.etRequestRemark);
        btnAddItem = findViewById(R.id.btnAddItem);
        btnSaveRequest = findViewById(R.id.btnSaveRequest);

        productQuantityList = new ArrayList<>();


        getFacilityList();
        dataAdapter =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_layout, R.id.text, facilityNameList);
        spRequestedFacilities.setAdapter(dataAdapter);


        getProductList();
        if(productNameList != null && !productNameList.isEmpty()) {
            dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_layout, R.id.text, productNameList);
            spProduct.setAdapter(dataAdapter);
        }

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProductDetail();
                cleanForm();
                updateRequestedProductList();
            }
        });


        btnSaveRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isSaved = saveRequestTransaction();

                MessageDialogView messageDialogView = new MessageDialogView(RequestActivity.this);
                if(isSaved) {
                    messageDialogView.showMessage(getString(R.string.success),
                            getString(R.string.saved_successfully),
                            Constants.MESSAGE_TYPE_SUCCESS);

                    cleanForm();
                } else {
                    messageDialogView.showMessage(getString(R.string.error),
                            getString(R.string.save_failed),
                            Constants.MESSAGE_TYPE_FAILURE);
                }
            }
        });

        getLastProductQuantityId();
    }



    private void getFacilityList(){
        userSessionService = new UserSessionService();
        UserSession userSession = userSessionService.getUserSessionDetail(RequestActivity.this);

        facilityService = new FacilityService();
        facilityList = facilityService.getReceivingFacilities(userSession.getLocationId());
        if(facilityList != null && !facilityList.isEmpty()){
            facilityNameList = new ArrayList<>();
            for(Facility facility: facilityList){
                facilityNameList.add(facility.getName());
            }

        }
    }

    private void getLastProductQuantityId() {
        Realm realm = Realm.getDefaultInstance();
        currentIdNum = realm.where(ProductQuantity.class).max("id");
        productQuantityId = ((currentIdNum == null)? 0L: currentIdNum.longValue()) + 1;
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
        quantity = Double.valueOf(etRequestedQuantity.getText().toString());
        remark = etRequestRemark.getText().toString();

        ProductQuantity productQuantity = new ProductQuantity();
        productQuantity.setId(productQuantityId++);
        productQuantity.setRequestedQuantity(quantity);
        productQuantity.setProduct(selectedProduct);
        productQuantity.setRemark(remark);
        productQuantityList.add(productQuantity);
    }

    private void cleanForm(){
        etRequestedQuantity.setText("");
        etRequestRemark.setText("");
        //clean the old list
        productDetailList.clear();
    }



    private void updateRequestedProductList(){
        getListOfProductDetail();
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,
                productDetailList, R.layout.two_item_list, productDetailContent, view);
        lvRequestedProductList.setAdapter(simpleAdapter);
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
            producteDetail.put("category", productQuantity.getRequestedQuantity() +
                    getString(R.string.tab) +
                    productQuantity.getProduct().getMeasurementUnit());
            productDetailList.add(producteDetail);
        }

    }

    private boolean saveRequestTransaction(){
        if(productQuantityList == null
                || productQuantityList.isEmpty())
            return false;

        selectedFacility = facilityList.get(spRequestedFacilities.getSelectedItemPosition());
        requestTransactionService = new RequestTransactionService();
        UserSession userSession = userSessionService.getUserSessionDetail(RequestActivity.this);
        return requestTransactionService.saveRequest(selectedFacility, userSession, productQuantityList);
    }


    private boolean validateForm(){
        boolean isValid = true;
        PositiveDecimalNumberValidator positiveDecimalNumberValidator = new PositiveDecimalNumberValidator();

        try {
            positiveDecimalNumberValidator.validate("Quantity", etRequestedQuantity.getText(), true);
        }catch (ValidatorException vEx){ etRequestedQuantity.setError(vEx.getMessage()); isValid = false;}

        return isValid;
    }
}

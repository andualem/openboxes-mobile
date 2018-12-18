package org.health.supplychain.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import org.health.supplychain.R;
import org.health.supplychain.constants.Constants;
import org.health.supplychain.customexception.ValidatorException;
import org.health.supplychain.entities.AvailableItem;
import org.health.supplychain.entities.Facility;
import org.health.supplychain.entities.IssueTransaction;
import org.health.supplychain.entities.Product;
import org.health.supplychain.entities.ProductQuantity;
import org.health.supplychain.entities.Shipment;
import org.health.supplychain.entities.ShipmentItem;
import org.health.supplychain.entities.ShipmentTransaction;
import org.health.supplychain.entities.Transaction;
import org.health.supplychain.security.entities.UserSession;
import org.health.supplychain.security.service.IUserSessionService;
import org.health.supplychain.security.service.UserSessionService;
import org.health.supplychain.service.IProductService;
import org.health.supplychain.service.IReceiveTransactionService;
import org.health.supplychain.service.IShipmentService;
import org.health.supplychain.service.IShipmentTransactionService;
import org.health.supplychain.service.ProductService;
import org.health.supplychain.service.ReceiveTransactionService;
import org.health.supplychain.service.ShipmentService;
import org.health.supplychain.service.ShipmentTransactionService;
import org.health.supplychain.validator.PositiveDecimalNumberValidator;
import org.health.supplychain.view.ReceivedForRequestItemTableRow;
import org.health.supplychain.view.ReceivedItemTableRow;

import java.util.ArrayList;
import java.util.List;

public class ReceiveForRequestActivity extends AppCompatActivity{

    private List<ReceivedForRequestItemTableRow> receivedForRequestItemTableRowList;
    private TextView tvIssuingFacility;
    private Button btnSaveReceive;
    private String selectedShipmentId;
    private IProductService productService;
    private IReceiveTransactionService receiveTransactionService;
    private Facility sourceFacility;
    private IUserSessionService userSessionService;
    private IShipmentService shipmentService;
    private Shipment selectedShipment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receiveforrequest_activity_layout);
        tvIssuingFacility = findViewById(R.id.tvIssuingFacility);
        btnSaveReceive = findViewById(R.id.btnSaveReceive);

        userSessionService = new UserSessionService();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            selectedShipmentId = bundle.getString(Constants.SHIPMENT_ID);
            populateForm();

        }

        btnSaveReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isSaved = saveReceiveTransaction();
            }
        });



    }



    private void populateForm(){
        shipmentService = new ShipmentService();
        selectedShipment = shipmentService.getShipmentById(selectedShipmentId);

        Facility sourceFacility;
        String sourceFacilityName;
        List<ShipmentItem> shipmentItemList;

        if(selectedShipment!= null){
            sourceFacility = selectedShipment.getOrigin();
            sourceFacilityName = sourceFacility != null? sourceFacility.getName(): "";
            shipmentItemList = selectedShipment.getShipmentItemList();

            tvIssuingFacility.setText(sourceFacilityName);

            if(shipmentItemList != null && !shipmentItemList.isEmpty()){
                TableLayout receivedTableLayout = (TableLayout) findViewById(R.id.receiveForRequestTableLayout);
                ReceivedForRequestItemTableRow receivedItemTableRow;
                receivedForRequestItemTableRowList = new ArrayList<>();
                for(ShipmentItem shipmentItem: shipmentItemList){
                    AvailableItem availableItem = shipmentItem.getInventoryItem();
                    receivedItemTableRow = new ReceivedForRequestItemTableRow(ReceiveForRequestActivity.this,
                            availableItem.getProductCode(),
                            availableItem.getProductName(),
                            String.valueOf(shipmentItem.getQuantity()),
                            String.valueOf(""));
                    receivedTableLayout.addView(receivedItemTableRow);
                    receivedForRequestItemTableRowList.add(receivedItemTableRow);
                }


            }

        }
    }

    private boolean saveReceiveTransaction(){
        if(!validateForm())
            return false;

        Product product;
        productService = new ProductService();
        receiveTransactionService = new ReceiveTransactionService();
        List<ProductQuantity> productQuantityList = new ArrayList<>();
        ProductQuantity productQuantity;

        for(ReceivedForRequestItemTableRow tableRow: receivedForRequestItemTableRowList){
            productQuantity = new ProductQuantity();

            product = productService.getProductByCode(tableRow.getProductCode());

            productQuantity.setProduct(product);
            productQuantity.setRequestedQuantity(Double.valueOf(tableRow.getRequestedQuantity()));
            productQuantity.setIssuedQuantity(Double.valueOf(tableRow.getIssuedQuantity()));
            productQuantity.setReceivedQuantity(Double.valueOf(tableRow.getEtReceivedQuantity().getText().toString()));
            productQuantityList.add(productQuantity);

        }

        UserSession userSession = userSessionService.getUserSessionDetail(ReceiveForRequestActivity.this);
        receiveTransactionService.saveReceiveTransaction(sourceFacility, userSession.getLocationId(), productQuantityList,
                Constants.TRANSACTION_RECEIVE_FOR_REQUEST);

        return false;
    }

    private boolean validateForm(){
        boolean isValid = true;
        PositiveDecimalNumberValidator positiveDecimalNumberValidator = new PositiveDecimalNumberValidator();

        for(ReceivedForRequestItemTableRow tableRow: receivedForRequestItemTableRowList){
            try {
                positiveDecimalNumberValidator.validate("Quantity", tableRow.getEtReceivedQuantity().getText(), true);
            }catch (ValidatorException vEx){ tableRow.getEtReceivedQuantity().setError(vEx.getMessage()); isValid = false;}
        }

        return isValid;
    }
}

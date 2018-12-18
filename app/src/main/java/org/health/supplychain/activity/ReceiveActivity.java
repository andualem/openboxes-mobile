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
import org.health.supplychain.entities.ReceiveTransaction;
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
import org.health.supplychain.view.ReceivedItemTableRow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.RealmList;

public class ReceiveActivity extends AppCompatActivity{


    private List<ReceivedItemTableRow> receivedItemTableRowList;
    private TextView tvFacilityName;
    private Button btnSaveReceiveTran;
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
        setContentView(R.layout.receive_activity_layout);
        tvFacilityName = findViewById(R.id.tvIssuingFacility);
        btnSaveReceiveTran = findViewById(R.id.btnSaveReceiveTran);

        userSessionService = new UserSessionService();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            selectedShipmentId = bundle.getString(Constants.SHIPMENT_ID);
            populateForm();

        }


        btnSaveReceiveTran.setOnClickListener(new View.OnClickListener() {
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

            tvFacilityName.setText(sourceFacilityName);

            if(shipmentItemList != null && !shipmentItemList.isEmpty()) {
                TableLayout receivedTableLayout = (TableLayout) findViewById(R.id.receiveTableLayout);
                ReceivedItemTableRow receivedItemTableRow;
                receivedItemTableRowList = new ArrayList<>();
                AvailableItem availableItem;
                for(ShipmentItem shipmentItem: shipmentItemList){
                    availableItem = shipmentItem.getInventoryItem();
                    receivedItemTableRow = new ReceivedItemTableRow(ReceiveActivity.this,
                            availableItem.getProductCode(),
                            availableItem.getProductName(),
                            String.valueOf(shipmentItem.getQuantity()));
                    receivedTableLayout.addView(receivedItemTableRow);
                    receivedItemTableRowList.add(receivedItemTableRow);
                }

            }
        }

    }


    private boolean saveReceiveTransaction() {
        if (!validateForm()) {
            return false;
        }

        Product product;
        productService = new ProductService();
        receiveTransactionService = new ReceiveTransactionService();
        List<ProductQuantity> productQuantityList = new ArrayList<>();
        ProductQuantity productQuantity;
        for (ReceivedItemTableRow tableRow : receivedItemTableRowList) {
            productQuantity = new ProductQuantity();

            product = productService.getProductByCode(tableRow.getProductCode());

//            receiveTransaction.setTransaction(null);
            productQuantity.setProduct(product);
            productQuantity.setIssuedQuantity(Double.valueOf(tableRow.getIssuedQuantity()));
            productQuantity.setReceivedQuantity(Double.valueOf(tableRow.getEtReceivedQuantity().getText().toString()));
            productQuantityList.add(productQuantity);


        }

        UserSession userSession = userSessionService.getUserSessionDetail(ReceiveActivity.this);
        receiveTransactionService.saveReceiveTransaction(sourceFacility, userSession.getLocationId(), productQuantityList, Constants.TRANSACTION_RECEIVE);

        return false;
    }

    private boolean validateForm(){
        boolean isValid = true;
        PositiveDecimalNumberValidator positiveDecimalNumberValidator = new PositiveDecimalNumberValidator();

        for(ReceivedItemTableRow tableRow: receivedItemTableRowList){
            try {
                positiveDecimalNumberValidator.validate("Quantity", tableRow.getEtReceivedQuantity().getText(), true);
            }catch (ValidatorException vEx){ tableRow.getEtReceivedQuantity().setError(vEx.getMessage()); isValid = false;}
        }

        return isValid;
    }
}

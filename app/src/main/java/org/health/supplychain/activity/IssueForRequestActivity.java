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
import org.health.supplychain.entities.Facility;
import org.health.supplychain.entities.IssueForRequestTransaction;
import org.health.supplychain.entities.Product;
import org.health.supplychain.entities.ProductQuantity;
import org.health.supplychain.entities.RequestTransaction;
import org.health.supplychain.entities.Transaction;
import org.health.supplychain.security.entities.UserSession;
import org.health.supplychain.security.service.IUserSessionService;
import org.health.supplychain.security.service.UserSessionService;
import org.health.supplychain.service.IIssuanceTransactionService;
import org.health.supplychain.service.IProductService;
import org.health.supplychain.service.IRequestTransactionService;
import org.health.supplychain.service.IssuanceTransactionService;
import org.health.supplychain.service.ProductService;
import org.health.supplychain.service.RequestTransactionService;
import org.health.supplychain.validator.PositiveDecimalNumberValidator;
import org.health.supplychain.view.IssuedItemTableRow;
import org.health.supplychain.view.ReceivedItemTableRow;

import java.util.ArrayList;
import java.util.List;

public class IssueForRequestActivity extends AppCompatActivity{

    private List<IssuedItemTableRow> issuedItemTableRowList;
    private TextView tvRequestingFacilityName;
    private Button btnSaveIssuance;
    private String selectedRequestUuid;
    private RequestTransaction selectedRequestTransaction;
    private IProductService productService;
    private IRequestTransactionService requestTransactionService;
    private IIssuanceTransactionService issuanceTransactionService;
    private Facility requestingFacility;
    private IUserSessionService userSessionService;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.issueforrequest_activity_layout);

        userSessionService = new UserSessionService();
        tvRequestingFacilityName = findViewById(R.id.tvRequestingFacilityName);
        btnSaveIssuance = findViewById(R.id.btnSaveIssuance);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            selectedRequestUuid = bundle.getString(Constants.REQUEST_UUID);
            populateForm();

        }


        btnSaveIssuance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isSaved = saveIssuanceTransaction();
            }
        });


    }


    private void populateForm(){
        requestTransactionService = new RequestTransactionService();
        selectedRequestTransaction = requestTransactionService.getRequestTransactionByUuid(selectedRequestUuid);
        Transaction transaction;
        String requestingFacilityName;
        List<ProductQuantity> productQuantityList;

        if(selectedRequestTransaction != null){
            transaction = selectedRequestTransaction.getTransaction();
            requestingFacility = transaction != null? transaction.getSourceFacility():null;
            requestingFacilityName = requestingFacility != null? requestingFacility.getName(): "";
            productQuantityList = selectedRequestTransaction.getProductQuantityRealmList();

            tvRequestingFacilityName.setText(requestingFacilityName);

            if(productQuantityList != null && !productQuantityList.isEmpty()){
                TableLayout issueTableLayout = (TableLayout) findViewById(R.id.issueTableLayout);
                IssuedItemTableRow issuedItemTableRow;

                issuedItemTableRowList = new ArrayList<>();
                for(ProductQuantity productQuantity: productQuantityList){
                    issuedItemTableRow = new IssuedItemTableRow(IssueForRequestActivity.this,
                            productQuantity.getProduct().getCode(),
                            productQuantity.getProduct().getName(),
                            String.valueOf(productQuantity.getRequestedQuantity()));
                    issueTableLayout.addView(issuedItemTableRow);
                    issuedItemTableRowList.add(issuedItemTableRow);
                }

            }
        }
    }


    private boolean saveIssuanceTransaction() {
        if(!validateForm())
            return false;

        Product product;
        productService = new ProductService();
        issuanceTransactionService = new IssuanceTransactionService();
        List<ProductQuantity> productQuantityList = new ArrayList<>();
        ProductQuantity productQuantity;

        for(IssuedItemTableRow tableRow: issuedItemTableRowList){
            productQuantity = new ProductQuantity();
            product = productService.getProductByCode(tableRow.getProductCode());

            productQuantity.setProduct(product);
            productQuantity.setRequestedQuantity(Double.valueOf(tableRow.getRequestedQuantity()));
            productQuantity.setReceivedQuantity(Double.valueOf(tableRow.getEtIssuedQuantity().getText().toString()));
            productQuantityList.add(productQuantity);
        }

        UserSession userSession = userSessionService.getUserSessionDetail(IssueForRequestActivity.this);

        issuanceTransactionService.saveIssuanceForRequest(requestingFacility, userSession.getLocationId(), selectedRequestTransaction,
                productQuantityList, null, Constants.TRANSACTION_ISSUANCE_FOR_REQUEST);
        return false;
    }


    private boolean validateForm(){
        boolean isValid = true;
        PositiveDecimalNumberValidator positiveDecimalNumberValidator = new PositiveDecimalNumberValidator();

        for(IssuedItemTableRow tableRow: issuedItemTableRowList){
            try {
                positiveDecimalNumberValidator.validate("Quantity", tableRow.getEtIssuedQuantity().getText(), true);
            }catch (ValidatorException vEx){ tableRow.getEtIssuedQuantity().setError(vEx.getMessage()); isValid = false;}
        }

        return isValid;
    }
}

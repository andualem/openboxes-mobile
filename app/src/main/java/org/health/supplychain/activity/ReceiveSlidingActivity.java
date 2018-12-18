package org.health.supplychain.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import org.health.supplychain.R;
import org.health.supplychain.constants.Constants;
import org.health.supplychain.customexception.ValidatorException;
import org.health.supplychain.entities.AvailableItem;
import org.health.supplychain.entities.Facility;
import org.health.supplychain.entities.Product;
import org.health.supplychain.entities.ProductQuantity;
import org.health.supplychain.entities.Shipment;
import org.health.supplychain.entities.ShipmentItem;
import org.health.supplychain.security.entities.UserSession;
import org.health.supplychain.security.service.IUserSessionService;
import org.health.supplychain.security.service.UserSessionService;
import org.health.supplychain.service.IProductService;
import org.health.supplychain.service.IReceiveTransactionService;
import org.health.supplychain.service.IShipmentService;
import org.health.supplychain.service.ProductService;
import org.health.supplychain.service.ReceiveTransactionService;
import org.health.supplychain.service.ShipmentService;
import org.health.supplychain.validator.PositiveDecimalNumberValidator;
import org.health.supplychain.view.ReceivedItemLayout;
import org.health.supplychain.view.ReceivedItemTableRow;

import java.util.ArrayList;
import java.util.List;

public class ReceiveSlidingActivity extends AppCompatActivity{


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
    private List<ReceivedItemLayout> receivedItemLayoutList;
    private ReceivedItemLayout receivedItemLayout;

//    private static final int NUM_PAGES = 5;
//    private ViewPager mPager;
//    private PagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receive_sliding_activity_layout);
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


        //sliding part - related
//        mPager = (ViewPager) findViewById(R.id.pagerSample);
//        mPagerAdapter = new MyPagerAdapter();
//        mPager.setAdapter(mPagerAdapter);
//        mPager.setCurrentItem(3);
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
                LinearLayout receivedItemLinearLayout = (LinearLayout) findViewById(R.id.receivedItemLinearLayout);
                ReceivedItemLayout receivedItemLayout;
                receivedItemLayoutList = new ArrayList<>();
                AvailableItem availableItem;
                for(ShipmentItem shipmentItem: shipmentItemList){
                    availableItem = shipmentItem.getInventoryItem();
                    receivedItemLayout = new ReceivedItemLayout(ReceiveSlidingActivity.this,
                            availableItem.getProductName(),
                            availableItem.getLotNumber(),
                            availableItem.getExpirationDate(),
                            "Unit",
                            String.valueOf(shipmentItem.getQuantity()));
                    receivedItemLinearLayout.addView(receivedItemLayout);
                    receivedItemLayoutList.add(receivedItemLayout);
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

        UserSession userSession = userSessionService.getUserSessionDetail(ReceiveSlidingActivity.this);
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



//    @Override
//    public void onBackPressed() {
//        if (mPager.getCurrentItem() == 0) {
//            // If the user is currently looking at the first step, allow the system to handle the
//            // Back button. This calls finish() on this activity and pops the back stack.
//            super.onBackPressed();
//        } else {
//            // Otherwise, select the previous step.
//            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
//        }
//    }


//    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
//        public ScreenSlidePagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return new ScreenSlidePageFragment();
//        }
//
//        @Override
//        public int getCount() {
//            return NUM_PAGES;
//        }
//    }






//    private class MyPagerAdapter extends PagerAdapter {
//
//        public int getCount() {
//            return 3;
//        }
//
//        public Object instantiateItem(View collection, int position) {
//
//            LayoutInflater inflater = (LayoutInflater) collection.getContext()
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//            int resId = 0;
//            switch (position) {
//                case 0:
//                    resId = R.layout.activity_login;
//                    break;
//                case 1:
//                    resId = R.layout.chooselocation_activity_layout;
//                    break;
//                case 2:
//                    resId = R.layout.activity_menu;
//                    break;
//            }
//
//            View view = inflater.inflate(resId, null);
//            ((ViewPager) collection).addView(view, 0);
//            return view;
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return false;
//        }
//    }
}

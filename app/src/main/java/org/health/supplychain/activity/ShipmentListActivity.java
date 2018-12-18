package org.health.supplychain.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import org.health.supplychain.R;
import org.health.supplychain.constants.Constants;
import org.health.supplychain.entities.Facility;
import org.health.supplychain.entities.IssueTransaction;
import org.health.supplychain.entities.Shipment;
import org.health.supplychain.entities.ShipmentTransaction;
import org.health.supplychain.entities.Transaction;
import org.health.supplychain.security.entities.UserSession;
import org.health.supplychain.security.service.IUserSessionService;
import org.health.supplychain.security.service.UserSessionService;
import org.health.supplychain.service.IShipmentService;
import org.health.supplychain.service.ShipmentService;
import org.health.supplychain.utility.Utils;
import org.health.supplychain.view.MessageDialogView;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/**
 * Created by aworkneh on 9/15/2018.
 */

public class ShipmentListActivity extends ListActivity {

    private static final String FACILITY = "FACILITY", SHIPPED_DATE = "SHPPED_DATE";
    private List<WeakHashMap<String, String>> shipmentDetailList = new ArrayList<WeakHashMap<String,String>>();
    private String[] shipmentDetailContent = new String[] {FACILITY, SHIPPED_DATE};
    private int[] view = new int[] {R.id.title, R.id.subtitle};
    private MessageDialogView messageDialogView;
    private SimpleAdapter simpleAdapter;
    private Shipment selectedShipment;
    private boolean openAsEditable = true;
    private IUserSessionService userSessionService = new UserSessionService();
    private UserSession userSession;
    private List<Shipment> shipmentList;
    private IShipmentService shipmentService;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shipment_list_layout);

        messageDialogView = new MessageDialogView(ShipmentListActivity.this);
        userSession = userSessionService.getUserSessionDetail(ShipmentListActivity.this);

        getShipmentList();
        if(shipmentList == null || shipmentList.isEmpty()){
            messageDialogView.showMessage(getString(R.string.info),
                    getString(R.string.nothing_to_display),
                    Constants.MESSAGE_TYPE_INFORMATION);
            return;
        }

        simpleAdapter = new SimpleAdapter(this,
                shipmentDetailList, R.layout.two_item_list, shipmentDetailContent, view);
        setListAdapter(simpleAdapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if(openAsEditable) {
                    selectedShipment = shipmentList.get(arg2);
                    Intent intent;
                    intent = new Intent(getApplicationContext(), ReceiveSlidingActivity.class);
//                    intent = new Intent(getApplicationContext(), ReceiveForRequestActivity.class);
                    intent.putExtra(Constants.SHIPMENT_ID, selectedShipment.getId());


                    startActivity(intent);
                }
            }
        });
    }

    private void getShipmentList(){
        shipmentService = new ShipmentService();
        shipmentList = shipmentService.getShipmentByDestinationId(userSession.getLocationId(), null);

        if(shipmentList != null && !shipmentList.isEmpty()){
            WeakHashMap<String, String> shipmentDetail;

            for(Shipment shipment:shipmentList){
                shipmentDetail = new WeakHashMap<String, String>();
                Facility sourceFacility = shipment.getOrigin();
                shipmentDetail.put(FACILITY, sourceFacility != null? sourceFacility.getName(): "");
                shipmentDetail.put(SHIPPED_DATE, getString(R.string.shipped_date) + shipment.getActualShippingDate());
                shipmentDetailList.add(shipmentDetail);
            }
        }
    }

//    private List<ShipmentTransaction> getSampleData(){
//        List<ShipmentTransaction> shipmentList = new ArrayList<>();
//        Facility facility = new Facility();
//        facility.setName("Bole Health Center");
//        Transaction transaction = new Transaction();
//        transaction.setSourceFacility(facility);
//
//        IssueTransaction issueTransaction = new IssueTransaction();
//        issueTransaction.setTransaction(transaction);
////        issueTransaction.setQuantity(12);
//
//        ShipmentTransaction shipment = new ShipmentTransaction();
//        shipment.setIssueTransaction(issueTransaction);
//
//
//        shipmentList.add(shipment);
//
//        return shipmentList;
//    }

}

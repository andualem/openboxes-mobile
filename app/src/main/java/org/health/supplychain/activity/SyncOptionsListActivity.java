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
import org.health.supplychain.entities.Product;
import org.health.supplychain.entities.ProductQuantity;
import org.health.supplychain.entities.ShipmentTransaction;
import org.health.supplychain.entities.Transaction;
import org.health.supplychain.security.entities.UserSession;
import org.health.supplychain.security.service.IUserSessionService;
import org.health.supplychain.security.service.UserSessionService;
import org.health.supplychain.service.FacilityService;
import org.health.supplychain.service.IFacilityService;
import org.health.supplychain.service.IProductService;
import org.health.supplychain.service.IShipmentTransactionService;
import org.health.supplychain.service.ProductService;
import org.health.supplychain.service.ShipmentTransactionService;
import org.health.supplychain.tasks.ChooseLocationUploadTask;
import org.health.supplychain.tasks.GenericProductDownloadTask;
import org.health.supplychain.tasks.InternalLocationDownloadTask;
import org.health.supplychain.tasks.LocationDownloadTask;
import org.health.supplychain.tasks.ProductDownloadTask;
import org.health.supplychain.tasks.RequisitionDownloadTask;
import org.health.supplychain.tasks.RequisitionUploadTask;
import org.health.supplychain.tasks.ShipmentDownloadTask;
import org.health.supplychain.tasks.ShipmentItemUploadTask;
import org.health.supplychain.tasks.ShipmentTypeDownloadTask;
import org.health.supplychain.tasks.ShipmentUploadTask;
import org.health.supplychain.tasks.SingleShipmentUploadTask;
import org.health.supplychain.webservice.API;
import org.health.supplychain.webservice.entities.ShipmentItems;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class SyncOptionsListActivity extends ListActivity{

    private List<WeakHashMap<String, String>> messageDetailList = new ArrayList<WeakHashMap<String,String>>();
    private String[] messageDetailContent = new String[] {"title", "category"};
    private int[] view = new int[] {R.id.title,
            R.id.subtitle,};

    private IUserSessionService userSessionService;
    private UserSession currentUserSession;
    private String sessionId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        getListOfContentTitle();
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,
                messageDetailList, R.layout.two_item_list, messageDetailContent, view);
        setListAdapter(simpleAdapter);
        userSessionService = new UserSessionService();
        currentUserSession = userSessionService.getUserSessionDetail(SyncOptionsListActivity.this);
        if(currentUserSession != null) {
            sessionId = currentUserSession.getSessionId();
        }


        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                Intent contentOptionIntent;
                switch(arg2)
                {
                    case 0:
//                        IFacilityService facilityService = new FacilityService();
//                        Facility facility = new Facility();
//
//                        facility.setId("HC-BHC");
//                        facility.setName("Bole Health Center");
//
//                        facilityService.saveFacility(facility);
//
//                        facility = new Facility();
//                        facility.setId("HC-Current");
//                        facility.setName("Current Health Center");
//
//                        facilityService.saveFacility(facility);

                        LocationDownloadTask locationDownloadTask
                                = new LocationDownloadTask(SyncOptionsListActivity.this,
                                null, null,
                                sessionId, null);
                        locationDownloadTask.execute();
                        break;

                    case 1:
//                        IProductService productService = new ProductService();
//                        Product product = new Product();
//                        product.setName("Implant");
//                        product.setCode("IMP");
//                        product.setMeasurementUnit("Packet");
//                        productService.saveProduct(product);


                        GenericProductDownloadTask genericProductDownloadTask
                                = new GenericProductDownloadTask(
                                SyncOptionsListActivity.this,
                                null, null,
                                sessionId, null);
                        genericProductDownloadTask.execute();

                        break;
                    case 2:

                        IShipmentTransactionService shipmentTransactionService = new ShipmentTransactionService();

                        List<ShipmentTransaction> shipmentList = new ArrayList<>();
                        Facility facility = new Facility();
                        facility.setName("Bole Health Center");
                        Transaction transaction = new Transaction();
                        transaction.setSourceFacility(facility);
                        transaction.setTransactionType(Constants.TRANSACTION_SHIPMENT);

                        IssueTransaction issueTransaction = new IssueTransaction();
                        issueTransaction.setTransaction(transaction);

//                        ProductQuantity productQuantity = new ProductQuantity();
//                        productQuantity.setProduct();
//                        productQuantity.setQuantity();
//                        issueTransaction.setProductQuantityRealmList(productQuantity);

                        ShipmentTransaction shipment = new ShipmentTransaction();
                        shipment.setIssueTransaction(issueTransaction);

                        shipmentTransactionService.saveShipment(shipment);

                        break;

                    case 3:
                        String parentLocaitonId;
                        for(int i=1; i < 5; i++) {
                            parentLocaitonId = String.valueOf(i);
                            System.out.println("parentLocaitonId =  " + parentLocaitonId);
                            InternalLocationDownloadTask internalLocationDownloadTask
                                    = new InternalLocationDownloadTask(SyncOptionsListActivity.this,
                                    null, null,
                                    sessionId, parentLocaitonId, null);
                            internalLocationDownloadTask.execute();
                        }
                        break;

                    case 4:
                        //Without parameter
//                        ProductDownloadTask productDownloadTask = new ProductDownloadTask(SyncOptionsListActivity.this,
//                                null, null,
//                                sessionId, null);

                        //with parameter
                        ProductDownloadTask productDownloadTask = new ProductDownloadTask(SyncOptionsListActivity.this,
                                null, null,
                                sessionId, null, "Asprine", 0, 1, null);
                        productDownloadTask.execute();
                        break;

                    case 5:
                        ChooseLocationUploadTask chooseLocationUploadTask = new ChooseLocationUploadTask(sessionId, "1");
                        chooseLocationUploadTask.execute();
                        break;

                    case 6:
                        ShipmentDownloadTask shipmentDownloadTask = new ShipmentDownloadTask(SyncOptionsListActivity.this, sessionId, null, null);
                        shipmentDownloadTask.execute();
                        break;

                    case 7:
                        //TODO: this will be called just after issue transaction is created
                        SingleShipmentUploadTask singleShipmentUploadTask = new SingleShipmentUploadTask(sessionId,
                                currentUserSession.getLocationId(), "bc7e1757-2a07-45c2-aabd-b56368934937");
                        singleShipmentUploadTask.execute();
                        break;

                    case 8:
                        ShipmentUploadTask shipmentUploadTask = new ShipmentUploadTask(sessionId, currentUserSession.getLocationId());
                        shipmentUploadTask.execute();
                        break;

                    case 9:
                        ShipmentTypeDownloadTask shipmentTypeDownloadTask = new ShipmentTypeDownloadTask(SyncOptionsListActivity.this, sessionId);
                        shipmentTypeDownloadTask.execute();
                        break;

                    case 10:
                        String issueTransactionUuid = "bc7e1757-2a07-45c2-aabd-b56368934937";
                        String shipmentId = "ff808181676bcdfb01676bcece720001";
                        ShipmentItemUploadTask shipmentItemUploadTask = new ShipmentItemUploadTask(sessionId, issueTransactionUuid, shipmentId);
                        shipmentItemUploadTask.execute();
                        break;

                    case 11:
                        RequisitionUploadTask requisitionUploadTask = new RequisitionUploadTask(sessionId, currentUserSession.getLocationId());
                        requisitionUploadTask.execute();
                        break;

                    case 12:
                        RequisitionDownloadTask requisitionDownloadTask = new RequisitionDownloadTask(SyncOptionsListActivity.this, sessionId, null);
                        requisitionDownloadTask.execute();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void getListOfContentTitle(){
        WeakHashMap<String, String> messageDetail;

        messageDetail = new WeakHashMap<String, String>();
        messageDetail.put("title", getString(R.string.facility));
        messageDetail.put("category", getString(R.string.synchronization_detail));
        messageDetailList.add(messageDetail);

        messageDetail = new WeakHashMap<String, String>();
        messageDetail.put("title", getString(R.string.generic)
                + getString(R.string.colon_space)
                + getString(R.string.product));
        messageDetail.put("category", getString(R.string.synchronization_detail));
        messageDetailList.add(messageDetail);


        messageDetail = new WeakHashMap<String, String>();
        messageDetail.put("title", getString(R.string.tran_shipment_list));
        messageDetail.put("category", getString(R.string.synchronization_detail));
        messageDetailList.add(messageDetail);


        messageDetail = new WeakHashMap<String, String>();
        messageDetail.put("title", getString(R.string.internal_location));
        messageDetail.put("category", getString(R.string.synchronization_detail));
        messageDetailList.add(messageDetail);

        messageDetail = new WeakHashMap<String, String>();
        messageDetail.put("title", getString(R.string.product));
        messageDetail.put("category", getString(R.string.synchronization_detail));
        messageDetailList.add(messageDetail);


        //choose location
        messageDetail = new WeakHashMap<String, String>();
        messageDetail.put("title", getString(R.string.choose_location));
        messageDetail.put("category", getString(R.string.synchronization_detail));
        messageDetailList.add(messageDetail);


        //download shipment
        messageDetail = new WeakHashMap<String, String>();
        messageDetail.put("title", getString(R.string.tran_shipment_list));
        messageDetail.put("category", getString(R.string.synchronization_detail));
        messageDetailList.add(messageDetail);


        //upload single shipment list
        messageDetail = new WeakHashMap<String, String>();
        messageDetail.put("title", getString(R.string.single_shipment));
        messageDetail.put("category", getString(R.string.synchronization_detail));
        messageDetailList.add(messageDetail);


        //upload shipment list
        messageDetail = new WeakHashMap<String, String>();
        messageDetail.put("title", getString(R.string.tran_shipment_list));
        messageDetail.put("category", getString(R.string.synchronization_detail));
        messageDetailList.add(messageDetail);


        //download shipment type
        messageDetail = new WeakHashMap<String, String>();
        messageDetail.put("title", getString(R.string.shipment_type));
        messageDetail.put("category", getString(R.string.synchronization_detail));
        messageDetailList.add(messageDetail);


        //upload shipment item type
        messageDetail = new WeakHashMap<String, String>();
        messageDetail.put("title", getString(R.string.shipment_item));
        messageDetail.put("category", getString(R.string.synchronization_detail));
        messageDetailList.add(messageDetail);


        //upload request
        messageDetail = new WeakHashMap<String, String>();
        messageDetail.put("title", getString(R.string.request));
        messageDetail.put("category", getString(R.string.synchronization_detail));
        messageDetailList.add(messageDetail);


        //download request
        messageDetail = new WeakHashMap<String, String>();
        messageDetail.put("title", getString(R.string.request) + " Download");
        messageDetail.put("category", getString(R.string.synchronization_detail));
        messageDetailList.add(messageDetail);
    }
}

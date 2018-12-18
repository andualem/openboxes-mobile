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
import org.health.supplychain.entities.RequestTransaction;
import org.health.supplychain.entities.Transaction;
import org.health.supplychain.utility.Utils;
import org.health.supplychain.view.MessageDialogView;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/**
 * Created by aworkneh on 9/15/2018.
 */

public class RequestListActivity extends ListActivity {

    private static final String FACILITY = "FACILITY", REQUESTED_DATE = "REQUESTED_DATE", STATUS = "STATUS";
    private List<WeakHashMap<String, String>> requestDetailList = new ArrayList<WeakHashMap<String,String>>();
    private String[] requestDetailContent = new String[] {FACILITY, REQUESTED_DATE};
    private int[] view = new int[] {R.id.title, R.id.subtitle};
    private MessageDialogView messageDialogView;
    private SimpleAdapter simpleAdapter;
    private List<RequestTransaction> requestTransactionList;
    private RequestTransaction selectedRequest;
    private boolean openAsEditable = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_list_layout);

        messageDialogView = new MessageDialogView(RequestListActivity.this);

        getRequestList();
        if(requestTransactionList == null || requestTransactionList.isEmpty()){
            messageDialogView.showMessage(getString(R.string.info),
                    getString(R.string.nothing_to_display),
                    Constants.MESSAGE_TYPE_INFORMATION);
            return;
        }

        simpleAdapter = new SimpleAdapter(this,
                requestDetailList, R.layout.two_item_list, requestDetailContent, view);
        setListAdapter(simpleAdapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if(openAsEditable) {
                    selectedRequest = requestTransactionList.get(arg2);
                    Intent intent;
                    intent = new Intent(getApplicationContext(), IssueForRequestActivity.class);
                    intent.putExtra(Constants.REQUEST_UUID, selectedRequest.getUuid());
                    startActivity(intent);
                }
            }
        });
    }

    private void getRequestList(){
        //TODO: this query may require revision
//        bincardList = Bincard.listAll(Bincard.class);
        requestTransactionList = getSampleData();

        if(requestTransactionList != null && !requestTransactionList.isEmpty()){
            WeakHashMap<String, String> requestDetail;

            for(RequestTransaction request:requestTransactionList){
                requestDetail = new WeakHashMap<String, String>();
                requestDetail.put(FACILITY, request.getTransaction().getSourceFacility().getName());
                requestDetail.put(REQUESTED_DATE, getString(R.string.requested_date) +
                        getString(R.string.colon_space) + Utils.convertDateToString(request.getTransaction().getCreatedDate(), Constants.DATE_FORMAT));



                requestDetailList.add(requestDetail);
            }
        }
    }

    private List<RequestTransaction> getSampleData(){
        List<RequestTransaction> requestList = new ArrayList<>();
        Facility facility = new Facility();
        facility.setName("Bole Health Center");
        Transaction transaction = new Transaction();
        transaction.setSourceFacility(facility);

        RequestTransaction request = new RequestTransaction();
        request.setTransaction(transaction);
//        request.setRequestedQuantity(12);

        requestList.add(request);

        return requestList;
    }

}

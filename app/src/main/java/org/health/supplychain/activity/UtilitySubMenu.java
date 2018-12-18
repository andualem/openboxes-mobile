package org.health.supplychain.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import org.health.supplychain.R;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class UtilitySubMenu extends ListActivity{

    private List<WeakHashMap<String, String>> messageDetailList = new ArrayList<WeakHashMap<String,String>>();
    private String[] messageDetailContent = new String[] {"title", "category"};
    private int[] view = new int[] {R.id.title,
            R.id.subtitle,};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        getListOfContentTitle();
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,
                messageDetailList, R.layout.two_item_list, messageDetailContent, view);
        setListAdapter(simpleAdapter);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                Intent contentOptionIntent;
                switch(arg2)
                {
                    case 0:
                        contentOptionIntent = new Intent(getApplicationContext(), SyncOptionsListActivity.class);
//						finish();
                        startActivity(contentOptionIntent);
                        break;
                    case 1:
//                        contentOptionIntent = new Intent(getApplicationContext(), InventoryControlListActivity.class);
////						finish();
//                        startActivity(contentOptionIntent);
//                        break;

                    default:
                        break;
                }
            }
        });
    }

    private void getListOfContentTitle(){
        WeakHashMap<String, String> messageDetail;

        messageDetail = new WeakHashMap<String, String>();
        messageDetail.put("title", getString(R.string.synchronization));
        messageDetail.put("category", getString(R.string.synchronization_detail));
        messageDetailList.add(messageDetail);

        messageDetail = new WeakHashMap<String, String>();
        messageDetail.put("title", getString(R.string.change_password));
        messageDetail.put("category", getString(R.string.change_password_detail));
        messageDetailList.add(messageDetail);

    }
}

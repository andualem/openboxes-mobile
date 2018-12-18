package org.health.supplychain.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import org.health.supplychain.R;
import org.health.supplychain.constants.Constants;
import org.health.supplychain.entities.Bincard;
import org.health.supplychain.entities.Product;
import org.health.supplychain.utility.Utils;
import org.health.supplychain.view.MessageDialogView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.WeakHashMap;

/**
 * Created by aworkneh on 9/15/2018.
 */

public class BincardListActivity extends ListActivity {

    private static final String PRODUCT_NAME = "PRODUCT_NAME", QUANTITY = "QUANTITY", LOT_NUMBER = "LOT_NUMBER", EXPIRY_DATE = "EXPIRY_DATE";
    private List<WeakHashMap<String, String>> bincardDetailList = new ArrayList<WeakHashMap<String,String>>();
    private String[] bincardDetailContent = new String[] {PRODUCT_NAME, QUANTITY, LOT_NUMBER, EXPIRY_DATE};
    private int[] view = new int[] {R.id.title, R.id.quantity, R.id.subtitle, R.id.moreDetail};
    private MessageDialogView messageDialogView;
    private SimpleAdapter simpleAdapter;
    private List<Bincard> bincardList;
    private Bincard selectedBincard;
    private boolean openAsEditable = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bincard_list_layout);

        messageDialogView = new MessageDialogView(BincardListActivity.this);

        getBincardDetailList();
        if(bincardList == null || bincardList.isEmpty()){
            messageDialogView.showMessage(getString(R.string.info),
                    getString(R.string.nothing_to_display),
                    Constants.MESSAGE_TYPE_INFORMATION);
            return;
        }

        simpleAdapter = new SimpleAdapter(this,
                bincardDetailList, R.layout.four_item_list, bincardDetailContent, view);
        setListAdapter(simpleAdapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if(openAsEditable) {
                    selectedBincard = bincardList.get(arg2);
                    Intent bincardActivityIntent;
                    bincardActivityIntent = new Intent(getApplicationContext(), StockKeepingForm.class);
                    bincardActivityIntent.putExtra(Constants.ID, selectedBincard.getUuid());
                    startActivity(bincardActivityIntent);
                }
            }
        });
    }

    private void getBincardDetailList(){
        //TODO: this query may require revision
//        bincardList = Bincard.listAll(Bincard.class);
        bincardList = getSampleData();

        if(bincardList != null && !bincardList.isEmpty()){
            WeakHashMap<String, String> bincardDetail;

            for(Bincard bincard:bincardList){
                bincardDetail = new WeakHashMap<String, String>();
                bincardDetail.put(PRODUCT_NAME, bincard.getProduct().getName());
                bincardDetail.put(QUANTITY, "12");
                bincardDetail.put(LOT_NUMBER, getString(R.string.lot) +
                        getString(R.string.colon_space) + bincard.getLotNumber());
                bincardDetail.put(EXPIRY_DATE, getString(R.string.expiry_date) +
                        getString(R.string.colon_space) + Utils.convertDateToString(bincard.getExpiryDate(), Constants.DATE_FORMAT));

                bincardDetailList.add(bincardDetail);
            }
        }
    }

    private List<Bincard> getSampleData(){
        List<Bincard> bincardList = new ArrayList<>();
        Bincard bincard;

        Product product = new Product();
        product.setName("IUD");
        bincard = new Bincard();
        bincard.setProduct(product);
        bincard.setLotNumber("1234");
        bincard.setExpiryDate((new Date()).getTime());
        bincardList.add(bincard);

        product = new Product();
        product.setName("Implant");
        bincard = new Bincard();
        bincard.setProduct(product);
        bincard.setLotNumber("3456");
        bincard.setExpiryDate((new Date()).getTime());
        bincardList.add(bincard);

        return bincardList;
    }

}

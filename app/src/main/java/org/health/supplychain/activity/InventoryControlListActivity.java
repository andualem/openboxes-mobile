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

public class InventoryControlListActivity extends ListActivity {

    private static final String PRODUCT_NAME = "PRODUCT_NAME", QUANTITY = "QUANTITY", CODE = "CODE", UNIT = "UNIT";
    private List<WeakHashMap<String, String>> productDetailList = new ArrayList<WeakHashMap<String,String>>();
    private String[] bincardDetailContent = new String[] {PRODUCT_NAME, QUANTITY, CODE, UNIT};
    private int[] view = new int[] {R.id.title, R.id.quantity, R.id.subtitle, R.id.moreDetail};
    private MessageDialogView messageDialogView;
    private SimpleAdapter simpleAdapter;
    private List<Product> productList;
    private Product selectedProduct;
    private boolean openAsEditable = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bincard_list_layout);

        messageDialogView = new MessageDialogView(InventoryControlListActivity.this);

        getBincardDetailList();
        if(productList == null || productList.isEmpty()){
            messageDialogView.showMessage(getString(R.string.info),
                    getString(R.string.nothing_to_display),
                    Constants.MESSAGE_TYPE_INFORMATION);
            return;
        }

        simpleAdapter = new SimpleAdapter(this,
                productDetailList, R.layout.four_item_list, bincardDetailContent, view);
        setListAdapter(simpleAdapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if(openAsEditable) {
                    selectedProduct = productList.get(arg2);
                    Intent bincardActivityIntent;
                    bincardActivityIntent = new Intent(getApplicationContext(), StockKeepingForm.class);
                    bincardActivityIntent.putExtra(Constants.ID, selectedProduct.getId());
                    startActivity(bincardActivityIntent);
                }
            }
        });
    }

    private void getBincardDetailList(){
        //TODO: this query may require revision
//        bincardList = Bincard.listAll(Bincard.class);
        productList = getSampleData();

        if(productList != null && !productList.isEmpty()){
            WeakHashMap<String, String> productDetail;

            for(Product product:productList){
                productDetail = new WeakHashMap<String, String>();
                productDetail.put(PRODUCT_NAME, product.getName());
                productDetail.put(QUANTITY, "12");
                productDetail.put(CODE, getString(R.string.code) +
                        getString(R.string.colon_space) + product.getCode());
                productDetail.put(UNIT, product.getMeasurementUnit());

                productDetailList.add(productDetail);
            }
        }
    }

    private List<Product> getSampleData(){
        List<Product> productList = new ArrayList<>();

        Product product = new Product();
        product.setName("IUD");
        product.setCode("101");
        product.setMeasurementUnit("Packet");
        productList.add(product);

        product = new Product();
        product.setName("Implant");
        product.setCode("102");
        product.setMeasurementUnit("Packet");
        productList.add(product);

        return productList;
    }

}

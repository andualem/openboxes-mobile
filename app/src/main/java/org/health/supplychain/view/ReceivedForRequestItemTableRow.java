package org.health.supplychain.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import org.health.supplychain.R;

public class ReceivedForRequestItemTableRow extends TableRow{

    private TextView tvRequestedQuantity;
    private TextView tvProductName;
    private TextView tvIssuedQuantity;
    private EditText etReceivedQuantity;

    private String productCode;
    private String productName;
    private String issuedQuantity;
    private String receivedQuantity;
    private String requestedQuantity;

    public ReceivedForRequestItemTableRow(Context context, String productCode, String productName,
                                          String issuedQuantiy, String requestedQuantity) {
        super(context);
        this.productCode = productCode;
        this.productName = productName;
        this.issuedQuantity = issuedQuantiy;
        this.requestedQuantity = requestedQuantity;
        init(context);
    }

    public ReceivedForRequestItemTableRow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TextView getTvRequestedQuantity() {
        return tvRequestedQuantity;
    }

    public void setTvRequestedQuantity(TextView tvRequestedQuantity) {
        this.tvRequestedQuantity = tvRequestedQuantity;
    }

    public String getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(String requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public TextView getTvProductName() {
        return tvProductName;
    }

    public void setTvProductName(TextView tvProductName) {
        this.tvProductName = tvProductName;
    }

    public TextView getTvIssuedQuantity() {
        return tvIssuedQuantity;
    }

    public void setTvIssuedQuantity(TextView tvIssuedQuantity) {
        this.tvIssuedQuantity = tvIssuedQuantity;
    }

    public EditText getEtReceivedQuantity() {
        return etReceivedQuantity;
    }

    public void setEtReceivedQuantity(EditText etReceivedQuantity) {
        this.etReceivedQuantity = etReceivedQuantity;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getIssuedQuantity() {
        return issuedQuantity;
    }

    public void setIssuedQuantity(String issuedQuantity) {
        this.issuedQuantity = issuedQuantity;
    }

    public String getReceivedQuantity() {
        return receivedQuantity;
    }

    public void setReceivedQuantity(String receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }

    private void init(Context context){
//        TableLayout receivedTableLayout = (TableLayout) findViewById(R.id.receiveTableLayout);

        LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
//        TableRow trReceivedItem = new TableRow(context);
        this.setLayoutParams(params);


        tvProductName = new TextView(context);
        tvProductName.setText(this.productName);
        tvProductName.setLayoutParams(params);
//        tvProductName.setGravity(View.TEXT_ALIGNMENT_CENTER);
        this.addView(tvProductName);

        tvRequestedQuantity = new TextView(context);
        tvRequestedQuantity.setText(this.requestedQuantity);
        tvRequestedQuantity.setLayoutParams(params);
//        tvRequestedQuantity.setGravity(View.TEXT_ALIGNMENT_CENTER);
        this.addView(tvRequestedQuantity);

        tvIssuedQuantity = new TextView(context);
        tvIssuedQuantity.setText(this.issuedQuantity);
        tvIssuedQuantity.setLayoutParams(params);
//        tvIssuedQuantity.setGravity(View.TEXT_ALIGNMENT_CENTER);
        this.addView(tvIssuedQuantity);

        etReceivedQuantity = new EditText(context);
        etReceivedQuantity.setText(getResources().getString(R.string.default_currency_amount));
        etReceivedQuantity.setLayoutParams(params);
//        etReceivedQuantity.setGravity(View.TEXT_ALIGNMENT_CENTER);
        this.addView(etReceivedQuantity);

//        receivedTableLayout.addView(trReceivedItem, params);
    }
}

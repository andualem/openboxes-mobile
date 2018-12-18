package org.health.supplychain.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import org.health.supplychain.R;

public class IssuedItemTableRow extends TableRow{

    private TextView tvProductName;
    private TextView tvRequestedQuantity;
    private EditText etIssuedQuantity;

    private String productCode;
    private String productName;
    private String requestedQuantity;
    private String issuedQuantity;

    public IssuedItemTableRow(Context context, String productCode, String productName, String requestedQuantity) {
        super(context);
        this.productCode = productCode;
        this.productName = productName;
        this.requestedQuantity = requestedQuantity;
        init(context);
    }

    public IssuedItemTableRow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public TextView getTvProductName() {
        return tvProductName;
    }

    public void setTvProductName(TextView tvProductName) {
        this.tvProductName = tvProductName;
    }

    public TextView getTvRequestedQuantity() {
        return tvRequestedQuantity;
    }

    public void setTvRequestedQuantity(TextView tvRequestedQuantity) {
        this.tvRequestedQuantity = tvRequestedQuantity;
    }

    public EditText getEtIssuedQuantity() {
        return etIssuedQuantity;
    }

    public void setEtIssuedQuantity(EditText etIssuedQuantity) {
        this.etIssuedQuantity = etIssuedQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(String requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public String getIssuedQuantity() {
        return issuedQuantity;
    }

    public void setIssuedQuantity(String issuedQuantity) {
        this.issuedQuantity = issuedQuantity;
    }

    private void init(Context context){

        LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(params);


        tvProductName = new TextView(context);
        tvProductName.setText(this.productName);
        tvProductName.setLayoutParams(params);
//        tvProductName.setGravity(View.TEXT_ALIGNMENT_CENTER);
        this.addView(tvProductName);

        tvRequestedQuantity = new TextView(context);
        tvRequestedQuantity.setText(this.requestedQuantity);
        tvRequestedQuantity.setLayoutParams(params);
//        tvProductName.setGravity(View.TEXT_ALIGNMENT_CENTER);
        this.addView(tvRequestedQuantity);

        etIssuedQuantity = new EditText(context);
        etIssuedQuantity.setLayoutParams(params);
//        tvProductName.setGravity(View.TEXT_ALIGNMENT_CENTER);
        this.addView(etIssuedQuantity);

//        receivedTableLayout.addView(trReceivedItem, params);
    }
}

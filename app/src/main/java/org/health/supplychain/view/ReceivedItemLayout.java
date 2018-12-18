package org.health.supplychain.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.health.supplychain.R;

public class ReceivedItemLayout extends LinearLayout {

    private TextView tvProductName, tvLotNumber, tvExpirationDate, tvUnitOfMeasure;
    private TextView tvIssuedQuantity;
    private EditText etReceivedQuantity, etRemark;

    private String productName, lotNumber, expiryDate, unitOfMeasure, issuedQuantity;


    public ReceivedItemLayout(Context context) {
        super(context);
        init(context);
    }

    public ReceivedItemLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ReceivedItemLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ReceivedItemLayout(Context context, String productName, String lotNumber, String expiryDate,
                              String unitOfMeasure, String issuedQuantity) {
        super(context);
        this.productName = productName;
        this.lotNumber = lotNumber;
        this.expiryDate = expiryDate;
        this.unitOfMeasure = unitOfMeasure;
        this.issuedQuantity = issuedQuantity;
        init(context);
    }

    public TextView getTvProductName() {
        return tvProductName;
    }

    public void setTvProductName(TextView tvProductName) {
        this.tvProductName = tvProductName;
    }

    public TextView getTvLotNumber() {
        return tvLotNumber;
    }

    public void setTvLotNumber(TextView tvLotNumber) {
        this.tvLotNumber = tvLotNumber;
    }

    public TextView getTvExpirationDate() {
        return tvExpirationDate;
    }

    public void setTvExpirationDate(TextView tvExpirationDate) {
        this.tvExpirationDate = tvExpirationDate;
    }

    public TextView getTvUnitOfMeasure() {
        return tvUnitOfMeasure;
    }

    public void setTvUnitOfMeasure(TextView tvUnitOfMeasure) {
        this.tvUnitOfMeasure = tvUnitOfMeasure;
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

//    public TextView getTvRemark() {
//        return tvRemark;
//    }
//
//    public void setTvRemark(TextView tvRemark) {
//        this.tvRemark = tvRemark;
//    }

    public EditText getEtRemark() {
        return etRemark;
    }

    public void setEtRemark(EditText etRemark) {
        this.etRemark = etRemark;
    }

    /* private TextView tvProductName, tvLotNumber, tvExpirationDate, tvUnitOfMeasure;
    private TextView tvIssuedQuantity, tvRemark;
    private EditText etReceivedQuantity; */

    private void init(Context context) {
        inflate(getContext(), R.layout.receive_item_layout, this);
        tvProductName = findViewById(R.id.tvProduct);
        tvLotNumber = findViewById(R.id.tvLotNumber);
        tvExpirationDate = findViewById(R.id.tvExpiryDate);
        tvUnitOfMeasure = findViewById(R.id.tvMeasurementUnit);
        tvIssuedQuantity = findViewById(R.id.tvIssuedQuantity);
        etReceivedQuantity = findViewById(R.id.etReceivedQuantity);
        etRemark = findViewById(R.id.etReceiveRemark);

        tvProductName.setText(this.productName);
        tvLotNumber.setText(this.lotNumber);
        tvExpirationDate.setText(this.expiryDate);
        tvUnitOfMeasure.setText(this.unitOfMeasure);
        tvIssuedQuantity.setText(this.issuedQuantity);

    }
}

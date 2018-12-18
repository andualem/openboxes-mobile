//package org.health.supplychain.view;
//
//import android.content.Context;
//import android.content.DialogInterface;
//import android.support.v7.app.AlertDialog;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.Toast;
//
//import org.health.supplychain.R;
//import org.health.supplychain.constants.Constants;
//
//import java.util.Calendar;
//
///**
// * Created by aworkneh on 8/16/2018.
// */
//
//public class DateRangeDialogView {
//
//    private Context context;
//
//    public DateRangeDialogView(Context context){
//        this.context = context;
//    }
//
//    private void showDateRangeDialog(){
//        AlertDialog.Builder dateRangeDialog = new AlertDialog.Builder(context);
//        dateRangeDialog.setTitle(context.getString(R.string.change_daterange));
//        LayoutInflater inflater = context.getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.date_range_layout, null);
//        dateRangeDialog.setView(dialogView);
//
//        DatePicker dpDateFrom, dpDateTo;
//        Button btnCalculate;
//
//        dpDateFrom = (DatePicker) dialogView.findViewById(R.id.dpDateFrom);
//        dpDateTo = (DatePicker) dialogView.findViewById(R.id.dpDateTo);
//
//        dateRangeDialog.setPositiveButton(context.getResources().getString(R.string.calculate),
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int i) {
//                        dateFrom = getDateFromDatePicker(dpDateFrom, Constants.SOD);
//                        dateTo = getDateFromDatePicker(dpDateTo, Constants.EOD);
//                        if(dateTo < dateFrom){
//                            Toast.makeText(getApplicationContext(), getString(R.string.invalid_date_range), Toast.LENGTH_LONG).show();
//                            return;
//                        }
//
//                        displayDateRange();
//                    }
//                });
//
//        dateRangeDialog.setNegativeButton(getResources().getString(R.string.cancel),
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//
//        dateRangeDialog.show();
//    }
//
//    private long getDateFromDatePicker(DatePicker datePicker, String SODEOD){
//        int day = datePicker.getDayOfMonth();
//        int month = datePicker.getMonth();
//        int year =  datePicker.getYear();
//        Calendar calendar = Calendar.getInstance();
//        if(SODEOD == Constants.SOD)
//            calendar.set(year, month, day, 0, 0);
//        else if(SODEOD == Constants.EOD)
//            calendar.set(year, month, day, 23, 59);
//        else
//            calendar.set(year, month, day);
//        return calendar.getTimeInMillis();
//    }
//}

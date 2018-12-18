
package org.health.supplychain.utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;


public class SafeUIBlockingUtility {


    public String utilityTitle = "Working";
    public String utilityMessage = "Syncing ...";

    private ProgressDialog progressDialog;

    private Context context;

    public SafeUIBlockingUtility(Context context){
        this.context = context;
        progressDialog = new ProgressDialog(this.context);
        progressDialog.setMessage(utilityMessage);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle(utilityTitle);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    public SafeUIBlockingUtility(Context context, String utilityTitle, String utilityMessage){
        this.context = context;
        progressDialog = new ProgressDialog(this.context);
        progressDialog.setMessage(utilityMessage);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle(utilityTitle);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }


    public void safelyBlockUI(){
        progressDialog.show();
    }

    public void safelyUnBlockUI(){
    	if(progressDialog.isShowing())
    		progressDialog.dismiss();
    }

    public String getUtilityTitle() {
        return utilityTitle;
    }

    public void setUtilityTitle(String utilityTitle) {
        this.utilityTitle = utilityTitle;
        progressDialog.setTitle(utilityTitle);
    }

    public String getUtilityMessage() {
        return utilityMessage;
    }

    public void setUtilityMessage(String utilityMessage) {
        this.utilityMessage = utilityMessage;
        progressDialog.setMessage(utilityMessage);
    }

    public void safelyUnblockUIForFailure(String tag, String message){

        progressDialog.dismiss();
        Toast.makeText(context, "Some Problem Executing Request", Toast.LENGTH_SHORT).show();
        Log.i(tag,message);

    }




}

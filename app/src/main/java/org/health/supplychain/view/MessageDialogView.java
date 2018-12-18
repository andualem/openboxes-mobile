package org.health.supplychain.view;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import org.health.supplychain.R;

/**
 * Created by Aworkneh on 8/30/2018.
 */

public class MessageDialogView {

    private Context context;

    public MessageDialogView(Context context){
        this.context = context;
    }

    public void showMessage(String title, String message, int type){
        AlertDialog.Builder messageDialog = new AlertDialog.Builder(context, R.style.CustomAlertDialog);
        messageDialog.setTitle(title);
        messageDialog.setMessage(R.string.new_line).setMessage(message);

        messageDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        messageDialog.show();
    }
}

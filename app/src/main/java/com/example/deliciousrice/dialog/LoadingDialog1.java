package com.example.deliciousrice.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.deliciousrice.R;

public class LoadingDialog1 {

    private Activity activity;
    private AlertDialog dialog;

    public LoadingDialog1(Activity myActivity){
        activity = myActivity;
    }

    public void StartLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.customdialog, null));
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();
    }

    public void dismissDialog(){
        dialog.dismiss();
    }

}

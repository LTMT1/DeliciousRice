package com.example.deliciousrice.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ProgressBar;

import com.example.deliciousrice.R;
import com.github.ybq.android.spinkit.style.Circle;

public class LoadingDialog {
    private Context context;
    private Dialog dialog;

    public LoadingDialog(Context context)
    {
        this.context = context;
    }

    public void startLoadingDialog(String title)
    {
        dialog = new Dialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ProgressBar prgLoadingDialog = dialog.findViewById(R.id.prgLoadingDialog);
        prgLoadingDialog.setIndeterminateDrawable(new Circle());
        dialog.create();
        dialog.show();
    }


    public void dismisDialog()
    {
        dialog.dismiss();
    }
}

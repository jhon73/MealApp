package com.mealapp.baseclass;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;




public class BaseAppCompactActivity extends AppCompatActivity {
    private Dialog dialog;
    private boolean adversitement;
//    private ProgressBarDialog progressBarDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        progressBarDialog = new ProgressBarDialog(this);
    }


//    public void showDialog() {
//        progressBarDialog.showDialog();
//    }
//
//    public void dismissDialog() {
//        progressBarDialog.dismissDialog();
//    }

    protected void replaceFragment(Fragment mFragment, String title) {
    }


    public void addFragment(Fragment mFragment, String title) {
    }


    protected void replaceSharedFragment(Fragment first, Fragment second, String title, String[] transiton, View... view) {
    }


//    protected MyPref getMyPref() {
//        return myPref;
//    }
//
//    protected void showDialog() {
//        dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
//        dialog.setContentView(R.layout.dialog_loader);
//        dialog.setCancelable(false);
//        dialog.show();
//    }
    public boolean isAdversitement() {
        return adversitement;
    }

    public void setAdversitement(boolean adversitement) {
        this.adversitement = adversitement;
    }

    protected void dismissDialog() {
        if (dialog != null)
            dialog.dismiss();
    }


}
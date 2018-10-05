package com.robotsoftware.orderingapp.activities.controllers.services;

import android.app.ProgressDialog;

public class Progress {

    /**
     * this for show progress dialog
     */
    public void showProgressDialog(ProgressDialog progressDialog) {
        progressDialog.setMessage("Ordering ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    /**
     * this for hide progress dialog
     */
    public void hideProgressDialog(ProgressDialog progressDialog) {
        progressDialog.dismiss();
    }
}

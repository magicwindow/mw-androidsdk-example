package com.magicwindow.deeplink.app;

import android.app.Dialog;

import cn.salesuite.saf.async.AsyncTask;
import cn.salesuite.saf.utils.SAFUtils;

/**
 * Created by Tony Shen on 16/4/29.
 */
public abstract class BaseAsyncTask<Params, Progress, Result> extends
        AsyncTask<Params, Integer, Result> {
    private Dialog mDialog;

    public BaseAsyncTask() {
    }

    public BaseAsyncTask(Dialog dialog) {
        this.mDialog = dialog;
    }

    @Override
    protected void onPreExecute() {
        if (mDialog != null) {
            mDialog.show();
        }
        super.onPreExecute();
    }

    @Override
    protected Result doInBackground(Params... arg0) {
        if (doCheck()) {
            return onExecute(arg0);
        }
        return null;
    }

    protected abstract Result onExecute(Params... arg0);

    private boolean doCheck() {
        if (SAFUtils.checkNetworkStatus(MWApplication.getInstance())) {
            return true;
        }
        return false;
    }

    @Override
    protected void onPostExecute(Result result) {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
        super.onPostExecute(result);

        if (result == null) {
            return;
        }

        if (isCancelled()) {
            return;
        }
    }
}


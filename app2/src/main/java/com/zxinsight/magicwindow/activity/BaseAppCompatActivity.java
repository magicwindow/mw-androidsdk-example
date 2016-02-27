package com.zxinsight.magicwindow.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.zxinsight.magicwindow.view.dialog.LoadingDialog;

import java.lang.ref.WeakReference;

public class BaseAppCompatActivity extends AppCompatActivity {

    protected Context mContext;
    private Dialog mDialog;
    protected Handler mHandler = new SafeHandler(this);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissDialog();
    }

    @Override
    protected void onPause() {
//        TrackAgent.currentEvent().onPause(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
//        TrackAgent.currentEvent().onResume(this);
        super.onResume();
    }

    /**
     * 显示loading
     */
    protected Dialog showLoading() {
        dismissDialog();
        mDialog = new LoadingDialog(mContext);
        mDialog.show();

        return mDialog;
    }

    /**
     * 关闭loading
     */
    protected void dismissDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    /**
     * 防止内部Handler类引起内存泄露
     *
     * @author Tony Shen
     */
    public static class SafeHandler extends Handler {
        private final WeakReference<Activity> mActivity;

        public SafeHandler(Activity activity) {
            mActivity = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mActivity.get() == null) {
                return;
            }
        }
    }
}


/**
 *
 */
package com.magicwindow.deeplink.app;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;

import com.magicwindow.deeplink.ui.dialog.LoadingDialog;
import com.magicwindow.deeplink.utils.EventBusManager;
import com.zxinsight.TrackAgent;

import cn.salesuite.saf.app.SAFActivity;
import cn.salesuite.saf.eventbus.EventBus;
import cn.salesuite.saf.inject.Injector;
import cn.salesuite.saf.log.L;
import cn.salesuite.saf.utils.SAFUtils;


/**
 * 工程的基类Activity
 * @author Tony Shen
 *
 */
public class BaseActivity extends SAFActivity {

    protected MWApplication app;
    protected EventBus eventBus;
    protected Context mContext;
    protected Dialog mDialog;
    protected Handler mHandler = new SafeHandler(this);

    private BroadcastReceiver mNetworkStateReceiver;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        mNetworkStateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (!SAFUtils.checkNetworkStatus(context)) {       // 网络断了的情况
                    toast(com.magicwindow.deeplink.R.string.network_error);
                }
            }

        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetworkStateReceiver, filter);

        app = MWApplication.getInstance();

        eventBus = EventBusManager.getInstance();
        eventBus.register(this);

        L.init(this);
    }

    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        Injector.injectInto(this);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mNetworkStateReceiver);
        eventBus.unregister(this);
        dismissDialog();
        super.onDestroy();
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


    @Override
    protected void onPause() {
        TrackAgent.currentEvent().onPause(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        TrackAgent.currentEvent().onResume(this);
        super.onResume();
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

}

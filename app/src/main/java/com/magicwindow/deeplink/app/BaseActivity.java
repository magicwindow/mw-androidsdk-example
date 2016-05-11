/**
 *
 */
package com.magicwindow.deeplink.app;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import com.magicwindow.deeplink.ui.dialog.LoadingDialog;
import com.magicwindow.deeplink.utils.EventBusManager;
import com.zxinsight.TrackAgent;

import cn.salesuite.saf.app.SAFActivity;
import cn.salesuite.saf.eventbus.EventBus;
import cn.salesuite.saf.inject.Injector;
import cn.salesuite.saf.log.L;


/**
 * 工程的基类Activity
 *
 * @author Tony Shen
 */
public class BaseActivity extends SAFActivity {

    protected MWApplication app;
    protected EventBus eventBus;
    protected Dialog mDialog;
    protected Handler mHandler = new SafeHandler(this);
    protected Context mContext;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
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
        eventBus.unregister(this);
        dismissDialog();
        super.onDestroy();
    }

    /**
     * 显示loading
     */
    protected Dialog showLoading(Context context) {
        dismissDialog();
        mDialog = new LoadingDialog(context);
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



}

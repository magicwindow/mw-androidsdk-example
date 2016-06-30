package com.magicwindow.deeplink.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.magicwindow.deeplink.ui.dialog.LoadingDialog;
import com.magicwindow.deeplink.utils.EventBusManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zxinsight.Session;
import com.zxinsight.TrackAgent;

import java.lang.ref.WeakReference;

import cn.salesuite.saf.eventbus.EventBus;
import cn.salesuite.saf.inject.Injector;
import cn.salesuite.saf.log.L;
import cn.salesuite.saf.utils.SAFUtils;
import cn.salesuite.saf.utils.ToastUtils;

//import android.net.ConnectivityManager;

/**
 * Created by Tony Shen on 15/11/26.
 */
public class BaseAppCompatActivity extends AppCompatActivity {

    protected MWApplication app;
    protected EventBus eventBus;
    protected Context mContext;
    public String TAG;
    protected Handler mHandler = new SafeHandler(this);
    private LoadingDialog mDialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        app = (MWApplication) this.getApplication();
        TAG = SAFUtils.makeLogTag(this.getClass());
        mContext = this;
        app = (MWApplication) MWApplication.getInstance();

        eventBus = EventBusManager.getInstance();
        eventBus.register(this);
        L.init(this.getClass());
        Log.e(TAG, "onCreate:" + getIntent().getData());
    }

    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        Injector.injectInto(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        ImageLoader.getInstance().clearMemoryCache();
    }

    @Override
    @TargetApi(14)
    public void onTrimMemory(int level) {
        if (SAFUtils.isICSOrHigher()) {
            super.onTrimMemory(level);

            if (level >= ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW) {
                ImageLoader.getInstance().clearMemoryCache();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissDialog();
        eventBus.unregister(this);

    }

    /**
     * @param message toast的内容
     */
    protected void toast(String message) {
        ToastUtils.showShort(this, message);
    }

    /**
     * @param resId toast的内容来自String.xml
     */
    protected void toast(int resId) {
        ToastUtils.showShort(this, resId);
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


    @Override
    protected void onPause() {
        Session.onPause(this);
        Log.e(TAG, "onPause:" + getClass().getName());

        super.onPause();
    }

    @Override
    protected void onResume() {
        Session.onResume(this);
        Log.e(TAG, "onResume:" + getClass().getName());
        super.onResume();
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
}


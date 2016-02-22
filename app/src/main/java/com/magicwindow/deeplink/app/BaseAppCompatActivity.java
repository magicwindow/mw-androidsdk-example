package com.magicwindow.deeplink.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.magicwindow.deeplink.utils.EventBusManager;
import com.zxinsight.TrackAgent;

import java.lang.ref.WeakReference;

import cn.salesuite.saf.eventbus.EventBus;
import cn.salesuite.saf.inject.Injector;
import cn.salesuite.saf.log.L;
import cn.salesuite.saf.utils.SAFUtils;
import cn.salesuite.saf.utils.ToastUtils;

/**
 * Created by Tony Shen on 15/11/26.
 */
public class BaseAppCompatActivity extends AppCompatActivity {

    protected MWApplication app;
    protected EventBus eventBus;
    protected Context mContext;
    public String TAG;
    public int networkType;
    public String networkName;
    protected Handler mHandler = new SafeHandler(this);
    private BroadcastReceiver mNetworkStateReceiver;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        app = (MWApplication) this.getApplication();
        TAG = SAFUtils.makeLogTag(this.getClass());
        addActivityToManager(this);

        mNetworkStateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (!SAFUtils.checkNetworkStatus(context)) {       // 网络断了的情况
                    toast(com.magicwindow.deeplink.R.string.network_error);
                    return;
                }
            }

        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetworkStateReceiver, filter);

        mContext = this;
        app = (MWApplication) MWApplication.getInstance();

        eventBus = EventBusManager.getInstance();
        eventBus.register(this);
        L.init(this.getClass());
    }

    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        Injector.injectInto(this);
    }

    protected  void addActivityToManager(Activity act) {
        Log.i(TAG, "addActivityToManager");
        if (!app.activityManager.contains(act)) {
            Log.i(TAG , "addActivityToManager, packagename = " + act.getClass().getName()) ;
            app.activityManager.add(act);
        }
    }

    protected void closeAllActivities() {
        Log.i(TAG, "closeAllActivities");
        for (final Activity act : app.activityManager) {
            if (act != null) {
                act.finish();
            }
        }
    }

    protected  void delActivityFromManager(Activity act) {
        Log.i(TAG,"delActivityFromManager") ;
        if (app.activityManager.contains(act)) {
            app.activityManager.remove(act);
        }
    }

    /**
     * 返回当前运行activity的名称
     * @return
     */
    protected String getCurrentActivityName() {
        int size = app.activityManager.size();
        if (size > 0) {
            return app.activityManager.get(size-1).getClass().getName();
        }
        return null;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        app.imageLoader.clearMemCache();
    }

    @Override
    @TargetApi(14)
    public void onTrimMemory(int level) {
        if (SAFUtils.isICSOrHigher()) {
            super.onTrimMemory(level);

            if (level >= ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW) {
                app.imageLoader.clearCache();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        delActivityFromManager(this);
        unregisterReceiver(mNetworkStateReceiver);
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
     * @author Tony Shen
     *
     */
    public static class SafeHandler extends Handler{
        private final WeakReference<Activity> mActivity;
        public SafeHandler(Activity activity) {
            mActivity = new WeakReference<Activity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            if(mActivity.get() == null) {
                return;
            }
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


/**
 * 
 */
package com.magicwindow.deeplink.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
//import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;

import com.magicwindow.deeplink.utils.EventBusManager;
import com.zxinsight.TrackAgent;
import com.magicwindow.deeplink.R;

import cn.salesuite.saf.app.SAFFragmentActivity;
import cn.salesuite.saf.eventbus.EventBus;
import cn.salesuite.saf.inject.Injector;
import cn.salesuite.saf.log.L;
import cn.salesuite.saf.utils.SAFUtils;

/**
 * @author Tony Shen
 *
 */
public class BaseFragmentActivity extends SAFFragmentActivity {

	protected MWApplication app;
	protected EventBus eventBus;
    protected Context mContext;

    protected Handler mHandler = new SafeHandler(this);
    protected boolean firstResume = true;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		mContext = this;
		app = (MWApplication) MWApplication.getInstance();
		
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
        super.onDestroy();
		MWApplication.getInstance().getRefWatcher().watch(this);
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

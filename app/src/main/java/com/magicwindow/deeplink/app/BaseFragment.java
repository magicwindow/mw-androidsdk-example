/**
 * 
 */
package com.magicwindow.deeplink.app;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;

import com.magicwindow.deeplink.utils.EventBusManager;

import cn.salesuite.saf.app.SAFFragment;
import cn.salesuite.saf.eventbus.EventBus;
import cn.salesuite.saf.log.L;

/**
 * @author Tony Shen
 *
 */
public class BaseFragment extends SAFFragment {

	protected EventBus eventBus;
	protected MWApplication app;

	protected Handler mHandler = new Handler();
	protected boolean firstResume = true;
	protected FragmentManager fmgr;

	public BaseFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		app = MWApplication.getInstance();
		eventBus = EventBusManager.getInstance();
		eventBus.register(this);
		fmgr = getFragmentManager();

		L.init(this);
	}

	@Override
	public void onDestroy() {
		eventBus.unregister(this);
		super.onDestroy();
	}
}

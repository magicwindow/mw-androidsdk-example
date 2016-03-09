/**
 * 
 */
package com.magicwindow.deeplink.app;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.magicwindow.deeplink.utils.EventBusManager;

import cn.salesuite.saf.app.SAFFragment;
import cn.salesuite.saf.eventbus.EventBus;
import cn.salesuite.saf.log.L;

/**
 * @author Tony Shen
 *
 */
public class BaseFragment extends SAFFragment implements RefreshView {

	protected EventBus eventBus;
	protected MWApplication app;

	protected Handler mHandler = new Handler();
	protected FragmentManager fmgr;

	public BaseFragment() {
	}

	@Override
	public void initView() {

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		initView();
		return super.onCreateView(inflater,container,savedInstanceState);
	}

	@Override
	public void onDestroy() {
		eventBus.unregister(this);
		super.onDestroy();
	}

	@Override
	public void onResume() {
		super.onResume();
		initView();
	}
}

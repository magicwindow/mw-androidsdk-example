/**
 * 
 */
package com.magicwindow.deeplink.ui.dialog;

import android.app.Dialog;
import android.content.Context;

import cn.salesuite.saf.eventbus.EventBus;
import cn.salesuite.saf.inject.Injector;

/**
 * @author Tony Shen
 *
 */
public class BaseDialog extends Dialog {

	protected Context mContext;
	protected EventBus eventBus;
	
	public BaseDialog(Context context) {
		super(context);
		mContext = context;
	}
	
	public BaseDialog(Context context, int theme) {
		super(context, theme);
		mContext = context;
	}
	
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		Injector.injectInto(this);
	}
}

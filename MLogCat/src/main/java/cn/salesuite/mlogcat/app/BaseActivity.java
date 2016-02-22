/**
 * 
 */
package cn.salesuite.mlogcat.app;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.salesuite.saf.inject.Injector;
import cn.salesuite.saf.log.L;
import cn.salesuite.saf.utils.ToastUtils;

/**
 * 工程的基类Activity
 * @author Tony Shen
 *
 */
public class BaseActivity extends Activity {
	
	protected Handler mHandler = new MyHandler(this);
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		L.init(BaseActivity.class);
	}
	
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		Injector.injectInto(this);
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
    public static class MyHandler extends Handler{
	    private final WeakReference<Activity> mActivity;
	    public MyHandler(Activity activity) {
	        mActivity = new WeakReference<Activity>(activity);
	    }
	    @Override
	    public void handleMessage(Message msg) {
	        if(mActivity.get() == null) {
	            return;
	        }
	    }
	}
}

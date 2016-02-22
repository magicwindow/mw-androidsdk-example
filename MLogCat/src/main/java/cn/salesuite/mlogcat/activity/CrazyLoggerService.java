package cn.salesuite.mlogcat.activity;

import java.util.Date;

import cn.salesuite.saf.log.L;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;

/**
 * just writes a bunch of logs.  to be used during debugging and testing.
 * @author nolan
 *
 */
public class CrazyLoggerService extends IntentService {
	
	private static final long INTERVAL = 300;
	
	private boolean kill = false;

	public CrazyLoggerService() {
		super("CrazyLoggerService");
		L.init(CrazyLoggerService.class);
	}

	protected void onHandleIntent(Intent intent) {
	
		L.d("onHandleIntent()");
		
		while (!kill) {
		
			try {
				Thread.sleep(INTERVAL);
			} catch (InterruptedException e) {
				L.e("error");
			}
			Date date = new Date();
			L.i("Log message " + date + " " + (date.getTime() % 1000));
		
		}
	
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		kill = true;
	}
	
}

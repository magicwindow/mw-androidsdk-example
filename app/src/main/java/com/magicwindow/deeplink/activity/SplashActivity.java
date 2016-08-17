package com.magicwindow.deeplink.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import com.igexin.sdk.PushManager;
import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.UrlDispatcher;
import com.magicwindow.deeplink.app.BaseAppCompatActivity;
import com.magicwindow.deeplink.config.Config;
import com.magicwindow.deeplink.prefs.AppPrefs;
import com.zxinsight.MLink;
import com.zxinsight.MWConfiguration;
import com.zxinsight.MagicWindowSDK;

import cn.salesuite.saf.log.L;

/**
 * Created by Tony Shen on 15/11/23.
 */
public class SplashActivity extends BaseAppCompatActivity {

    AppPrefs appPrefs;
    private final String TAG = "SplashActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initData();
        initGetui();
        Log.e(TAG, "onCreate");
    }

    private void initGetui() {
        PushManager.getInstance().initialize(this.getApplicationContext());
    }

    private void initData() {
        appPrefs = AppPrefs.get(this);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels; // 屏幕宽度（像素）
        int height = metric.heightPixels; // 屏幕高度（像素）
        float density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5 /2.0）
        L.i("device screen: " + width + "*" + height + " desity: " + density);
        Config.height = height;
        Config.width = width;
        Config.density = density;
        initMW();
        //@mw 注册mLink
        UrlDispatcher.register(this);
//        MagicWindowSDK.getMLink().registerWithAnnotation(this);
        //@mw mLink跳转 start
        Uri mLink = getIntent().getData();
        Log.e(TAG, "Splash mLink = " + mLink);
        goHomeActivity();
        if (mLink != null) {
            MagicWindowSDK.getMLink().router(mLink);
        } else {
            MLink.getInstance(this).checkYYB();
        }
        finish();
        //mLink跳转 end
    }

    //@mw 初始化魔窗
    private void initMW() {
        long t1 = System.currentTimeMillis();
        MWConfiguration config = new MWConfiguration(this);
        long t2 = System.currentTimeMillis();

        config.setChannel("魔窗")
                .setDebugModel(true)
                .setPageTrackWithFragment(true)
                .setMLinkOpen()
                .setSharePlatform(MWConfiguration.ORIGINAL);
        long t3 = System.currentTimeMillis();

        MagicWindowSDK.initSDK(config);
        long t4 = System.currentTimeMillis();
        long time = t2 - t1;
        long time1 = t3 - t2;
        long time2 = t4 - t3;
        long time3 = t4 - t1;
        Log.e("aaron", "time = " + time + ",time1 = " + time1 + ",time2 = " + time2 + ",time3 = " + time3);

    }

    private void goHomeActivity() {

        if (appPrefs != null && appPrefs.getLastVersion() == null) { // 肯定是第一次安装，进入学习页
            appPrefs.setLastVersion(app.version);
            Intent i = new Intent(SplashActivity.this, LearnActivity.class);
            i.putExtra(LearnActivity.TYPE, LearnActivity.FROM_SPLASH);
            startActivity(i);
        } else {
            if (appPrefs != null && appPrefs.getLastVersion().equals(app.version)) { // 进入MainActivity
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
            } else {
                if (appPrefs != null) {
                    appPrefs.setLastVersion(app.version);
                }
                Intent i = new Intent(SplashActivity.this, LearnActivity.class);
                i.putExtra(LearnActivity.TYPE, LearnActivity.FROM_SPLASH);
                startActivity(i);
            }
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.setIntent(intent);

        Uri mLink = intent.getData();
        if (mLink != null) {
            MagicWindowSDK.getMLink().router(mLink);
        } else {
            MLink.getInstance(this).checkYYB();
        }
    }


}

package com.magicwindow.deeplink.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.UrlDispatcher;
import com.magicwindow.deeplink.app.BaseActivity;
import com.magicwindow.deeplink.config.Config;
import com.magicwindow.deeplink.prefs.AppPrefs;
import com.magicwindow.deeplink.utils.NetTask;
import com.zxinsight.MagicWindowSDK;

import java.util.ArrayList;
import java.util.List;

import cn.salesuite.saf.log.L;
import cn.salesuite.saf.utils.AsyncTaskExecutor;

/**
 * Created by Tony Shen on 15/11/23.
 */
public class SplashActivity extends BaseActivity {


    AppPrefs appPrefs;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        appPrefs = AppPrefs.get(mContext);
        initData();
        initJson();
    }

    private void initJson() {
        List<String> list = new ArrayList<String>();
        list.add(Config.businessList);
        list.add(Config.o2oList);
        list.add(Config.newsList);
        list.add(Config.picList);
        list.add(Config.travelList);
        AsyncTaskExecutor.executeAsyncTask(new NetTask(this, list));
    }

    private void initData() {

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels; // 屏幕宽度（像素）
        int height = metric.heightPixels; // 屏幕高度（像素）
        float density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5 /2.0）
        L.i("device screen: " + width + "*" + height + " desity: " + density);
        Config.height = height;
        Config.width = width;
        Config.density = density;

        //@mw 注册mLink
        UrlDispatcher.register(this);
        //@mw mLink跳转 start
        Uri mLink = getIntent().getData();
        if (mLink != null) {
            MagicWindowSDK.getMLink().router(mLink);
        } else {
            loadingNext();
        }
        finish();
        //mLink跳转 end
    }

    private void loadingNext() {

        if (appPrefs.getLastVersion() == null) { // 肯定是第一次安装，进入学习页
            appPrefs.setLastVersion(app.version);
            Intent i = new Intent(mContext, LearnActivity.class);
            i.putExtra(LearnActivity.TYPE, LearnActivity.FROM_SPLASH);
            startActivity(i);
        } else {
            if (appPrefs.getLastVersion().equals(app.version)) { // 进入MainActivity
                Intent i = new Intent(mContext, MainActivity.class);
                startActivity(i);
            } else {
                appPrefs.setLastVersion(app.version);
                Intent i = new Intent(mContext, LearnActivity.class);
                i.putExtra(LearnActivity.TYPE, LearnActivity.FROM_SPLASH);
                startActivity(i);
            }
        }
        finish();
    }

    @Override
    public void onNewIntent(Intent intent) {
        this.setIntent(intent);
    }


}

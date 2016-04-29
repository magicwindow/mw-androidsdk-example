package com.magicwindow.deeplink.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.UrlDispatcher;
import com.magicwindow.deeplink.app.BaseActivity;
import com.magicwindow.deeplink.config.Config;
import com.magicwindow.deeplink.domain.DownloadResponse;
import com.magicwindow.deeplink.domain.event.UpdateAppEvent;
import com.magicwindow.deeplink.prefs.AppPrefs;
import com.magicwindow.deeplink.task.CheckUpdateTask;
import com.zxinsight.MagicWindowSDK;

import java.io.IOException;

import cn.salesuite.saf.http.rest.RestUtil;
import cn.salesuite.saf.log.L;
import cn.salesuite.saf.rxjava.RxAsyncTask;
import cn.salesuite.saf.utils.Preconditions;
import cn.salesuite.saf.utils.SAFUtils;
import cn.salesuite.saf.view.LightDialog;

/**
 * Created by Tony Shen on 15/11/23.
 */
public class SplashActivity extends BaseActivity {

    AppPrefs appPrefs;
    private LightDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initData();
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

        //@mw 注册mLink
        UrlDispatcher.register(this);
        //@mw mLink跳转 start
        Uri mLink = getIntent().getData();
        if (mLink != null) {
            MagicWindowSDK.getMLink().router(mLink);
        } else {
            CheckUpdateTask task = new CheckUpdateTask(app.version);
            task.execute(new RxAsyncTask.HttpResponseHandler() {
                @Override
                public void onSuccess(String s) {
                    if (Preconditions.isNotBlank(s)) {
                        try {
                            DownloadResponse response = RestUtil.parseAs(DownloadResponse.class,s);
                            checkUpdate(response);
                        } catch (IOException e) {
                            e.printStackTrace();
                            loadingNext();
                        }
                    } else {
                        loadingNext();
                    }

                }

                @Override
                public void onFail(Throwable throwable) {

                }
            });
        }
        finish();
        //mLink跳转 end
    }

    private void loadingNext() {

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
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
        },500);
    }

    @Override
    public void onNewIntent(Intent intent) {
        this.setIntent(intent);
    }

    public void checkUpdate(final DownloadResponse response) {

        // 只有升级状态为true并且强制升级状态为true，才强制升级， 不需要弹出选择对话框
        if (response.result.upgrade && response.result.forceUpgrade) {

            if (SAFUtils.isWiFiActive(mContext)) { // wifi情况下，弹出升级提示
                doUpdateEvent(response);
            } else {
                loadingNext();
            }
        } else if (response.result.upgrade) {
            if (dialog == null) {
                dialog = LightDialog.create(mContext, "软件更新",
                        "描述");
                dialog.setCanceledOnTouchOutside(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doUpdateEvent(response);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        loadingNext();
                    }
                });
                dialog.show();
            }
        } else {
            loadingNext();
        }

    }

    /**
     * 触发app升级下载监听者事件
     * @param response
     */
    void doUpdateEvent(DownloadResponse response) {

        UpdateAppEvent event = new UpdateAppEvent();
        event.url = response.result.newVersionUrl;
        eventBus.post(event);
    }


}

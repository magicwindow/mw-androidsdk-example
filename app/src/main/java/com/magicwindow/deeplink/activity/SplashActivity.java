package com.magicwindow.deeplink.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;

import com.alibaba.fastjson.JSONObject;
import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.UrlDispatcher;
import com.magicwindow.deeplink.app.BaseActivity;
import com.magicwindow.deeplink.app.BaseAsyncTask;
import com.magicwindow.deeplink.config.Config;
import com.magicwindow.deeplink.domain.DownloadResponse;
import com.magicwindow.deeplink.domain.event.UpdateAppEvent;
import com.magicwindow.deeplink.download.UpdateDownloadTaskListener;
import com.magicwindow.deeplink.prefs.AppPrefs;
import com.zxinsight.MagicWindowSDK;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import cn.salesuite.saf.download.DownloadManager;
import cn.salesuite.saf.eventbus.Subscribe;
import cn.salesuite.saf.http.rest.HttpResponseHandler;
import cn.salesuite.saf.http.rest.RestClient;
import cn.salesuite.saf.http.rest.RestException;
import cn.salesuite.saf.http.rest.RestUtil;
import cn.salesuite.saf.http.rest.UrlBuilder;
import cn.salesuite.saf.log.L;
import cn.salesuite.saf.utils.AsyncTaskExecutor;
import cn.salesuite.saf.utils.SAFUtils;
import cn.salesuite.saf.utils.StringUtils;
import cn.salesuite.saf.view.LightDialog;

/**
 * Created by Tony Shen on 15/11/23.
 */
public class SplashActivity extends BaseActivity {

    AppPrefs appPrefs;
    private LightDialog dialog;
    private ProgressDialog pBar;

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
            finish();
        } else {
            CheckUpdateTask checkUpdateTask = new CheckUpdateTask();
            AsyncTaskExecutor.executeAsyncTask(checkUpdateTask);
        }
        //mLink跳转 end
    }

    private void loadingNext() {

        if (appPrefs != null && appPrefs.getLastVersion() == null) { // 肯定是第一次安装，进入学习页
            appPrefs.setLastVersion(app.version);
            Intent i = new Intent(SplashActivity.this, LearnActivity.class);
            i.putExtra(LearnActivity.TYPE, LearnActivity.FROM_SPLASH);
            startActivity(i);
            finish();
        } else {
            if (appPrefs != null && appPrefs.getLastVersion().equals(app.version)) { // 进入MainActivity
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            } else {
                if (appPrefs != null) {
                    appPrefs.setLastVersion(app.version);
                }
                Intent i = new Intent(SplashActivity.this, LearnActivity.class);
                i.putExtra(LearnActivity.TYPE, LearnActivity.FROM_SPLASH);
                startActivity(i);
                finish();
            }
        }
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
                        response.result.desc);
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
                                finish();
                            }
                        });
                if (!SplashActivity.this.isFinishing() && !dialog.isShowing()) {
                    dialog.show();
                }
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

    /**
     * 升级app的事件
     *
     * @param event
     */
    @Subscribe
    public void onUpdateAppEvent(UpdateAppEvent event) {

        if (StringUtils.isNotBlank(event.url) && SAFUtils.checkNetworkStatus(mContext)) {
            String url = event.url;
            String path = Environment.getExternalStorageDirectory().getPath() + Config.DIR + "/";
            String fileName = "mwdemo" + System.currentTimeMillis()+ ".apk";
            final String apkPathUrl = path + fileName;
            DownloadManager.getInstance(app).startDownload(url, path, fileName,
                    new UpdateDownloadTaskListener(pBar, mContext, apkPathUrl));
            finish();
        } else {
            loadingNext();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    class CheckUpdateTask extends BaseAsyncTask<String, String[], Integer> {

        private DownloadResponse response;

        @Override
        protected Integer onExecute(String... arg0) {
            String urlString;

            try {
                UrlBuilder builder = new UrlBuilder("http://demoapp.test.magicwindow.cn/v1/demoapp/checkUpdate");
                urlString = builder.buildUrl();

                JSONObject json = new JSONObject();
                json.put("os","0"); // 0表示android,1表示iOS
                json.put("currentVersion",app.version);

                RestClient.post(urlString,json, new HttpResponseHandler() {

                    @Override
                    public void onSuccess(String content, Map<String, List<String>> header) {
                        try {
                            response = RestUtil.parseAs(DownloadResponse.class, content);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(RestException arg0) {
                    }

                });

                if (response == null) {
                    throw new IOException();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return Config.RESULT_IOERROR;
            } catch (Exception e) {
                return Config.RESULT_IOERROR;
            }

            return Config.RESULT_SUCCESS;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            if (result == Config.RESULT_SUCCESS) {
                checkUpdate(response);
            } else {
                loadingNext();
            }
        }

    }

}

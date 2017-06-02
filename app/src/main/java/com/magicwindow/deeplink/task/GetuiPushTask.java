package com.magicwindow.deeplink.task;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.magicwindow.deeplink.app.MWApplication;
import com.magicwindow.deeplink.config.ApiConstant;
import com.magicwindow.deeplink.prefs.AppPrefs;
import cn.magicwindow.MWConfiguration;

import java.util.List;
import java.util.Map;

import cn.salesuite.saf.http.rest.HttpResponseHandler;
import cn.salesuite.saf.http.rest.RestClient;
import cn.salesuite.saf.http.rest.RestException;
import cn.salesuite.saf.http.rest.UrlBuilder;

/**
 * Created by aaron on 16/5/6.
 */
public class GetuiPushTask extends AsyncTask<String, String[], Integer> {


    private final String PUSH_APPID = "PUSH_APPID";
    private final String PUSH_APPKEY = "PUSH_APPKEY";
    private final String PUSH_APPSECRET = "PUSH_APPSECRET";

    @Override
    protected Integer doInBackground(String... params) {
        UrlBuilder builder = new UrlBuilder(ApiConstant.PUSH);
        String urlString = builder.buildUrl();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("os", "0");
        if (!TextUtils.isEmpty(AppPrefs.get(MWApplication.getInstance()).getCid())) {
            jsonObject.put("cid", AppPrefs.get(MWApplication.getInstance()).getCid());
        }

        RestClient.post(urlString, jsonObject, new HttpResponseHandler() {
            @Override
            public void onSuccess(String s, Map<String, List<String>> map) {
                Log.d("GetuiPushTask", "push success!");

            }

            @Override
            public void onFail(RestException e) {
                Log.d("GetuiPushTask", "push failed!");

            }
        });
//        RestClient.post(urlString).body();
        return null;
    }


    /**
     * 读取application 节点  meta-data 信息
     */
    private String getMetaData(String tag) {
        String metaData = "";
        if (TextUtils.isEmpty(tag)) {
            return "";
        }

        try {
            ApplicationInfo appInfo = MWConfiguration.getContext().getPackageManager()
                    .getApplicationInfo(MWConfiguration.getContext().getPackageName(),
                            PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                metaData = appInfo.metaData.getString(tag);
                if (!TextUtils.isEmpty(metaData)) {
                    metaData = metaData.trim();
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.d("GetuiPushTask", "please make sure the " + tag + " in AndroidManifest.xml is right! " + tag + " = " + metaData);
        }

        return metaData;
    }
}

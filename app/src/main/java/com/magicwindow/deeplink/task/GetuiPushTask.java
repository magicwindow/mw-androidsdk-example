package com.magicwindow.deeplink.task;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.zxinsight.MWConfiguration;

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
    private static final String HOST = "http://121.40.195.177/push/";

    private String path;


    private final String PUSH_APPID = "PUSH_APPID";
    private final String PUSH_APPKEY = "PUSH_APPID";
    private final String PUSH_APPSECRET = "PUSH_APPID";


    public GetuiPushTask(String path) {
        this.path = path;
    }

    @Override
    protected Integer doInBackground(String... params) {
        UrlBuilder builder = new UrlBuilder(HOST + path);
        String urlString = builder.buildUrl();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appId", getMetaData(PUSH_APPID));
        jsonObject.put("appKey", getMetaData(PUSH_APPKEY));
        jsonObject.put("masterSecret", getMetaData(PUSH_APPSECRET));
        jsonObject.put("os", "android");
        jsonObject.put("cid", "7a05d84997e96431ceafc44adbd0afdf");
        jsonObject.put("message", "wtf");

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
        RestClient.get(urlString).body();
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

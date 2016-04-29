package com.magicwindow.deeplink.task;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import cn.salesuite.saf.http.rest.RestClient;
import cn.salesuite.saf.rxjava.RxAsyncTask;

/**
 * Created by Tony Shen on 16/4/29.
 */
public class CheckUpdateTask extends RxAsyncTask {

    private static final String CHECK_UPDATE_URL = "http://demoapp.test.magicwindow.cn/v1/demoapp/checkUpdate";
    private String version;

    public CheckUpdateTask(String version) {
        this.version = version;
    }

    /**
     * 返回网络请求的response
     * @return
     */
    public String onExecute() {

        RestClient client = RestClient.post(CHECK_UPDATE_URL);

        JSONObject json = new JSONObject();
        json.put("os","0"); // 0表示android,1表示iOS
        json.put("currentVersion",version);

        try {
            return client.acceptJson().contentType("application/json", null).send(json).body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

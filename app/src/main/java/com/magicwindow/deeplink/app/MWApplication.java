/**
 *
 */
package com.magicwindow.deeplink.app;

import com.magicwindow.deeplink.config.Config;
import com.magicwindow.deeplink.task.NetTask;
import com.zxinsight.MWConfiguration;
import com.zxinsight.MagicWindowSDK;

import java.util.ArrayList;
import java.util.List;

import cn.salesuite.saf.app.SAFApp;
import cn.salesuite.saf.utils.AsyncTaskExecutor;

/**
 * @author Tony Shen
 */
public class MWApplication extends SAFApp {

    private static MWApplication mInstance = null;

    public static MWApplication getInstance() {
        return mInstance;
    }

    public void onCreate() {

        setFileDir(Config.CACHE_DIR); // 设置app默认文件路径,用于存放图片
        super.onCreate();
        mInstance = this;
        mInstance.imageLoader.setEnableDiskCache(false);
        initMW();
        initJson();
    }

    //@mw 初始化魔窗
    private void initMW(){
        MWConfiguration config = new MWConfiguration(this);
        config.setChannel("WanDouJia")
                .setDebugModel(true)
                .setPageTrackWithFragment(true)
                .setWebViewBroadcastOpen(true)
                .setMLinkOpen()
                .setSharePlatform(MWConfiguration.ORIGINAL);
        MagicWindowSDK.initSDK(config);
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
}

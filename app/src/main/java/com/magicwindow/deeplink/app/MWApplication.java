/**
 *
 */
package com.magicwindow.deeplink.app;

import com.magicwindow.deeplink.config.Config;
import com.magicwindow.deeplink.prefs.AppPrefs;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.zxinsight.MWConfiguration;
import com.zxinsight.MagicWindowSDK;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.salesuite.saf.app.SAFApp;

/**
 * @author Tony Shen
 */
public class MWApplication extends SAFApp {

    private static MWApplication mInstance = null;
    private AppPrefs appPrefs;
    private RefWatcher refWatcher;

    public static MWApplication getInstance() {
        return mInstance;
    }

    public void onCreate() {

        setFileDir(Config.CACHE_DIR); // 设置app默认文件路径,用于存放图片
        super.onCreate();
        mInstance = this;
        appPrefs = AppPrefs.get(mInstance);
//        mInstance.imageLoader.setEnableDiskCache(false);
        imageLoader.setEnableDiskCache(true);
        initMW();
        initJson();
        refWatcher = LeakCanary.install(this);
    }

    public RefWatcher getRefWatcher() {
        return refWatcher;
    }

    //@mw 初始化魔窗
    private void initMW() {
        MWConfiguration config = new MWConfiguration(this);
        config.setChannel("WanDouJia")
                .setDebugModel(true)
                .setPageTrackWithFragment(true)
                .setWebViewBroadcastOpen(true)
                .setMLinkOpen()
                .setSharePlatform(MWConfiguration.ORIGINAL);
        MagicWindowSDK.initSDK(config);
    }

    /**
     * 初始化json,如果网络出现问题,取本地的json
     */
    private void initJson() {
        List<String> list = new ArrayList<String>();
        list.add(Config.businessList);
        list.add(Config.o2oList);
        list.add(Config.newsList);
        list.add(Config.picList);
        list.add(Config.travelList);

        for (final String path : list) {
            initAssetsJson(path);
        }
    }

    private void initAssetsJson(String path) {
        if (appPrefs != null)
            appPrefs.saveJson(path, getFromAssets(path));
    }

    //从assets 文件夹中获取文件并读取数据
    private String getFromAssets(String fileName) {
        String result = "";
        try {
            InputStream in = mInstance.getResources().getAssets().open(fileName);
            //获取文件的字节数
            int lenght = in.available();
            //创建byte数组
            byte[] buffer = new byte[lenght];
            //将文件中的数据读到byte数组中
            in.read(buffer);
            result = new String(buffer, "UTF-8");
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

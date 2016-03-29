/**
 *
 */
package com.magicwindow.deeplink.app;

import com.magicwindow.deeplink.config.Config;
import com.magicwindow.deeplink.prefs.AppPrefs;
import com.magicwindow.deeplink.task.NetTask;
import com.zxinsight.MWConfiguration;
import com.zxinsight.MagicWindowSDK;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.salesuite.saf.app.SAFApp;
import cn.salesuite.saf.log.L;
import cn.salesuite.saf.rxjava.RxAsyncTask;

/**
 * @author Tony Shen
 */
public class MWApplication extends SAFApp {

    private static MWApplication mInstance = null;
    private AppPrefs appPrefs;

    public static MWApplication getInstance() {
        return mInstance;
    }

    public void onCreate() {

        setFileDir(Config.CACHE_DIR); // 设置app默认文件路径,用于存放图片
        super.onCreate();
        mInstance = this;
        appPrefs = AppPrefs.get(mInstance);
//        mInstance.imageLoader.setEnableDiskCache(false);
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

        NetTask task = null;
        for(final String path:list) {
            initAssetsJson(path);
            task = new NetTask(path);
            task.execute(new RxAsyncTask.HttpResponseHandler() {
                @Override
                public void onSuccess(String s) {
                    appPrefs.saveJson(path, s);
                }

                @Override
                public void onFail(Throwable throwable) {

                }
            });
        }
    }

    private void initAssetsJson(String path) {

        if (appPrefs.getLastVersion() != null && appPrefs.getLastVersion().equals(MWApplication.getInstance().version)) { // 肯定是第一次安装，进入学习页
            appPrefs.saveJson(path, getFromAssets(path));
        }

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

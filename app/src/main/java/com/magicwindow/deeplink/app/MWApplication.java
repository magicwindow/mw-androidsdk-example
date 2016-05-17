/**
 *
 */
package com.magicwindow.deeplink.app;

import android.content.Context;
import android.graphics.Bitmap;

import com.magicwindow.deeplink.config.Config;
import com.magicwindow.deeplink.prefs.AppPrefs;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
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

    public static MWApplication getInstance() {
        return mInstance;
    }

    public void onCreate() {

        super.onCreate();
        mInstance = this;
        appPrefs = AppPrefs.get(mInstance);
        initMW();
        initJson();
        initImageLoader(getApplicationContext());
    }

    //@mw 初始化魔窗
    private void initMW() {
        MWConfiguration config = new MWConfiguration(this);
        config.setChannel("魔窗")
                .setDebugModel(true)
                .setPageTrackWithFragment(true)
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
        list.add(Config.shopDetail);
        list.add(Config.o2oDetail);
        list.add(Config.travelDetail);

        for (final String path : list) {
            initAssetsJson(path);
        }
    }


    public static void initImageLoader(Context context) {
        DisplayImageOptions option = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.defaultDisplayImageOptions(option);
//        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
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

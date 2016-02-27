/**
 *
 */
package com.zxinsight.magicwindow.activity;

import android.app.Application;

import com.zxinsight.MWConfiguration;
import com.zxinsight.MagicWindowSDK;

/**
 * @author Aaron
 */
public class MWApplication extends Application {

    private static MWApplication mInstance = null;

    public static MWApplication getInstance() {
        return mInstance;
    }

    public void onCreate() {

        super.onCreate();
        mInstance = this;
        initMW();
    }

    //@mw 初始化魔窗
    private void initMW(){
        MWConfiguration config = new MWConfiguration(this);
        config.setChannel("魔窗")
                .setDebugModel(true)
                .setWebViewBroadcastOpen(true)
                .setSharePlatform(MWConfiguration.ORIGINAL)
                .setMLinkOpen();
        MagicWindowSDK.initSDK(config);
    }
}
